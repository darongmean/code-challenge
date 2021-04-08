(ns code-interview.count-triplets-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is testing]]))


;; See https://www.hackerrank.com/challenges/count-triplets-1/problem


(defn count-triplets
  [arr r]
  (let [plus (fnil + 0 0)
        count-triplet (fn [{:keys [single pair] :as m} x]
                        (let [rx (*' r x)
                              rrx (*' r rx)]
                          (-> m
                              (update-in [:single x] plus 1)
                              (update-in [:pair x rx] plus (get single rx))
                              ;; Uncomment the following to see how triplet is accumulated
                              #_(update-in [:triplet x rx rrx] plus (get-in pair [rx rrx]))
                              (update-in [:triplet-count] plus (get-in pair [rx rrx])))))]
    (->> (reverse arr)
         (reduce count-triplet {:single {} :pair {} :triplet {} :triplet-count 0})
         :triplet-count)))


(deftest count-triplets-test
  (is (= 2 (count-triplets [1 4 16 64] 4)))
  (is (= 2 (count-triplets [1 2 2 4] 2)))
  (is (= 6 (count-triplets [1 3 9 9 27 81] 3)))
  (is (= 4 (count-triplets [1 5 5 25 125] 5)))
  (testing "it could handle big number"
    (let [in (-> "test/code_interview/count_triplets_input07.txt"
                 (slurp)
                 (str/split-lines)
                 (last)
                 (str/split #" ")
                 (->> (map #(Long/parseLong %))))]
      (is (= 0 (count-triplets in 165427300))))))
