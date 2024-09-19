(ns automaton-optimization.randomness.impl.prng.xoroshiro128-test
  (:require
   #?(:clj [clojure.test :refer [deftest is testing]]
      :cljs [cljs.test :refer [deftest is testing] :include-macros true])
   [automaton-optimization.randomness.impl.prng.stateful       :as opt-prng-stateful]
   [automaton-optimization.randomness.impl.prng.stateless-test :as opt-prng-stateless-test]
   [automaton-optimization.randomness.impl.prng.tests          :as opt-prng-tests]
   [automaton-optimization.randomness.impl.prng.xoroshiro128   :as sut]))

(deftest xoro-test
  (testing "Stateless tests for xoro are passing\n"
    (opt-prng-stateless-test/test-all (sut/make-stateless))))

(deftest test-uniformity
  (testing "Uniformity of test is below 3"
    (is (nil? (opt-prng-tests/distribution-uniformity
               (opt-prng-stateful/draw-ints (sut/make) 100000 0 11)
               3)))))
