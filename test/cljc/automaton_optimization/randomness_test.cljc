(ns automaton-optimization.randomness-test
  (:require
   [automaton-core.adapters.schema    :as core-schema]
   #?@(:clj [[clojure.test :refer [deftest is testing]]]
       :cljs [[cljs.test :refer [deftest is testing] :include-macros true]])
   [automaton-optimization.randomness :as sut]))

(deftest as-int-pair-test
  (testing "Is generated `as-int` actually an Integer."
    (is (nil? (core-schema/validate-data-humanize [:sequential int?]
                                                  (sut/as-int-pair (sut/xoroshiro128) 10 16))))))
