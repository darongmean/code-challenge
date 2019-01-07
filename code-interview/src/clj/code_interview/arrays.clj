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

