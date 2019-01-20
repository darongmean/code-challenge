(ns code-interview.graphs)


(defn dfs-itr [{:keys [graph queue] :as m}]
  (let [[x & xs] queue]
    (cond
      (empty? graph) nil

      (empty? queue) nil

      (not (contains? graph x))
      (-> m
          (assoc :queue xs))

      (empty? (graph x))
      (-> m
          (update :graph dissoc x)
          (assoc :queue xs)
          (update :acc conj x))

      :else
      (-> m
          (update :graph dissoc x)
          (assoc :queue (concat (graph x) xs))
          (update :acc conj x)))))


(defn depth-first-search [graph elem]
  (->> (iterate dfs-itr {:graph graph :queue [elem] :acc []})
       (take-while (complement nil?))
       (last)
       (:acc)))

