(ns code-interview.two-strings-test
  (:require
   [clojure.test :refer [deftest is]]))


;; See https://www.hackerrank.com/challenges/two-strings/problem


(defn two-strings
  [s1 s2]
  (let [s1-set (into #{} s1)
        contain-s1-char (fn [acc cha]
                          (if (acc cha)
                            (reduced :contain-s1-char)
                            acc))
        has-common-char? (->> s2
                              (reduce contain-s1-char s1-set)
                              (= :contain-s1-char))]

    (if has-common-char?
      "YES"
      "NO")))


(deftest two-strings-test
  (is (= "YES" (two-strings "and" "art")))
  (is (= "YES" (two-strings "hello" "world")))
  (is (= "NO" (two-strings "be" "cat")))
  (is (= "NO" (two-strings "hi" "world"))))

