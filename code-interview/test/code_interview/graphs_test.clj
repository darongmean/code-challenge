(ns code-interview.graphs-test
  (:require
    [clojure.spec.alpha :as s]
    [clojure.test :refer :all]
    [clojure.test.check.generators :as gen]
    [com.gfredericks.test.chuck.clojure-test :refer [checking]]
    [orchestra.spec.test :as st]

    [code-interview.graphs :as graphs]))


(st/instrument)
