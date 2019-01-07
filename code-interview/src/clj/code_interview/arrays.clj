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


(defn char-edit? [x y prev-edit] (and (not= x y) (empty? prev-edit)))

(defn ins-edit? [x y [prev-x _]] (and (not= x y) (= y prev-x)))

(defn rem-edit? [x y [_ prev-y]] (and (not= x y) (= x prev-y)))

(defn rep-edit? [x y prev-edit] (and (= x y) (not-empty prev-edit)))

(defn edit-count [s1 s2]
  (loop [[x & xs :as s1'] (seq s1)
         [y & ys :as s2'] (seq s2)
         [prev-x prev-y :as prev-edit] []
         edits []]
    (cond
      (and (empty? s1') (empty? s2'))
      edits

      (char-edit? x y prev-edit)
      (recur xs ys [x y] edits)

      (ins-edit? x y prev-edit)
      (recur s1' ys [] (conj edits {:insert [nil prev-y]}))

      (rem-edit? x y prev-edit)
      (recur xs s2' [] (conj edits {:remove [prev-x nil]}))

      (rep-edit? x y prev-edit)
      (recur xs ys [] (conj edits {:replace prev-edit}))

      (and (not= x y) (not-empty prev-edit))
      (recur xs ys [x y] (conj edits {:replace prev-edit}))

      :else
      (recur xs ys [] edits))))

(defn one-away? [s1 s2]
  (<= (count (edit-count s1 s2)) 1))

(s/fdef one-away?
  :args (s/cat :s1 string? :s2 string?)
  :ret boolean?)
