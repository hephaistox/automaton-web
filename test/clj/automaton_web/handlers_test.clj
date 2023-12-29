(ns automaton-web.handlers-test
  (:require [automaton-web.handlers :as sut]
            [automaton-web.middleware :as web-middleware]
            [automaton-web.adapters.be.http-response :as http-response]
            [automaton-web.i18n.be.translator.tempura :as be-tempura-translator]
            [automaton-web.i18n.dict.text :as web-dict-text]
            [automaton-web.i18n.dict.resources :as web-dict-resources]
            [clojure.test :refer [deftest is testing]]
            [reitit.ring :as reitit-ring]))

(def be-translator-stub (be-tempura-translator/make-tempura-be-translator [:fr] web-dict-text/dict web-dict-resources/dict))

(def app
  (reitit-ring/ring-handler (reitit-ring/router [["/ping" {:get (constantly (http-response/ok "ok"))}] ["/pong" (constantly nil)]])
                            (sut/default-handlers
                             {:not-found (fn [request] (assoc (http-response/not-found "kosh") :tr-present? (boolean (:tr request))))
                              :not-allowed (constantly (http-response/method-not-allowed "kosh"))
                              :not-acceptable (constantly (http-response/not-acceptable "kosh"))}
                             (web-middleware/translation-middlewares be-translator-stub))))

(deftest default-handlers-test
  (testing "test not-found handler is called and returning 404 status"
    (is (= {:status 404
            :body "kosh"
            :tr-present? true}
           (select-keys (app {:request-method :get
                              :uri "/"})
                        [:status :body :tr-present?]))))
  (testing "test not-allowed handler is called and returning 405 status"
    (is (= {:status 405
            :body "kosh"}
           (select-keys (app {:request-method :post
                              :uri "/ping"})
                        [:status :body :tr-present?]))))
  (testing "test not-acceptable handler is called and returning 406 status"
    (is (= {:status 406
            :body "kosh"}
           (select-keys (app {:request-method :get
                              :uri "/pong"})
                        [:status :body :tr-present?])))))

(deftest translation-middlewares-test
  (testing "Is the translation middleware added?"
    (is (= {:status 404
            :body "kosh"
            :tr-present? true}
           (select-keys (app {:request-method :get
                              :uri "/not-existing-page"})
                        [:status :body :tr-present?])))))

(deftest apply-middlewares-test
  (testing "Wrapper are able to modify the request"
    (is (= {:foo 3}
           ((sut/apply-middlewares (fn [request] {:foo (:foo-r request)})
                                   [(fn [handler]
                                      (fn [request]
                                        (-> (update request :foo-r inc)
                                            handler)))
                                    (fn [handler]
                                      (fn [request]
                                        (-> (update request :foo-r inc)
                                            handler)))])
            {:foo-r 1}))))
  (testing "Wrapper are compatible with compiled middlewares"
    (is (= {:foo 1}
           ((sut/apply-middlewares (fn [request] {:foo (:foo-r request)})
                                   [automaton-web.middleware/wrap-cookies
                                    {:name :reitit.ring.middleware.parameters/parameters
                                     :wrap automaton-web.middleware/wrap-cookies}])
            {:foo-r 1})))))
