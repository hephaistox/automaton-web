(ns automaton-web.adapters.fe.cookies-test
  (:require
   [automaton-web.adapters.fe.cookies :as sut]
   [cljs.test :refer [deftest is testing] :include-macros true]))

(deftest cookie-test
  (testing "Set a cookie"
    (is (nil? (sut/set-cookie "automaton-web.test" "for frontend cookies")))
    (is (= "for frontend cookies" (sut/get-cookie-val "automaton-web.test")))))
