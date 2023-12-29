(ns automaton-web.adapters.be.http-request-test
  (:require [automaton-web.adapters.be.http-request :as sut]
            [clojure.test :refer [deftest is testing]]))

(deftest cookies-language-test
  (testing "Empty query should pass silently" (is (nil? (sut/cookies-language {}))))
  (testing "Test nil values"
    (is (nil? (sut/cookies-language {:cookies {:value {"lang" nil}}})))
    (is (nil? (sut/cookies-language {:cookies (:value {"lang" "null"})}))))
  (testing "Keywords are stored" (is (= :foo (sut/cookies-language {:cookies {"lang" {:value "foo"}}})))))
