(ns automaton-web.components.file-loader-test
  (:require [automaton-web.components.file-loader :as sut]
            [cljs.test :refer [deftest is testing] :include-macros true]))

(deftest component (testing "No parameter raises an error" (is (= [:div "Error in the component parameters"] (sut/component {})))))
