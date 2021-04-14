(ns code-interview.fraudulent-activity-notifications-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is testing]]))


;; See https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem
;; TODO: fix timeout error

(defn nth-counting-sort
  [counting-sort-map index]
  (let [index-elem-pair (fn [{:keys [i] :as acc} [elem elem-freq]]
                          (if (<= i index)
                            (-> acc
                                (update :i + elem-freq)
                                (assoc :elem elem))
                            (reduced acc)))]
    (->> counting-sort-map
         (reduce index-elem-pair {:i 0 :elem nil})
         :elem)))


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
        low mid
        high (inc mid)]
    (if (even? length)
      (/ (+ (nth-counting-sort expenditure-counting-sort low)
            (nth-counting-sort expenditure-counting-sort high))
         2)
      (nth-counting-sort expenditure-counting-sort mid))))


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
  (testing "it could handle big number"
    (let [input (-> "test/code_interview/fraudulent_activity_notifications_input05.txt"
                    (slurp)
                    (str/split-lines)
                    (last)
                    (str/split #" ")
                    (->> (map #(Long/parseLong %))))]
      (is (= 926 (activity-notifications input 40001))))))
