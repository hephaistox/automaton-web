(ns automaton-web.duplex.routes-test
  (:require
   [automaton-web.adapters.be.http-response :as http-response]
   [automaton-web.duplex.route              :as duplex-route]
   [automaton-web.duplex.routes             :as sut]
   [clojure.test                            :refer [deftest is testing]]
   [reitit.ring                             :as reitit-ring]))

(deftest route2
  (let [router (reitit-ring/router (sut/routes*
                                    (constantly (http-response/ok "stub"))
                                    (constantly (http-response/ok "stub"))))
        app (reitit-ring/ring-handler router)
        page (app {:request-method :get
                   :query-string
                   "client-id=d184907d-f1ee-491c-a49f-eb21fed56c1f"
                   :headers {"accept-encoding" "gzip, deflate, br"}
                   :uri duplex-route/duplex-uri})]
    (testing "Session are managed" (is (get-in page [:headers "Set-Cookie"])))
    (testing "Antiforgery token"
      (is (get-in page [:request-copied :anti-forgery-token])))
    (testing "Wrap params is keyed wrap-params and wrap-keyword-params"
      (is (keyword? (ffirst (get-in page [:request-copied :params])))))))
