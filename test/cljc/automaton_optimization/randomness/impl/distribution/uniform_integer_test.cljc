(ns automaton-optimization.randomness.impl.distribution.uniform-integer-test
  (:require
   #?(:clj [clojure.test :refer [deftest is testing]]
      :cljs [cljs.test :refer [deftest is testing] :include-macros true])
   [automaton-optimization.maths                                        :as opt-maths]
   [automaton-optimization.randomness.distribution                      :as opt-proba-distribution]
   [automaton-optimization.randomness.impl.distribution.uniform-integer :as sut]
   [automaton-optimization.randomness.impl.prng.xoroshiro128            :as opt-prng-xoro]))

(deftest uniform-test
  (testing "Uniform distribution returns elements in the range"
    (is (every? int?
                (repeatedly 10
                            (fn []
                              (-> (opt-prng-xoro/make)
                                  (sut/make 2 60)
                                  opt-proba-distribution/draw))))))
  (testing "Uniform distribution returns elements in the range"
    (is (= 31
           (-> (opt-prng-xoro/make)
               (sut/make 2 60)
               opt-proba-distribution/median)))
    (is (= #?(:clj 5/2
              :cljs 2.5)
           (-> (opt-prng-xoro/make)
               (sut/make 2 3)
               opt-proba-distribution/median))))
  (testing "Test uniform cumulative"
    (is (zero? (-> (opt-prng-xoro/make)
                   (sut/make 3 12)
                   (opt-proba-distribution/cumulative 3))))
    (is (= #?(:clj 1/2
              :cljs 0.5)
           (-> (opt-prng-xoro/make)
               (sut/make 3 13)
               (opt-proba-distribution/cumulative 8))))
    (is (= 1.0
           (-> (opt-prng-xoro/make)
               (sut/make 3 12)
               (opt-proba-distribution/cumulative 12)))))
  (testing "minimum"
    (is (= 3
           (-> (opt-prng-xoro/make)
               (sut/make 3 12)
               opt-proba-distribution/minimum))))
  (testing "maximum"
    (is (= 12
           (-> (opt-prng-xoro/make)
               (sut/make 3 12)
               opt-proba-distribution/maximum))))
  (testing "quantile"
    (is (opt-maths/approx= 4.8
                           (-> (opt-prng-xoro/make)
                               (sut/make 3 12)
                               (opt-proba-distribution/quantile 0.2))
                           0.001))
    (is (opt-maths/approx= 7.5
                           (-> (opt-prng-xoro/make)
                               (sut/make 3 12)
                               (opt-proba-distribution/quantile 0.5))
                           0.001)))
  (testing "Test uniform interquartile"
    (is (= 4.5
           (-> (opt-prng-xoro/make)
               (sut/make 3 12)
               opt-proba-distribution/iqr))))
  (testing "Test uniform interquartile"
    (is (= {:iqr 4.5
            :min 3
            :q1 5.25
            :median 7.5
            :q3 9.75
            :max 12}
           (-> (opt-prng-xoro/make)
               (sut/make 3 12)
               opt-proba-distribution/summary)))))
