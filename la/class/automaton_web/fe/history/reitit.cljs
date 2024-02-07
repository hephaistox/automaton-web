(ns automaton-web.fe.history.reitit
  "Adapter for reitit history
  See [reitit history](https://github.com/metosin/reitit/blob/master/modules/reitit-frontend/src/reitit/frontend/history.cljs) for details"
  (:require
   [automaton-web.adapters.fe.url :as fe-url]
   [automaton-web.fe.history :as web-fe-history]
   [reitit.frontend.history :as reitit-fe-history]))

(defrecord History [history]
  web-fe-history/History
    (init! [_] nil)
    (navigate! [_ page] (fe-url/navigate! page))
    (stop! [_] (reitit-fe-history/stop! history))
    (href-delta [_ match name path-params query-params]
      (reitit-fe-history/href history
                              (or name (get-in match [:data :name]))
                              (merge (:path-params match) path-params)
                              (merge (:query-params match) query-params)))
    (href [_ name path-params query-params]
      (reitit-fe-history/href history name path-params query-params)))

(defn make-history
  "Make an history (fe-history/History) instance for pushy.
  Decides what is send to backend, and so its routing or not.

  Params:
  * `router` router used by that history
  * `on-navigate` function called when a route changed. Takes two parameters, `match` and `history`"
  [router on-navigate]
  (->History
   (reitit-fe-history/start! router on-navigate {:use-fragment false})))
