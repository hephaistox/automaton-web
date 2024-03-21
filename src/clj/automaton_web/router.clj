(ns automaton-web.router
  "Proxy to [reitit](https://cljdoc.org/d/fi.metosin/reitit/0.7.0-alpha6/doc/introduction) for the backend"
  (:require
   [automaton-web.duplex.routes :as duplex-routes]
   [automaton-web.handlers      :as bewh]
   [muuntaja.core               :as m]
   [reitit.coercion             :as coercion]
   [reitit.ring                 :as reitit-ring]))

(defn router
  [web-routes web-middleware]
  (reitit-ring/router (vector web-routes
                              [@duplex-routes/routes
                               {:compile coercion/compile-request-coercers}])
                      {:data {:muuntaja m/instance
                              :middleware web-middleware}}))

(defn ring-handler
  "Gather the routes
  Params:
  * `web-routes` routes to assemble
  * `web-middleware` middleware for web
  * `default-handlers` list of default handlers
  * `global-middlewares` middlewares common for all routes, default, resource and web
  * `translator-middlewares` middlewares needed for translator"
  [{:keys [web-routes
           web-middleware
           default-handlers
           global-middlewares
           translator-middlewares]}]
  (reitit-ring/ring-handler (router web-routes web-middleware)
                            (reitit-ring/routes (bewh/resource-handler {})
                                                (bewh/default-handlers
                                                 default-handlers
                                                 translator-middlewares))
                            {:middleware global-middlewares
                             :inject-match? true ;; So the `:match` keyword
                                                 ;; is in the request and
                                                 ;; you can analyse it
                            }))
