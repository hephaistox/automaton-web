(ns automaton-web.duplex.routes
  "Routes for realtime discussion between server and frontend"
  (:require
   [automaton-core.log         :as core-log]
   [automaton-web.duplex.core  :as duplex]
   [automaton-web.duplex.route :as duplex-route]
   [automaton-web.duplex.session]
   [automaton-web.middleware   :as web-middleware]
   [mount.core                 :refer [defstate]]))

(defn routes*
  ([] (routes* (duplex/ajax-get-or-ws-handshake) (duplex/ajax-post)))
  ([get-handler put-handler]
   (let [route [duplex-route/duplex-uri {:summary "Realtime channel"
                                         :middleware
                                         [web-middleware/wrap-session
                                          web-middleware/wrap-anti-forgery
                                          web-middleware/parameters-middleware
                                          web-middleware/wrap-keyword-params
                                          web-middleware/wrap-copy-params]
                                         :get {:handler get-handler}
                                         :put {:handler put-handler}}]]
     route)))

(defstate routes
          :start
          (do (core-log/info "Starting realtime routes")
              (let [res (routes*)]
                (core-log/trace "Realtime routes are started")
                res)))
