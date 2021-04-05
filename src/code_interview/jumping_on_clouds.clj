(ns code-interview.jumping-on-clouds
  "See https://www.hackerrank.com/challenges/jumping-on-the-clouds/problem")


(defn jump
  [{:keys [input jump-count] :or {jump-count 0}}]
  (let [[_ n2 n3] input
        [_ & jump-1] input
        [_ _ & jump-2] input
        inc-jump (inc jump-count)]
    (cond
      (= 0 n3) {:input jump-2
                :jump-count inc-jump}
      (= 1 n3) {:input jump-1
                :jump-count inc-jump}
      (some? n2) {:jump-count inc-jump}
      :else nil)))


(defn jumping-on-clouds
  [c]
  (->> (iterate jump {:input c})
       (take-while some?)
       (last)
       :jump-count))


(comment
  (jump {:input [0 0]})
  ;; => 3
  (jumping-on-clouds [0 0 0 1 0 0])
  ;; => 4
  (jumping-on-clouds [0 0 1 0 0 1 0])
  ;; => 3
  (jumping-on-clouds [0 1 0 0 0 1 0])
  ;; => 3
  (jumping-on-clouds [0 0 0 0 1 0]))
