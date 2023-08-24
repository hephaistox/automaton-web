(ns automaton-web.endpoint.web.dev-route
  "Defines the specific dev routes.
   That routes should only be used in the dev environment"
  (:require
   [automaton-web.pages.index :as index]
   [automaton-web.pages.admin :as admin-index]))

(defn route
  []
  ["admin"
   {:summary "Admin subdir"}
   [""
    {:get (fn [request]
            (let [admin-content (admin-index/index request)]
              {:status 200
               :headers {"content-type" "text/html;charset=utf8"}
               :body (-> request
                         (index/build admin-content))}))}]
   ["/throw-exception"
    {:summary "Raise intentionally an exception"
     :get (fn [_request] (throw (ex-info "This exception is raised intentionally" {:to "check"
                                                                                   :what "happens"})))}]
   ["/devcards"
    {:summary "Show devcards"
     :get (fn [request]
            {:status 200
             :headers {"content-type" "text/html;charset=utf8"}
             :body (-> request
                       (index/build
                        ;; Disable additional devcards styling to see our design
                        {:header-elements [[:style {:id "com-rigsomelight-devcards-addons-css"}]
                                           [:style {:id "com-rigsomelight-code-highlight-css"}]
                                           [:style {:id "com-rigsomelight-edn-css"}]]}
                        [:div ""]
                        [:script {:type "text/javascript"
                                  :src "/js/compiled/share.js"}]
                        [:script {:type "text/javascript"
                                  :src "/js/compiled/devcards.js"}]))})}]])
