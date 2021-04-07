(ns code-interview.sherlock-and-anagrams-test
  (:require
   [clojure.test :refer [deftest is]]))


;; See https://www.hackerrank.com/challenges/sherlock-and-anagrams/problem


(defn sherlock-and-anagrams
  [s]
  (let [length (count s)
        substrings (for [start (range length)
                         end (range (inc start) (inc length))]
                     {(sort (subs s start end)) {start (dec end)}})
        anagram-pair (apply merge-with merge substrings)
        _anagram-count (fn [m]
                         (->> (count m)
                              (range)
                              (reduce +)))]
    (reduce-kv (fn [acc _ pair]
                 (+ acc
                    ;; or use the function _anagram-count instead of the following math formula
                    (long (/ (* (count pair)
                                (- (count pair) 1))
                             2))))
               0
               anagram-pair)))


(deftest sherlock-and-anagram-test
  (is (= 2 (sherlock-and-anagrams "mom")))
  (is (= 4 (sherlock-and-anagrams "abba")))
  (is (= 0 (sherlock-and-anagrams "abcd")))
  (is (= 3 (sherlock-and-anagrams "ifailuhkqq")))
  (is (= 10 (sherlock-and-anagrams "kkkk")))
  (is (= 5 (sherlock-and-anagrams "cdcd"))))
