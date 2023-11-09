(ns automaton-web.endpoint.web.dev-route
  "Defines the specific dev routes.
   That routes should only be used in the dev environment"
  (:require [automaton-core.adapters.be.http-response :as response]
            [automaton-web.pages.index :as index]
            [automaton-web.pages.admin :as admin-index]))

(defn route
  []
  ["/admin" {:summary "Admin subdir"}
   [""
    {:get (fn [request]
            (let [admin-content (admin-index/index request)]
              (response/ok {"content-type" "text/html;charset=utf8"}
                           (-> request
                               (index/build admin-content)))))}]
   ["/throw-exception"
    {:summary "Raise intentionally an exception"
     :get (fn [_request]
            (throw (ex-info "This exception is raised intentionally"
                            {:to "check"
                             :what "happens"})))}]
   ["/devcards"
    {:summary "Show devcards"
     :get (fn [request]
            (response/ok {"content-type" "text/html;charset=utf8"}
                         (index/build (merge request
                                             ;; Disable additional devcards styling to see our
                                             ;; design
                                             {:header-elements [[:style {:id "com-rigsomelight-devcards-addons-css"}]
                                                                [:style {:id "com-rigsomelight-code-highlight-css"}]
                                                                [:style {:id "com-rigsomelight-edn-css"}]]})
                                      [:div ""]
                                      [:script
                                       {:type "text/javascript"
                                        :src "/js/compiled/share.js"}]
                                      [:script
                                       {:type "text/javascript"
                                        :src "/js/compiled/devcards.js"}])))}]])
