(ns automaton-optimization.sample.vector-test
  (:require
   #?@(:clj [[clojure.test :refer [deftest is testing]]]
       :cljs [[cljs.test :refer [deftest is testing] :include-macros true]])
   [automaton-optimization.maths                         :as opt-maths]
   [automaton-optimization.randomness.impl.sample.vector :as sut]
   [automaton-optimization.randomness.sample             :as opt-sample-data]))

(deftest SampleVector-test
  (testing "Sample data vector is running"
    (is (= 2
           (-> (sut/make [:a :b])
               opt-sample-data/n)))
    (is (= 5
           (-> (sut/make [4 6])
               opt-sample-data/average)))
    (is (opt-maths/approx= 1
                           (-> (sut/make [4 6])
                               opt-sample-data/variance)
                           0.0001))
    (is (= 5
           (-> (sut/make [4 6])
               opt-sample-data/median)))))
