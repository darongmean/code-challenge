(ns code-interview.graphs)


(defn mark-visited
  "Mark node \"x\" as visited by remove nodes from graph"
  [m x] (dissoc m x))


(defn visited? [m x] (not (contains? m x)))


(defn- dfs-itr [{:keys [graph queue] :as m}]
  (let [[x & xs] queue
        terminate? (or (empty? graph) (empty? queue))]
    (cond
      terminate? nil

      (visited? graph x)
      (assoc m :queue xs)

      :else
      (-> m
          (update :acc conj x)
          (update :graph mark-visited x)
          (assoc :queue (concat (graph x) xs))))))


(defn depth-first-search [graph elem]
  (->> (iterate dfs-itr {:graph graph :queue [elem] :acc []})
       (take-while (complement nil?))
       (last)
       (:acc)))


(defn- bfs-itr [{:keys [graph queue] :as m}]
  (let [[x & xs] queue
        terminate? (or (empty? graph) (empty? queue))]
    (cond
      terminate? nil

      (visited? graph x)
      (assoc m :queue xs)

      :else
      (-> m
          (update :acc conj x)
          (update :graph mark-visited x)
          (assoc :queue (concat xs (graph x)))))))


(defn breath-first-search [graph elem]
  (->> (iterate bfs-itr {:graph graph :queue [elem] :acc []})
       (take-while (complement nil?))
       (last)
       (:acc)))

