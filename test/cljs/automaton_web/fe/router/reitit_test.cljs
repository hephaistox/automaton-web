(ns automaton-web.fe.router.reitit-test
  (:require
   [automaton-web.fe.router :as web-fe-router]
   [automaton-web.fe.router.reitit :as sut]
   [cljs.test :refer [deftest is testing] :include-macros true]))

(def reitit-router
  (sut/make-reitit-router ["/foo"
                           {:name ::foo
                            :panel-id ::get-fe-foo}]
                          (constantly {})))

(deftest make-reitit-router-test
  (testing "testing matching route"
    (is (= ::get-fe-foo
           (->> "/foo"
                (web-fe-router/match-from-url reitit-router)
                (web-fe-router/panel-id reitit-router)))))
  (testing "testing not matching route"
    (is (= :panels/not-found
           (->> "/bar"
                (web-fe-router/match-from-url reitit-router)
                (web-fe-router/panel-id reitit-router))))))
