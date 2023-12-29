(ns automaton-web.events.subs
  "UI subscriptions managed by `automaton-web` components.

  All db data required by `automaton-web` should have a subscription to make it possible to be registered here"
  (:require [automaton-web.events-proxy :as web-events-proxy]))

(web-events-proxy/reg-sub ::lang (fn [db _] (:lang db)))

(web-events-proxy/reg-sub ::route-match (fn [db _] (:route-match db)))
