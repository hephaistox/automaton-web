(ns automaton-web.events-proxy
  (:require [re-frame.core :as rfc]))

(def reg-sub
  rfc/reg-sub)

(def subscribe
  rfc/subscribe)

(def reg-fx
  rfc/reg-fx)

(def reg-event-db
  rfc/reg-event-db)

(def reg-event-fx
  rfc/reg-event-fx)

(def dispatch
  rfc/dispatch)

(def inject-cofx
  rfc/inject-cofx)

(def clear-subscription-cache!
  rfc/clear-subscription-cache!)

(defn client-app-db-init!
  [kw]
  (clear-subscription-cache!)
  (rfc/dispatch-sync [kw]))
