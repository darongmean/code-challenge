(ns code-interview.fraudulent-activity-notifications-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is testing]]))


;; See https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem


(defn nth-counting-sort
  [counting-sort-map low high]
  (let [index-elem-pair (fn [^clojure.lang.Indexed acc ^clojure.lang.Indexed pair]
                          ;; Use (.nth ^Indexed pair ...) improve performance by reducing the overall
                          ;; time from 6000 ms to 3000 ms.
                          (let [element (.nth pair 0)
                                element-frequency (.nth pair 1)
                                index-low (.nth acc 0)
                                element-low (.nth acc 1)
                                index-high (.nth acc 2)]
                            (cond
                              (<= index-low low)
                              [(+ index-low element-frequency) element
                               (+ index-high element-frequency) element]
                              (<= index-high high)
                              [index-low element-low
                               (+ index-high element-frequency) element]
                              :else
                              (reduced acc))))]
    (reduce index-elem-pair [0 nil 0 nil] counting-sort-map)))


(defn decrement
  [x]
  (cond
    (nil? x) 0
    (<= x 0) 0
    :else (dec x)))


(def increment (fnil inc 0))


(defn median
  [expenditure-counting-sort length]
  (let [mid (Math/floorDiv (long length) (long 2))
        high mid
        low (if (even? length)
              (decrement mid)
              mid)
        [_ exp-low _ exp-high] (nth-counting-sort expenditure-counting-sort low high)]
    (/ (+ exp-low exp-high)
       2)))


(defn notification-count
  [{:keys [past-expenditure-queue past-expenditure-counting-sort] :as acc} today-exp]
  (let [first-day-exp (peek past-expenditure-queue)
        median-exp (median past-expenditure-counting-sort
                           (count past-expenditure-queue))]
    (cond-> acc
      :always (assoc :past-expenditure-queue (-> past-expenditure-queue
                                                 (pop)
                                                 (conj today-exp))
                     :past-expenditure-counting-sort (-> past-expenditure-counting-sort
                                                         (update first-day-exp decrement)
                                                         (update today-exp increment)))
      (<= (* 2 median-exp) today-exp) (update :notification-count increment))))


(defn activity-notifications
  [expenditure d]
  (let [past-expenditure-queue (into (clojure.lang.PersistentQueue/EMPTY)
                                     (take d)
                                     expenditure)
        present-expenditure (drop d expenditure)
        past-expenditure-counting-sort (->> past-expenditure-queue
                                            (seq)
                                            (frequencies)
                                            (into (sorted-map)))]
    (->> present-expenditure
         (reduce notification-count {:past-expenditure-queue past-expenditure-queue
                                     :past-expenditure-counting-sort past-expenditure-counting-sort
                                     :notification-count 0})
         :notification-count)))


(deftest activity-notifications-test
  (is (= 1 (activity-notifications [10 20 30 40 50] 3)))
  (is (= 0 (activity-notifications [1 2 3 4 4] 4)))
  (is (= 2 (activity-notifications [2 3 4 2 3 6 8 4 5] 5)))
  (testing "it could handle even big number"
    (let [input (-> "test/code_interview/fraudulent_activity_notifications_input01.txt"
                    (slurp)
                    (str/split-lines)
                    (last)
                    (str/split #" ")
                    (->> (map #(Long/parseLong %))))]
      (is (= 633 (activity-notifications input 10000)))))
  (testing "it could handle odd big number"
    (let [input (-> "test/code_interview/fraudulent_activity_notifications_input05.txt"
                    (slurp)
                    (str/split-lines)
                    (last)
                    (str/split #" ")
                    (->> (map #(Long/parseLong %))))]
      (is (= 926 (activity-notifications input 40001))))))


(comment
 (def input (-> "test/code_interview/fraudulent_activity_notifications_input05.txt"
                (slurp)
                (str/split-lines)
                (last)
                (str/split #" ")
                (->> (map #(Long/parseLong %)))))

 (time (activity-notifications input 10000))

 (require '[clj-async-profiler.core :as prof])
 (prof/serve-files 8080)

 (prof/profile (dotimes [_ 100] (activity-notifications input 40001)))
 (prof/profile {:event :alloc} (dotimes [_ 100] (activity-notifications input 40001))))
