(ns automaton-web.pages.portfolio.route
  (:require
   [automaton-web.adapters.be.http-response :as http-response]
   [automaton-web.pages.index :as web-pages-index]))

(defn route
  ([] (route "/js/compiled/share.js"))
  ([share-js]
   ["/portfolio"
    {:summary "Show portfolio"
     :get (fn [request]
            (http-response/ok {"content-type" "text/html;charset=utf8"}
                              (web-pages-index/build
                               request
                               [:div ""]
                               [:script {:type "text/javascript"
                                         :src share-js}]
                               [:script {:type "text/javascript"
                                         :src
                                         "/js/compiled/portfolio.js"}])))}]))
