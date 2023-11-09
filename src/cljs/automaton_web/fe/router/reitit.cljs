(ns automaton-web.fe.router.reitit
  "Adapter for `fe-router/Router` based on [reitit](https://cljdoc.org/d/fi.metosin/reitit/0.7.0-alpha6/doc/introduction)

  That namespace is written to always return a page when something unexpected occur, so a page is displayed, even if a message is logged

  Create an instance preferably with `make-reitit-routes` function"
  (:require [automaton-core.log :as log]
            [automaton-web.fe.router :as web-fe-router]
            [reitit.core :as reitit]
            [reitit.dev.pretty :as pretty]
            [reitit.frontend :as reitit-frontend]
            [reitit.spec :as rs]))

(defrecord ReititRouter [router gather-route-params-fn]
  web-fe-router/Router
    (match-from-url [_ url] (reitit-frontend/match-by-path router url))
    (route-name [_ match] (get-in match [:data :name]))
    (panel-id [_ match] (get-in match [:data :panel-id] :panels/not-found))
    (url-params [_ match] (get match :query-params)))

(defn make-reitit-router
  "Make reitit router
  Params:
  * `routes` is the data structure describing the routes
  * `gather-route-params-fn` function with no argument returning a map with all data used in the routes"
  [routes gather-route-params-fn]
  (let [reitit-router (reitit/router routes
                                     {:conflicts (fn [conflicts]
                                                   (log/error (ex-info "Conflicts in routes have been found"
                                                                       {:conflicts conflicts
                                                                        :routes routes})))
                                      :validate rs/validate
                                      :exception pretty/exception})]
    (->ReititRouter reitit-router gather-route-params-fn)))
