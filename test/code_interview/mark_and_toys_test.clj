(ns code-interview.mark-and-toys-test
  (:require
   [clojure.test :refer [deftest is]]))


;; See https://www.hackerrank.com/challenges/mark-and-toys/problem


(defn maximum-toys
  [prices k]
  (->> prices
       (sort)
       (reductions +)
       (take-while #(< % k))
       (count)))


(deftest maximum-toys-test
  (is (= 3 (maximum-toys [1 2 3 4] 7)))
  (is (= 4 (maximum-toys [1 12 5 111 200 1000 10] 50))))
