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
    (is (= output (arrays/unique-character-clj? input)))))


(defspec unique-character-same-result-different-implementation 100
  (for-all [x (gen/vector gen/char-alpha)]
    (is (= (arrays/unique-character-clj? x)
           (arrays/unique-character-java? x)))))
