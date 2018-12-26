(ns code-interview.arrays-test
  (:require
    [clojure.spec.alpha :as s]
    [clojure.test :refer :all]

    [code-interview.core :refer :all]

    [orchestra.spec.test :as st]))


(s/check-asserts true)

(st/instrument)

;;;  Implement an algorithm to determine if a string has all unique characters.
;;;  What if you cannot use additional data structures?


(deftest unique-character-test
  (is (= true true)))
