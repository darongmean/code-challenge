(ns code-interview.repeated-string-test
  (:require
   [clojure.test :refer [deftest is]]))


;; See https://www.hackerrank.com/challenges/repeated-string/problem


(defn repeated-string
  [s n]
  (let [length (count s)
        multiplier (long (/ n length))
        remainder (rem n length)
        count-all-a (->> (seq s)
                         (filter #{\a})
                         (count))
        count-remain-a (->> (subs s 0 remainder)
                            (filter #{\a})
                            (count))]
    (+ (* count-all-a multiplier) count-remain-a)))


(deftest repeated-string-test
  (is (= 7 (repeated-string "aba" 10)))
  (is (= 2 (repeated-string "ababa" 3)))
  (is (= 1000000000000 (repeated-string "a" 1000000000000))))
