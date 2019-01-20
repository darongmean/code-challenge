(ns code-interview.graphs)


(defn mark-visited
  "Mark node \"x\" as visited by remove nodes from graph"
  [m x] (dissoc m x))


(defn visited? [m x] (not (contains? m x)))


(defn- dfs-itr [{:keys [itr result] :as m}]
  (let [{:keys [graph queue acc]} itr
        [x & xs] queue
        terminate? (or (empty? graph) (empty? queue))]
    (cond
      terminate? {:result (or acc result)}

      (visited? graph x)
      (assoc-in m [:itr :queue] xs)

      :else
      (-> m
          (update-in [:itr :acc] conj x)
          (update-in [:itr :graph] mark-visited x)
          (assoc-in [:itr :queue] (concat (graph x) xs))))))


(defn depth-first-search [graph elem]
  (->> (iterate dfs-itr {:itr {:graph graph :queue [elem] :acc []}})
       (keep :result)
       (first)))


(defn- bfs-itr [{:keys [itr result] :as m}]
  (let [{:keys [graph queue acc]} itr
        [x & xs] queue
        terminate? (or (empty? graph) (empty? queue))]
    (cond
      terminate? {:result (or acc result)}

      (visited? graph x)
      (assoc-in m [:itr :queue] xs)

      :else
      (-> m
          (update-in [:itr :acc] conj x)
          (update-in [:itr :graph] mark-visited x)
          (assoc-in [:itr :queue] (concat xs (graph x)))))))


(defn breath-first-search [graph elem]
  (->> (iterate bfs-itr {:itr {:graph graph :queue [elem] :acc []}})
       (keep :result)
       (first)))


(defn connected? [graph elem1 elem2]
  (->> (breath-first-search graph elem1)
       (some #{elem2})
       (some?)))
