(ns code-interview.arrays
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
