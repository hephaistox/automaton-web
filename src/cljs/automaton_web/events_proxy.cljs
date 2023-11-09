(ns automaton-web.events-proxy
  "Adapter for [re-frame](https://day8.github.io/re-frame/api-intro/)"
  (:require [re-frame.core :as rfc]
            [re-frame.db]
            [day8.re-frame.tracing :as tracing]
            [automaton-core.log :as log]))

(def reg-sub rfc/reg-sub)

(def subscribe rfc/subscribe)

(defn subscribe-value
  "return the value of the subscription
  Is safe even if the query does not match any subscription
  Params:
  * `query` subscription query (see [API](https://day8.github.io/re-frame/api-re-frame.core/#subscribe) for details)"
  [query]
  (some-> (rfc/subscribe query)
          deref))

(def reg-fx rfc/reg-fx)

(def reg-event-db rfc/reg-event-db)

(def reg-event-fx rfc/reg-event-fx)

(defn dispatch [event] (log/trace "Event `" event "`") (rfc/dispatch event))

(def dispatch-sync rfc/dispatch-sync)

(def inject-cofx rfc/inject-cofx)

(def clear-subscription-cache! rfc/clear-subscription-cache!)

(defn assert-db-populated
  "Assert if the db has been populated"
  []
  (when (empty? @re-frame.db/app-db) (log/error "Re-frame is not correctly initialized, db is empty")))

(defn client-app-db-init!
  "To be called during cust-app init to init re-frame
  Params:
  * `init-db-evt` keyword of the event to init the db"
  [init-db-evt]
  (clear-subscription-cache!)
  (rfc/dispatch-sync [init-db-evt])
  (assert-db-populated) ;; Check init has been done synchroneously and
                        ;; before
)

(defmacro fn-traced [& definition] `(tracing/fn-traced ~@definition))
