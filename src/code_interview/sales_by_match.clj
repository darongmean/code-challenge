(ns code-interview.sales-by-match
  "See https://www.hackerrank.com/challenges/sock-merchant/problem")


(defn sock-merchant
  [n ar]
  (->> ar
       (group-by identity)
       (map (fn [[_color sock-coll]] (count sock-coll)))
       (map #(int (/ % 2)))
       (reduce +)))


(comment
  ;; => 3
  (sock-merchant 9 [10 20 20 10 10 30 50 10 20]))
