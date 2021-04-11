(ns code-interview.sorting-bubble-sort-test
  (:require
   [clojure.test :refer [deftest is]]))


;; See https://www.hackerrank.com/challenges/ctci-bubble-sort/problem


(defn swap
  [{:keys [swap-count order un-order]
    :or {swap-count 0 order []}}]
  (let [[aj aj-plus-1 & remaining-un-order] un-order]
    (cond
      (nil? aj) nil
      (nil? aj-plus-1) {:swap-count swap-count
                        :order (conj order aj)
                        :un-order remaining-un-order}
      (< aj-plus-1 aj) {:swap-count (inc swap-count)
                        :order (conj order aj-plus-1)
                        :un-order (cons aj remaining-un-order)}
      :else {:swap-count swap-count
             :order (conj order aj)
             :un-order (cons aj-plus-1 remaining-un-order)})))


(defn swap-adjacent
  [sort-context]
  (->> sort-context
       (iterate swap)
       (take-while some?)
       (last)))


(defn mark-as-un-order
  [{:keys [swap-count order]}]
  {:swap-count swap-count
   :un-order order})


(defn swap-adjacent-n-minus-1-time
  [sort-context length]
  (->> sort-context
       (iterate #(-> %
                     (swap-adjacent)
                     (mark-as-un-order)))
       (take length)
       (last)))


(defn count-swaps
  [a]
  (let [length (count a)
        {:keys [swap-count order]} (-> {:un-order a}
                                       (swap-adjacent-n-minus-1-time length)
                                       (swap-adjacent))
        print-1 (str "Array is sorted in " swap-count " swaps.")
        print-2 (str "First Element: " (first order))
        print-3 (str "Last Element: " (last order))]
    (println print-1)
    (println print-2)
    (println print-3)
    [print-1 print-2 print-3]))


(deftest count-swaps-test
  (is (= ["Array is sorted in 3 swaps."
          "First Element: 1"
          "Last Element: 6"]
         (count-swaps [6 4 1])))
  (is (= ["Array is sorted in 0 swaps."
          "First Element: 1"
          "Last Element: 3"]
         (count-swaps [1 2 3])))
  (is (= ["Array is sorted in 3 swaps."
          "First Element: 1"
          "Last Element: 3"]
         (count-swaps [3 2 1]))))
