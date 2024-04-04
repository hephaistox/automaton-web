(ns automaton-web.log.tracking.sentry
  "Sentry for web"
  (:require
   ["@sentry/react"    :as Sentry]
   ["react-router-dom" :refer (useLocation useNavigationType
                                           createRoutesFromChildren
                                           matchRoutes)]
   [react              :as react]))

(defn init-sentry!
  "Initialize sentry for react, which is recording react errors that happens inside the components and enables to send events.
  'development' as an environment is ignored, so no event is sent from it."
  [{:keys [dsn traced-website env]}]
  (.init Sentry
         #js {:dsn dsn
              :environment env
              :integrations #js [(new (.-BrowserTracing Sentry)
                                      #js {:routingInstrumentation
                                           (.reactRouterV6Instrumentation
                                            Sentry
                                            react/useEffect
                                            useLocation
                                            useNavigationType
                                            createRoutesFromChildren
                                            matchRoutes)})
                                 (new (.-Replay Sentry))]
              :replaysSessionSampleRate 0
              :replaysOnErrorSampleRate 0
              :tracesSampleRate 1.0
              :tracePropagationTargets #js ["localhost" traced-website]}))
