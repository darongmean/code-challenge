(ns code-interview.linklists-test
  (:require
    [clojure.spec.alpha :as s]
    [clojure.test :refer :all]
    [clojure.test.check.generators :as gen]
    [com.gfredericks.test.chuck.clojure-test :refer [checking]]
    [orchestra.spec.test :as st]

    [code-interview.linklists :as linklists]))


(st/instrument)


;;; Implement a function to check if a linked list is a palindrome.
;;; Assuming the length of the linked list isn't known.

(deftest palindrome?-test
  (checking "with well known examples" 100
    [[expected ll] (gen/elements [[false [0 1 2 3]]
                                  [true [0 1 2 1 0]]
                                  [true [0 1 1 0]]
                                  [false [0 1 2 3]]
                                  [false [0 1 2 3 4]]
                                  [false [0 1 2 3 4 2 1]]])]
    (is (= expected (linklists/palindrome? ll)))))