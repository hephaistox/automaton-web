(ns automaton-web.duplex.router
  (:require [taoensso.sente :as sente]
            [automaton-core.log :as log]
            [automaton-web.duplex.message-handler :as message-handler]
            [automaton-web.duplex.core :as duplex]))

(defonce router_ (atom nil))

(defn stop-router! "Stop the router component" [] (when-let [stop-f @router_] (stop-f)))

(defn start-router!
  "Start the router component"
  []
  (stop-router!)
  (log/trace "Starting realtime router")
  (reset! router_ (sente/start-client-chsk-router! duplex/ch-chsk message-handler/event-msg-handler)))
