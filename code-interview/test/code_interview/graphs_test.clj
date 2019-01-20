(ns code-interview.graphs-test
  (:require
    [clojure.spec.alpha :as s]
    [clojure.test :refer :all]
    [clojure.test.check.generators :as gen]
    [com.gfredericks.test.chuck.clojure-test :refer [checking]]
    [orchestra.spec.test :as st]

    [code-interview.graphs :as graphs]))


(st/instrument)


(def directed-graph
  {0 [1 4 5]
   1 [3 4]
   2 [1]
   3 [2 4]
   4 []
   5 []})


(deftest depth-first-search-test
  (is (= [0 1 3 2 4 5] (graphs/depth-first-search directed-graph 0))))


;;; Given a directed graph, design an algorithm to find out whether there is
;;; a route between two nodes.

