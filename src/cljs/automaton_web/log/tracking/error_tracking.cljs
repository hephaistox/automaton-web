(ns automaton-web.log.tracking.error-tracking
  "Web error tracking related code."
  (:require [automaton-web.log.tracking.sentry :as sentry]))

(defn init-error-tracking!
  "Initializes error tracking, currently focused on sentry."
  [{:keys [dsn traced-website env]}]
  (sentry/init-sentry! {:dsn dsn
                        :traced-website traced-website
                        :env env}))
