(ns automaton-web.duplex.router
  (:require
   [taoensso.sente :as sente]

   [automaton-web.log :as log]
   [automaton-web.duplex.message-handler :as msg-handler]
   [automaton-web.duplex.core :as realtime]))

(defonce router_ (atom nil))

(defn  stop-router! [] (when-let [stop-f @router_] (stop-f)))

(defn start-router! []
  (stop-router!)
  (log/trace "Starting realtime router")
  (reset! router_
          (sente/start-client-chsk-router!
           realtime/ch-chsk msg-handler/event-msg-handler)))
