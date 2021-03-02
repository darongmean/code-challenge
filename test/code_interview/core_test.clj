(ns code-interview.core-test
  (:require
    [clojure.spec.alpha :as s]
    [clojure.test :refer :all]

    [code-interview.core :refer :all]

    [orchestra.spec.test :as st]))


(s/check-asserts true)

(st/instrument)
