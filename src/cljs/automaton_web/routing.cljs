(ns automaton-web.routing
  (:require [pushy.core :as pc]
            [reitit.core :as rc]))
(def router
  rc/router)

(defn url-for
  "Return the path from the given `panel-kw` in the `routes` definition.
   You can read more here https://cljdoc.org/d/metosin/reitit/0.5.18/api/reitit.frontend?q=match-by#match-by-name"
  [routes & panel-kw]
  (apply rc/match-by-name routes panel-kw))

(defn navigate!
  "Go to the path associated with the `handler` keyword"
  [history routes & handler]
  (pc/set-token! history
                 (:path (apply url-for routes handler))))

(defn create-history
  [dispatch parse]
  (pc/pushy dispatch
            parse))

(defn init-routing!
  [history]
  (pc/start! history))

(defn parse
  "From the `routes` parameter, parse the `url` parameter, to return the route content"
  [routes url]
  (get-in (rc/match-by-path routes
                            url)
          [:data :name]))
