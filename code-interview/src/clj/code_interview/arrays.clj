(ns code-interview.arrays
  (:require
    [clojure.spec.alpha :as s]
    [clojure.string :as string])
  (:import
    [java.util HashSet]))


(defn unique-character-java? [s]
  (loop [[x & xs] (seq s)
         visited (HashSet.)]
    (if (nil? x)
      true
      (let [not-visited? (.add visited x)]
        (if not-visited?
          (recur xs visited)
          false)))))


(defn unique-character-clj? [s]
  (let [s' (set s)]
    (= (count s) (count s'))))


(defn permutation?
  "Check if String s1 is a permutation of s2"
  [s1 s2]
  (= (frequencies (string/lower-case s1))
     (frequencies (string/lower-case s2))))

(s/fdef permutation?
  :args (s/cat :s1 string? :s2 string?)
  :ret boolean?)


(defn encode [st]
  (.replace st " " "%20"))

(def substring (fnil #(.substring %1 %2 %3) ""))

(defn urlify [st l]
  (-> st
      (substring 0 l)
      (encode)))

(s/fdef urlify
  :args (s/cat :st string? :l integer?)
  :ret string?)


(defn palindrome? [char-occurrences]
  (let [odd-count (count (remove even? char-occurrences))]
    (contains? #{0 1} odd-count)))

(defn format-palindrome-input [st]
  (let [lower-case (fnil #(.toLowerCase %) "")]
    (-> st
        (lower-case)
        (.replace " " ""))))

(defn char-counts [st]
  (-> st
      (frequencies)
      (vals)))

(defn permutation-palindrome? [st]
  (let [st' (format-palindrome-input st)]
    (palindrome? (char-counts st'))))

(s/fdef permutation-palindrome?
  :args (s/cat :st string?)
  :ret boolean?)
