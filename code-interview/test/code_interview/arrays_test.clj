(ns code-interview.arrays-test
  (:require
    [clojure.spec.alpha :as s]
    [clojure.test :refer :all]
    [clojure.test.check.clojure-test :refer [defspec]]
    [clojure.test.check.generators :as gen]
    [com.gfredericks.test.chuck.clojure-test :refer [for-all checking]]

    [code-interview.arrays :as arrays]

    [orchestra.spec.test :as st]))


(s/check-asserts true)

(st/instrument)

;;;  Implement an algorithm to determine if a string has all unique characters.
;;;  What if you cannot use additional data structures?


(deftest unique-character-test
  (checking "output shape" 100
    [x (gen/vector gen/char-alpha)]
    (is (s/valid? boolean? (arrays/unique-character-java? x))))

  (checking "with well known examples" 100
    [[output input] (gen/elements [[true "a"] [false "aa"] [true "abc"]])]
    (is (= output (arrays/unique-character-java? input)))
    (is (= output (arrays/unique-character-clj? input))))

  (checking "java and clojure style implementation are the same" 100
    [x (gen/vector gen/char-alpha)]
    (is (= (arrays/unique-character-clj? x)
           (arrays/unique-character-java? x)))))


;;; Given two strings, write a method to decide if one is a permutation of the other.


(deftest permutation?-test
  (checking "with well known examples" 100
    [[expected s1 s2] (gen/elements [[true "a" "a"]
                                     [false "a" "aa"]
                                     [true "bca" "abc"]
                                     [true "God" "dog"]
                                     [false "god    " "dog"]])]
    (is (= expected (arrays/permutation? s1 s2))))

  (checking "with random string" 100
    [s2 gen/string
     s1 (gen/return (apply str ((fnil shuffle []) (seq s2))))]
    (is (arrays/permutation? s1 s2))))
