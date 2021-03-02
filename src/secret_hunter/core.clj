(ns secret-hunter.core
  (:gen-class)
  (:require
   [secret-hunter.http :as http]
   [secret-hunter.secret-tree :as tree]

   [clj-http.conn-mgr :as conn-mgr]
   [clojure.core.async :as async]
   [clojure.spec.alpha :as s]
   [expound.alpha :as expound]
   [taoensso.timbre :as timbre]))

(timbre/set-level! :info)


;;; see https://github.com/bhb/expound/blob/master/doc/faq.md#using-alter-var-root


(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn pre-order-nodes [get-node-fn node-ids]
  (loop [[id & rest-queued :as queued] node-ids
         visited []]
    (cond
      (empty? queued)
      visited

      (tree/leaf-node? (get-node-fn id))
      (recur
       rest-queued
       (conj visited (:id (get-node-fn id))))

      :else
      (recur
       (concat (tree/child-node-ids (get-node-fn id)) rest-queued)
       (conj visited (:id (get-node-fn id)))))))

(defn join-secret [get-node-fn node-ids]
  (->> (map get-node-fn node-ids)
       (map :secret)
       (reduce str)))

(defn find-secret [ch timeout-ch base-url session connection-manager]
  (let [rest-client (http/mk-rest-client base-url session connection-manager)
        node-by-id (tree/mk-tree-with-async ch timeout-ch rest-client ["start"])]
    (->> ["start"]
         (pre-order-nodes node-by-id)
         (join-secret node-by-id))))

(defn -main [base-url session]
  (let [ch (async/chan)
        timeout (async/timeout (* 150 1000))
        cm (conn-mgr/make-reuseable-async-conn-manager {:timeout           5
                                                        :threads           64
                                                        :default-per-route 128})]
    (println "[Session] => " session)
    (println "[Secret] => " (find-secret ch timeout base-url session cm))
    (async/close! ch)
    (.shutdown cm)))
