(ns automaton-web.duplex.routes
  "Routes for realtime discussion between server and frontend"
  (:require
   [mount.core :refer [defstate]]
   [automaton-web.duplex.session]
   [automaton-web.duplex.core :as rt]
   [automaton-web.middleware :as web-middleware]
   [automaton-web.log :as log]
   [automaton-web.duplex.route :as cst]))

(defn routes*
  ([]
   (routes* (rt/ajax-get-or-ws-handshake)
            (rt/ajax-post)))
  ([get-handler put-handler]
   (let [route [cst/realtime-uri
                {:summary "Realtime channel"
                 :middleware [web-middleware/wrap-session
                              web-middleware/wrap-anti-forgery
                              web-middleware/parameters-middleware
                              web-middleware/wrap-keyword-params
                              web-middleware/wrap-copy-params]
                 :get {:handler get-handler}
                 :put {:handler put-handler}}]]
     route)))

(defstate routes
  :start (do
           (log/info "Starting realtime routes")
           (let [res (routes*)]
             (log/trace "Realtime routes are started")
             res)))
