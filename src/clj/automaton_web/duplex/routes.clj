(ns automaton-web.duplex.routes
  "Routes for realtime discussion between server and frontend"
  (:require [mount.core :refer [defstate]]
            [automaton-web.duplex.session]
            [automaton-web.duplex.core :as duplex]
            [automaton-web.middleware :as web-middleware]
            [automaton-core.log :as log]
            [automaton-web.duplex.route :as duplex-route]))

(defn routes*
  ([] (routes* (duplex/ajax-get-or-ws-handshake) (duplex/ajax-post)))
  ([get-handler put-handler]
   (let [route [duplex-route/duplex-uri
                {:summary "Realtime channel"
                 :middleware [web-middleware/wrap-session web-middleware/wrap-anti-forgery web-middleware/parameters-middleware
                              web-middleware/wrap-keyword-params web-middleware/wrap-copy-params]
                 :get {:handler get-handler}
                 :put {:handler put-handler}}]]
     route)))

(defstate routes
          :start
          (do (log/info "Starting realtime routes")
              (let [res (routes*)]
                (log/trace "Realtime routes are started")
                res)))
