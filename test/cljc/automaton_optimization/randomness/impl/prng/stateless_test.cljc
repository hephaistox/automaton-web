(ns automaton-optimization.randomness.impl.prng.stateless-test
  (:require
   #?(:clj [clojure.test :refer [is testing deftest]]
      :cljs [cljs.test :refer [is testing deftest] :include-macros true])
   [automaton-optimization.randomness.impl.prng.stateless :as sut]))

(defn test-jump
  [stateless-prng]
  (testing "Jump executes without errors." (is (sut/jump stateless-prng))))

(defn test-rnd-range
  [stateless-prng]
  (testing "Returns randomness range without errors." (is (sut/rnd-range stateless-prng))))

(defn test-seed
  [stateless-prng]
  (testing "Returns the seed without errors." (is (sut/seed stateless-prng))))

(defn test-peek-rnd
  [stateless-prng]
  (testing "Test `peek-rnd` execution is ok." (is (sut/peek-rnd stateless-prng))))

(defn test-next
  [stateless-prng]
  (testing "Test `next` changes the value."
    (is (not= (sut/peek-rnd stateless-prng)
              (-> stateless-prng
                  sut/next
                  sut/peek-rnd)))))

(defn is-stateless-test
  [stateless-prng1]
  (let [stateless-prng2 stateless-prng1
        v1 (sut/peek-rnd stateless-prng1)
        _ (-> stateless-prng1
              sut/next
              sut/next
              sut/next)
        v2 (sut/peek-rnd stateless-prng2)]
    (testing "The `prng` does not remember states." (is (= v1 v2)))))

(defn test-all
  "Assemble all tests. Returns the collection of returns of all tests.
  Note that if the call is wrapped in deftest, the report will be built."
  [stateless-prng]
  ((juxt test-jump test-rnd-range test-seed test-peek-rnd test-next is-stateless-test)
   stateless-prng))

(defrecord PRNGStatelessSTUB [vals]
  sut/PRNGStateless
    (jump [_]
      (PRNGStatelessSTUB. (-> vals
                              rest
                              rest
                              rest)))
    (rnd-range [_] [10 20])
    (seed [_] 1234)
    (peek-rnd [_] (first vals))
    (next [_] (PRNGStatelessSTUB. (rest vals))))

(deftest prngstateless-test
  (testing "Test stub" (is (test-all (->PRNGStatelessSTUB [10 12 11 19 15])))))
