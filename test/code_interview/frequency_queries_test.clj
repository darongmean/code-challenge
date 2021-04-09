(ns code-interview.frequency-queries-test
  (:require
   [clojure.test :refer [deftest is]]))


;; See https://www.hackerrank.com/challenges/frequency-queries/problem


(defn freq-query
  [queries]
  (let [increment (fnil inc 0)
        decrement (fnil dec 0)
        present? (fn [frequency-count n]
                   (if (pos? (frequency-count n -1))
                     1
                     0))
        apply-query (fn [{:keys [frequency-by-n frequency-count] :as acc} [query n]]
                      (let [stale-freq (frequency-by-n n)
                            inc-freq (increment stale-freq)
                            dec-freq (decrement stale-freq)]
                        (cond
                          (= 1 query) (-> acc
                                          (update-in [:frequency-by-n n] increment)
                                          (update-in [:frequency-count stale-freq] #(max 0 (decrement %)))
                                          (update-in [:frequency-count inc-freq] increment))
                          (= 2 query) (-> acc
                                          (update-in [:frequency-by-n n] #(max 0 (decrement %)))
                                          (update-in [:frequency-count stale-freq] #(max 0 (decrement %)))
                                          (update-in [:frequency-count dec-freq] increment))
                          (= 3 query) (update acc :output conj (present? frequency-count n)))))]
    (->> queries
         (reduce apply-query {:output [] :frequency-by-n {} :frequency-count {}})
         :output)))


(deftest freq-query-test
  (is (= [0 1] (freq-query [[1 1] [2 2] [3 2] [1 1] [1 1] [2 1] [3 2]])))
  (is (= [0 1] (freq-query [[1 5] [1 6] [3 2] [1 10] [1 10] [1 6] [2 5] [3 2]])))
  (is (= [0 1] (freq-query [[3 4] [2 1003] [1 16] [3 1]])))
  (is (= [0 1 1] (freq-query [[1 3] [2 3] [3 2] [1 4] [1 5] [1 5] [1 4] [3 2] [2 4] [3 2]]))))
