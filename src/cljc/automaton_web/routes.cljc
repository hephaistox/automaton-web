(ns automaton-web.routes
  "Toolings for routes management, for both backend and frontend"
  (:require
   [automaton-core.utils.map :as utils-map]))

(defn- kw-to-registry-value
  ([registry options val]
   (if (keyword? val) (partial (get registry val) options) val))
  ([registry val]
   (if (keyword? val) (get registry val) val)))

(defn- update-registry
  [handler-registry handler-map]
  (utils-map/update-kw handler-map
                       [:get :head :patch :delete :options :post :put :trace]
                       (if-let [metadata (:index-metadata handler-map)]
                         (partial kw-to-registry-value handler-registry metadata)
                         (partial kw-to-registry-value handler-registry))))

(defn parse-routes
  "Parse the routes to return the frontend or backend
  Params:
  * `kw` Is `:fe` or `:be`, to respectively return frontend or backend data
  * `routes` routes to analyze
  * `handler-registry` transform a keyword into a handler"
  ([router-kw routes] (parse-routes router-kw routes {}))
  ([router-kw routes handler-registry]
   (let [selected-route-data (get routes router-kw)]
     (cond
       (string? routes) routes
       (and (map? routes) (map? selected-route-data))
       (merge (select-keys routes [:name])
              (update-registry handler-registry selected-route-data))
       (map? routes) (kw-to-registry-value handler-registry selected-route-data)
       (vector? routes) (mapv #(parse-routes router-kw % handler-registry)
                              routes)))))
