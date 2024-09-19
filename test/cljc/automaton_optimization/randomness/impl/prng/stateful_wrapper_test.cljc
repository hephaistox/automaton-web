(ns automaton-optimization.randomness.impl.prng.stateful-wrapper-test
  (:require
   [automaton-optimization.randomness.impl.prng.stateful-test    :as opt-prng-stateful-test]
   [automaton-optimization.randomness.impl.prng.stateful-wrapper :as sut]
   #?(:clj [clojure.test :refer [is testing deftest]]
      :cljs [cljs.test :refer [is testing deftest] :include-macros true])
   [automaton-optimization.randomness.impl.prng.stateless-test   :as opt-prng-stateless-test]))

(deftest StatefulImpl-test
  (testing "Is the built stateful prng pass tests?"
    (is (-> (opt-prng-stateless-test/->PRNGStatelessSTUB [10 12 14 11])
            sut/make
            opt-prng-stateful-test/test-all))))
