(ns code-interview.linklists-test
  (:require
   [clojure.spec.alpha :as s]
   [clojure.test :refer :all]
   [clojure.test.check.generators :as gen]
   [code-interview.linklists :as linklists]
   [com.gfredericks.test.chuck.clojure-test :refer [checking]]
   [orchestra.spec.test :as st]))


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


;;; Given a circular linked list, implement an algorithm that returns the node at the beginning of the loop.
;;; DEFINITION
;;; Circular linked list: A (corrupt) linked list in which a node's next pointer points to an earlier node,
;;; so as to make a loop in the linked list.
;;; EXAMPLE
;;; Input: A - > B - > C - > D - > E - > C [the same C as earlier)
;;; Output: C


(deftest circular-point-test
  (checking "with well known examples" 100
    [[expected h ll] (gen/elements [[:c :a {:a :b :b :c :c :d :d :e :e :c}]
                                    [:a :a {:a :b :b :a}]
                                    [nil :a {:a :b :b :c}]
                                    [:a :a {:a :b :b :c :c :a}]
                                    [:e :a {:a :b
                                            :b :c
                                            :c :d
                                            :d :e
                                            :e :f
                                            :f :g
                                            :g :e}]])]
    (is (= expected (linklists/circular-point h ll)))))

