(ns code-interview.counting-valleys
  "See https://www.hackerrank.com/challenges/counting-valleys/problem")


(defn counting-valleys
  [steps path]
  (let [sea-height {\U 1 \D -1}
        valley? (fn [[prev-height current-height]]
                  (and (neg? prev-height) (zero? current-height)))]
    (->> path
         (map sea-height)
         (reductions +)
         (partition-all 2 1)
         (filter valley?)
         (count))))


(comment
  ;; => 1
  (counting-valleys 8 "UDDDUDUU")
  ;; => 2
  (counting-valleys 12 "DDUUDDUDUUUD"))
