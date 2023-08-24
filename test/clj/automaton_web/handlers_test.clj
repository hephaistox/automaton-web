(ns automaton-web.handlers-test
  (:require
   [automaton-web.handlers :as bewh]
   [clojure.test :refer [deftest is testing]]
   [reitit.ring :as rr]))

(def app
  (rr/ring-handler
   (rr/router
    [["/ping" {:get (constantly {:status 200
                                 :body "ok"})}]
     ["/pong" (constantly nil)]])
   (bewh/default-handlers
    {:not-found (constantly {:status 404, :body "kosh"})
     :not-allowed (constantly {:status 405, :body "kosh"})
     :not-acceptable (constantly {:status 406, :body "kosh"})})))

(deftest default-handlers-test
  (testing "with default http responses"

    (testing "test not-found handler is called and returning 404 status"
      (is (= {:status 404
              :body "kosh"}
             (app {:request-method :get, :uri "/"}))))
    (testing "test not-allowed handler is called and returning 405 status"
      (is (= {:status 405
              :body "kosh"}
             (app {:request-method :post, :uri "/ping"}))))
    (testing "test not-acceptable handler is called and returning 406 status"
      (is (= {:status 406
              :body "kosh"}
             (app {:request-method :get, :uri "/pong"}))))))
