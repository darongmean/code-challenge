(ns code-challenge.secret-tree
  (:require
    [clojure.spec.alpha :as s]
    [clojure.core.async :as async]
    [clojure.string :as string]
    [clojure.set :as set]))


(s/def ::depth integer?)

(s/def ::id string?)

(s/def ::secret string?)

(s/def ::next (s/or :one string? :many (s/* string?)))

(s/def ::branch-node (s/keys :req-un [::depth ::id ::next]))

(s/def ::leaf-node (s/keys :req-un [::depth ::id ::secret]))

(s/def ::node (s/or :branch ::branch-node :leaf ::leaf-node))


(defn format-keys [node]
  (let [ks (keys node)
        lowercasekw #(-> % (name) (string/lower-case) (keyword))]
    (->> (map (juxt identity lowercasekw) ks)
         (into {})
         (set/rename-keys node))))


(defn leaf-node? [node]
  (let [{:keys [next]} (format-keys node)]
    (empty? next)))


(defn child-node-ids [node]
  (let [{:keys [next]} (format-keys node)]
    (if (coll? next) next [next])))


(defn expand-node [visit-node-fn node]
  {:pre [(or (s/valid? ::node node) (s/explain ::node node))]}
  (doseq [id (child-node-ids node)
          :when (not (leaf-node? node))]
    (visit-node-fn id)))

(s/fdef expand-node :args (s/cat :visit-fn fn? :node ::node))


(defn collect-node [tree node]
  {:pre [(or (s/valid? ::node node) (s/explain ::node node))]}
  (assoc tree (:id node) node))

(s/fdef collect-node
        :args (s/cat :tree map? :node ::node)
        :ret map?)


(defn expand-tree [next-node-fn expand-node-fn]
  (loop [tree {}]
    (if-some [node (format-keys (next-node-fn))]
      (do
        (expand-node-fn node)
        (recur (collect-node tree node)))
      tree)))


(defn mk-tree-with-async [acc-ch timeout-ch visit-node-fn node-ids]
  (doseq [id node-ids]
    (visit-node-fn acc-ch id))
  (expand-tree
    #(first (async/alts!! [acc-ch timeout-ch]))
    #(expand-node (partial visit-node-fn acc-ch) %)))


(defn mk-tree [visit-node-fn node-ids]
  (mk-tree-with-async
    (async/chan)
    (async/timeout 100)
    #(async/go (async/>! %1 (visit-node-fn %2)))
    node-ids))
