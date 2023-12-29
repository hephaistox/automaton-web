(ns automaton-web.events.fx
  "UI fx triggered by `automaton-web` components"
  (:require [automaton-core.log :as core-log]
            [automaton-web.adapters.fe.cookies :as fe-cookies]
            [automaton-web.events-proxy :as web-events-proxy]
            [automaton-web.i18n.language :as web-language]))

(web-events-proxy/reg-fx ::set-cookie
                         (fn [[key lang-id]]
                           (let [saved-value (-> (web-language/get-web-lang lang-id)
                                                 :ui-text)]
                             (core-log/trace "Effect set-cookie" saved-value)
                             (fe-cookies/set-cookie key saved-value))))
