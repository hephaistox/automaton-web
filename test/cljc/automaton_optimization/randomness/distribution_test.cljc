(ns automaton-optimization.randomness.distribution-test
  (:require
   [automaton-optimization.randomness.distribution              :as sut]
   #?@(:clj [[clojure.test :refer [deftest is testing]]]
       :cljs [[cljs.test :refer [deftest is testing] :include-macros true]])
   [automaton-optimization.randomness.impl.distribution.uniform :as opt-uniform]
   [automaton-optimization.randomness.impl.prng.xoroshiro128    :as opt-prng-xoro]))

(deftest iqr-test
  (testing "Inter quartile of uniform distribution is ok."
    (is (= 4.0
           (-> (opt-prng-xoro/make)
               (opt-uniform/make 7 15)
               sut/iqr)))))

(deftest summary-test
  (testing "Summary of uniform distribution is ok."
    (is (= {:min 1
            :q1 25.75
            :median 50.5
            :q3 75.25
            :max 100
            :iqr 49.5}
           (-> (opt-prng-xoro/make)
               (opt-uniform/make 1 100)
               sut/summary)))))

(deftest critical-value-test
  (testing "Critical value of uniform distribution is ok."
    (is (= 97.5
           (-> (opt-prng-xoro/make)
               (opt-uniform/make 0 100)
               sut/critical-value)))))
