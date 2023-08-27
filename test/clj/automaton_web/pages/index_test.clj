(ns automaton-web.pages.index-test
  (:require
   [clojure.test :refer [deftest is testing]]

   [automaton-web.pages.index :as sut]))

;;The antiforgery is tested in the mocked http server
(deftest index-html
  (testing "index html not raising exception"
    (is (sut/build {} [:foo :bar]))))
