(ns automaton-web.pages.admin.route
  (:require [automaton-web.adapters.be.http-response :as http-response]
            [automaton-web.hiccup :as web-hiccup]
            [automaton-web.pages.index :as web-pages-index]
            [automaton-web.pages.admin :as web-admin-index]
            [automaton-web.configuration :as web-conf]))

(defn route
  []
  [""
   {:get (fn [request]
           (let [admin-content (web-admin-index/admin-page)]
             (http-response/ok {"content-type" "text/html;charset=utf8"}
                               (-> request
                                   (merge {:header-elements [(web-hiccup/js-script-raw (web-conf/config-web-reference))]})
                                   (web-pages-index/build admin-content)))))}])
