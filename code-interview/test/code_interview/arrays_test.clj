(ns code-interview.arrays-test
  (:require
    [clojure.spec.alpha :as s]
    [clojure.test :refer :all]
    [clojure.test.check.clojure-test :refer [defspec]]
    [clojure.test.check.generators :as gen]
    [com.gfredericks.test.chuck.clojure-test :refer [for-all checking]]

    [code-interview.arrays :as arrays]))


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


;;; Write a method to replace all spaces in a string with '%20: You may assume that the string
;;; has sufficient space at the end to hold the additional characters,
;;; and that you are given the "true" length of the string.


(deftest urlify-test
  (checking "with well known examples" 100
    [[expected st l] (gen/elements [["Mr%20John%20Smith" "Mr John Smith      " 13]
                                    ["" nil 0]
                                    ["" "" 0]])]
    (is (= expected (arrays/urlify st l)))))


;;; Given a string, write a function to check if it is a permutation of a palindrome.
;;; A palindrome is a word or phrase that is the same forwards and backwards.
;;; A permutation is a rearrangement of letters.
;;; The palindrome does not need to be limited to just dictionary words.


(deftest palindrome?-test
  (checking "with well known examples" 100
    [[expected st] (gen/elements [[true "Tact Coa"]
                                  [true nil]
                                  [false "abc"]])]
    (is (= expected (arrays/permutation-palindrome? st)))))


;;; There are three types of edits that can be performed on strings:
;;; insert a character, remove a character, or replace a character.
;;; Given two strings, write a function to check if they are one edit (or zero edits) away.


(deftest one-away?-test
  (checking "with well known examples" 100
    [[expected s1 s2] (gen/elements [[false "s" "pales"]
                                     [false "pales" "s"]
                                     [false "pales" "pbcdst"]
                                     [false "pales" "baket"]
                                     [false "pale" "bake"]
                                     [true "pale" "ple"]
                                     [true "pales" "pale"]
                                     [true "pale" "bale"]
                                     [true "abcb" "adcb"]])]
    (is (= expected (arrays/one-away? s1 s2)))
    (is (= expected (arrays/one-away2? s1 s2)))))


;;; Implement a method to perform basic string compression using the counts of repeated characters.
;;; For example, the string aabcccccaaa would become a2b1c5a3.
;;; If the "compressed" string would not become smaller than the original string,
;;; your method should return the original string.
;;; You can assume the string has only uppercase and lowercase letters (a - z).


(deftest compress-string-test
  (checking "with well known examples" 100
    [[expected st] (gen/elements [["a2b1c5a3" "aabcccccaaa"]])]
    (is (= expected (arrays/compress-string st)))))
