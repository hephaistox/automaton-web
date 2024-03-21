(ns automaton-web.adapters.be.http-response-test
  (:require
   [automaton-web.adapters.be.http-response :as sut]
   [clojure.test                            :refer [deftest is testing]]))

(deftest default-handlers-test
  (testing "with default http responses"
    (testing "test ok is returning 200"
      (is (= {:status 200
              :headers {}
              :body "test"}
             (sut/ok "test"))))
    (testing "text ok is returning headers that are passed"
      (is (= {:status 200
              :headers {"content-type" "text"}
              :body "test"}
             (sut/ok {"content-type" "text"} "test"))))
    (testing "test no-content is returning 204"
      (is (= {:status 204
              :headers {}
              :body nil}
             (sut/no-content))))
    (testing "test not-found handler is called and returning 404 status"
      (is (= {:status 404
              :headers {}
              :body "not-found"}
             (sut/not-found "not-found"))))
    (testing
      "test method-not-allowed handler is called and returning 405 status"
      (is (= {:status 405
              :headers {}
              :body "method-not-allowed"}
             (sut/method-not-allowed "method-not-allowed"))))
    (testing "test not-acceptable handler is called and returning 404 status"
      (is (= {:status 406
              :headers {}
              :body "not-acceptable"}
             (sut/not-acceptable "not-acceptable"))))))
