(ns automaton-optimization.randomness.impl.distribution.exponential-test
  (:require
   #?@(:clj [[clojure.test :refer [deftest is testing]]]
       :cljs [[cljs.test :refer [deftest is testing] :include-macros true]])
   [automaton-optimization.maths                                    :as opt-maths]
   [automaton-optimization.randomness.distribution                  :as opt-proba-distribution]
   [automaton-optimization.randomness.impl.distribution.exponential :as sut]
   [automaton-optimization.randomness.impl.prng.xoroshiro128        :as opt-prng-xoro]))

(def prng-stub (opt-prng-xoro/make))

(deftest draw-test
  (testing "Test for draw"
    (is (double? (-> (sut/make prng-stub 2.0)
                     opt-proba-distribution/draw)))))

(deftest median-test
  (testing "Is the median of exponential 2 is (ln 2)/lambda"
    (is (opt-maths/approx= (-> (sut/make prng-stub 2.0)
                               opt-proba-distribution/median)
                           0.34657
                           0.00001))))

(deftest cumulative-test
  (testing "Cumulative"
    (is (opt-maths/approx= (opt-proba-distribution/cumulative (sut/make prng-stub 2.0) 0.4)
                           0.5506
                           0.0001))))

(deftest minimun-test
  (testing "Minimum" (is (zero? (opt-proba-distribution/minimum (sut/make prng-stub 2.0))))))

(deftest maximun-test
  (testing "Maximum"
    (is (opt-maths/infinite? (opt-proba-distribution/maximum (sut/make prng-stub 2.0))))))

(deftest quantile-test
  (testing "Quantile"
    (is (double? (opt-proba-distribution/quantile (sut/make prng-stub 2.0) 0.3)))))
