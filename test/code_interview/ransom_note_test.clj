(ns code-interview.ransom-note-test
  (:require
   [clojure.test :refer [deftest is]]))


;; See https://www.hackerrank.com/challenges/ctci-ransom-note/problem


(defn check-magazine
  [magazine note]
  (let [note-frequency (frequencies note)
        note-subtract-magazine (reduce (fn [remaining-note word]
                                         (cond
                                           (empty? remaining-note) (reduced remaining-note)
                                           (= 1 (remaining-note word)) (dissoc remaining-note word)
                                           (< 1 (remaining-note word 0)) (update remaining-note word dec)
                                           :else remaining-note))
                                       note-frequency
                                       magazine)
        answer (if (seq note-subtract-magazine)
                 "No"
                 "Yes")]
    (doto answer println)))


(deftest check-magazine-test
  (is (= "Yes" (check-magazine ["give" "me" "one" "grand" "today" "night"] ["give" "one" "grand" "today"])))
  (is (= "No" (check-magazine ["two" "times" "three" "is" "not" "four"] ["two" "times" "two" "is" "four"])))
  (is (= "No" (check-magazine ["ive" "got" "a" "lovely" "bunch" "of" "coconuts"]
                              ["ive" "got" "some" "coconuts"])))
  (is (= "Yes" (check-magazine ["apgo" "clm" "w" "lxkvg" "mwz" "elo" "bg" "elo" "lxkvg" "elo" "apgo" "apgo" "w" "elo" "bg"]
                               ["elo" "lxkvg" "bg" "mwz" "clm" "w"]))))
