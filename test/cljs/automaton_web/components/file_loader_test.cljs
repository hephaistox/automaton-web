(ns automaton-web.components.file-loader-test
  (:require
   [clojure.test :refer [deftest is testing]]

   [automaton-web.components.file-loader :as sut]
   ))

(deftest component
  (testing "No parameter raises an error"
      (is (= [:div "Error in the component parameters"]
             (sut/component {})
             ))))
