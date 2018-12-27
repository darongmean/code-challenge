(ns code-interview.arrays-test
  (:require
    [clojure.spec.alpha :as s]
    [clojure.test :refer :all]
    [clojure.test.check.clojure-test :refer [defspec]]
    [clojure.test.check.generators :as gen]
    [com.gfredericks.test.chuck.clojure-test :refer [for-all]]

    [code-interview.arrays :as arrays]

    [orchestra.spec.test :as st]))


(s/check-asserts true)

(st/instrument)

;;;  Implement an algorithm to determine if a string has all unique characters.
;;;  What if you cannot use additional data structures?


(deftest unique-character-test
  (is (= true (arrays/unique-character-java? "a")))
  (is (= false (arrays/unique-character-java? "aa")))
  (is (= true (arrays/unique-character-clj? "abc")))
  (is (= false (arrays/unique-character-clj? "aa"))))


(defspec unique-character-same-result-different-implementation 100
  (for-all [x (gen/vector gen/char-alpha)]
    (is (= (arrays/unique-character-clj? x)
           (arrays/unique-character-java? x)))))
