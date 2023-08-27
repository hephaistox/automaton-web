(ns automaton-web.middleware-test
  (:require
   [automaton-web.middleware :as sut]
   [clojure.test :refer [deftest is testing]]
   [reitit.ring :as rr]))

(defn handler-test
  [request]
  (assoc request
         :foo :bar))

(defn middleware-env-mock [middleware]
  (rr/ring-handler
   (rr/router
    ["/ping" {:get #(select-keys % [:params :query-params])}]
    {:data {:middleware middleware}})))

(deftest wrap-deny-frame-test
  (let [app (sut/wrap-deny-frame handler-test)]
    (testing "wrap is still a function"
      (is (fn? app)))
    (testing "wrap is still a function"
      (is (= {:bar2 :foo2
              :headers {"X-Frame-Options" "DENY"}
              :foo :bar}
             (app {:bar2 :foo2}))))))

(deftest wrap-copy-params-test
  (let [app (sut/wrap-copy-params handler-test)]
    (testing "wrap is still a function"
      (is (fn? app)))
    (testing "wrap-copy-params is adding :request-copied params"
      (is (= {:bar2 :foo2
              :request-copied {:bar2 :foo2}
              :foo :bar}
             (app {:bar2 :foo2}))))))

(deftest wrap-transit-test
  (let [app (sut/wrap-transit handler-test)]
    (testing "wrap is still a function"
      (is (fn? app)))
    (testing "wrap is adding the header"
      (is (= {:bar2 :foo2
              :headers {"Accept" "application/transit+json"}
              :foo :bar}
             (app {:bar2 :foo2}))))))

(deftest wrap-session-test
  (testing "wrap-session is a function"
    (is (fn? sut/wrap-session))))

(deftest kw-and-params-test
  (let [h (middleware-env-mock [sut/parameters-middleware])]
    (testing "(Query) Params handler is working - parameters-middleware contract"
      (is (= {:params {"a" "b"}
              :query-params {"a" "b"}}
             (h {:request-method :get
                 :uri "/ping"
                 :query-string "a=b"})))))
  (let [h (middleware-env-mock [sut/wrap-keyword-params
                                sut/parameters-middleware])]
    (testing "Params and keyword params are working"
      (is (= {:params {:a "b"} :query-params {}}
             (h {:request-method :get
                 :uri "/ping"
                 :params {"a" "b"}}))))))

(deftest wrap-cookies-test
  (let [req  {:headers {"cookie" "a=b"}}
        resp ((sut/wrap-cookies :cookies) req)]
    (testing "cookies middleware contract"
      (is (= {"a" {:value "b"}}
             resp)))))
