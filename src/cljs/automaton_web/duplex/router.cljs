(ns automaton-web.duplex.router
  (:require
   [automaton-core.log                   :as core-log]
   [automaton-web.duplex.core            :as duplex]
   [automaton-web.duplex.message-handler :as message-handler]
   [taoensso.sente                       :as sente]))

(defonce router_ (atom nil))

(defn stop-router!
  "Stop the router component"
  []
  (when-let [stop-f @router_] (stop-f)))

(defn start-router!
  "Start the router component"
  []
  (stop-router!)
  (core-log/trace "Starting realtime router")
  (reset! router_ (sente/start-client-chsk-router!
                   duplex/ch-chsk
                   message-handler/event-msg-handler)))
