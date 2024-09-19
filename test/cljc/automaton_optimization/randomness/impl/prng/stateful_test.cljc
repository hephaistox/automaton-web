(ns automaton-optimization.randomness.impl.prng.stateful-test
  (:require
   #?@(:clj [[clojure.test :refer [is testing deftest]]]
       :cljs [[cljs.test :refer [is testing deftest] :include-macros true]])
   [automaton-core.adapters.schema                           :as core-schema]
   [automaton-optimization.randomness.impl.prng.stateful     :as sut]
   [automaton-optimization.randomness.impl.prng.xoroshiro128 :as opt-prng-xoro]))

(defn test-duplicate
  [stateful-prng]
  (testing "Is duplicate creates a prng starting again at the seed."
    (let [first-rnd (-> stateful-prng
                        sut/rnd)
          prng-dup (sut/duplicate stateful-prng)]
      (is (= first-rnd (sut/rnd prng-dup))))))

(defn test-jump
  [stateful-prng]
  (testing "Jump executes without errors." (is (sut/jump stateful-prng))))

(defn test-rnd-range
  [stateful-prng]
  (testing "Returns randomness range without errors." (is (sut/rnd-range stateful-prng))))

(defn test-seed
  [stateful-prng]
  (testing "Returns the seed without errors." (is (sut/seed stateful-prng))))

(defn test-reset
  [stateful-prng]
  (testing "Reset comes back to the seed so rnd returns same values."
    (sut/reset stateful-prng)
    (let [first-val (sut/rnd stateful-prng)
          _ (sut/reset stateful-prng)
          second-attempt (sut/rnd stateful-prng)]
      (is (= first-val second-attempt)))))

(defn is-stateful-test
  [stateful-prng]
  (testing "Are rnd differents?"
    (is (not= (sut/rnd stateful-prng)
              (sut/rnd stateful-prng)
              (sut/rnd stateful-prng)
              (sut/rnd stateful-prng)))))

(defn test-all
  [stateful-prng]
  ((juxt test-duplicate is-stateful-test test-jump test-rnd-range test-seed test-reset)
   stateful-prng))

(defn test-non-repeatable
  [stateful-prng]
  ((juxt is-stateful-test test-jump test-rnd-range test-seed) stateful-prng))

(deftest as-int-test
  (testing "Is generated `as-int` actually an Integer."
    (is (integer? (sut/as-int (opt-prng-xoro/make) 10 16)))))

(deftest as-double-test
  (testing "Is generated `as-int` actually a Double."
    (is (double? (sut/as-double (opt-prng-xoro/make) 10 16)))))

(deftest draw-ints-test
  (testing "Are integer drawn in the range."
    (is (nil? (core-schema/validate-data-humanize [:sequential int?]
                                                  (sut/draw-ints (opt-prng-xoro/make) 10 0 100))))))
