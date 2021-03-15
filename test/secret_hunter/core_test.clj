(ns secret-hunter.core-test
  (:require
   [clojure.spec.alpha :as s]
   [clojure.test :refer :all]
   [expound.alpha :as expound]
   [orchestra.spec.test :as st]
   [secret-hunter.core :refer :all]
   [secret-hunter.secret-tree :refer :all]))


(s/check-asserts true)

(set! s/*explain-out* expound/printer)

(st/instrument)


;;; A test-tree with the following structure
;;;        1
;;;      /    \
;;;    2   5   7
;;;   / \  |   |
;;;  3  4  6   8
;;;


(def test-tree
  {"1" {:depth 0 :id "1" :next ["2" "5" "7"]}
   "2" {:depth 1 :id "2" :next ["3" "4"]}
   "5" {:depth 1 :id "5" :next ["6"]}
   "7" {:depth 1 :id "7" :NexT "8"}
   "3" {:depth 2 :id "3" :secret "a"}
   "4" {:depth 2 :id "4" :secret "b"}
   "6" {:depth 2 :id "6" :secret "c"}
   "8" {:depth 2 :id "8" :secret "d"}})


(deftest pre-order-nodes-test
  (testing "traverse leaf node"
    (is (= ["3"] (pre-order-nodes test-tree ["3"])))
    (is (= (pre-order-nodes test-tree ["3"])
           (pre-order-nodes (mk-tree test-tree ["3"]) ["3"]))))
  (testing "traverse node with one depth"
    (is (= ["2" "3" "4"] (pre-order-nodes test-tree ["2"])))
    (is (= (pre-order-nodes test-tree ["2"])
           (pre-order-nodes (mk-tree test-tree ["2"]) ["2"]))))
  (testing "next element is not an array"
    (is (= ["7" "8"] (pre-order-nodes test-tree ["7"])))
    (is (= (pre-order-nodes test-tree ["7"])
           (pre-order-nodes (mk-tree test-tree ["7"]) ["7"]))))
  (testing "traverse node with more depths"
    (is (= ["1" "2" "3" "4" "5" "6" "7" "8"] (pre-order-nodes test-tree ["1"])))
    (is (= (pre-order-nodes test-tree ["1"])
           (pre-order-nodes (mk-tree test-tree ["1"]) ["1"])))))


(deftest join-secret-test
  (testing "traverse leaf node"
    (is (= "a" (join-secret test-tree ["3"]))))
  (testing "traverse node with one depth"
    (is (= "c" (join-secret test-tree ["5" "6"]))))
  (testing "traverse node with more depths"
    (is (= "abcd" (join-secret test-tree ["1" "2" "3" "4" "5" "6" "7" "8"])))))


(deftest code-challenge-answer
  (testing "use test API"
    (is (= "abcd"
           (->> (pre-order-nodes test-tree ["1"]) (join-secret test-tree))))))
