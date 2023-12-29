(ns automaton-web.log.tracking.error-tracking
  "Web error tracking related code."
  (:require [automaton-web.log.tracking.sentry :as sentry]))

(defn init-error-tracking!
  "Initializes error tracking, currently focused on sentry."
  [{:keys [dsn traced-website env]}]
  (when-not dsn (prn "dsn is missing in init-error-tracking!"))
  (when-not env (prn "env is missing in init-error-tracking!"))
  (sentry/init-sentry! {:dsn dsn
                        :traced-website traced-website
                        :env env}))
