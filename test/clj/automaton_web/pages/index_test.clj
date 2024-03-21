(ns automaton-web.pages.index-test
  (:require
   [automaton-web.pages.index :as sut]
   [clojure.test              :refer [deftest is testing]]))

;;The antiforgery is tested in the mocked http server
(deftest index-html
  (testing "index html not raising exception"
    (is (re-seq #"(?s)DOCTYPE.*html(?s).*body" (sut/build {} [:foo :bar])))))
