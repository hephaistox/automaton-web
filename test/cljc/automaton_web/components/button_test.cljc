(ns automaton-web.components.button-test
  (:require #?(:clj [clojure.test :refer [deftest is testing]]
               :cljs [cljs.test :refer [deftest is testing] :include-macros true])
            [automaton-web.components.button :as sut]))

(deftest component
  (testing "Url set" (is (sut/button {:url "/"})))
  (testing "on click set" (is (sut/button {:on-click #(fn [_] 1)})))
  (testing "Url and on-click no set trigger an exception" (is (sut/button {}))))
