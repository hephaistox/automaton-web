(ns automaton-optimization.randomness.impl.prng.built-in-test
  (:require
   #?(:clj [clojure.test :refer [deftest is testing]]
      :cljs [cljs.test :refer [deftest is testing] :include-macros true])
   [automaton-core.utils.call-limit                           :as core-call-limit]
   [automaton-optimization.randomness.impl.prng.built-in      :as sut]
   [automaton-optimization.randomness.impl.prng.stateful      :as opt-prng-stateful]
   [automaton-optimization.randomness.impl.prng.stateful-test :as opt-prng-stateful-test]
   [automaton-optimization.randomness.impl.prng.tests         :as opt-prng-tests]))

(deftest built-in-test
  (testing "Is built-in a stateful prng?"
    (opt-prng-stateful-test/test-non-repeatable (sut/make))
    (core-call-limit/remove-fn-call ::sut/are-global)))



(deftest test-uniformity
  (testing "Uniformity of test is below 3"
    (is (nil? (opt-prng-tests/distribution-uniformity
               (opt-prng-stateful/draw-ints (sut/make) 100000 0 30)
               3)))
    (core-call-limit/remove-fn-call ::sut/are-global)))
