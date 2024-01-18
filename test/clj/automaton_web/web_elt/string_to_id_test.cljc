(ns automaton-web.web-elt.string-to-id-test
  (:require
   [automaton-core.utils.string-to-id :as sut]
   #?(:cljs [cljs.test :refer [deftest is testing] :include-macros true]
      :clj [clojure.test :refer [deftest is testing]])))

(deftest string-to-id-test
  (testing "testing spaces and accent"
    (is (= "page-d-accueil" (sut/string-to-id "Page d'accueil"))))
  (testing "Empty string gives a uuid"
    (is (= 36 (count (sut/string-to-id ""))))))
