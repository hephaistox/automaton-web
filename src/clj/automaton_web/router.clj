(ns automaton-web.router
  (:require
   [automaton-web.duplex.routes :as rt-routes]
   [automaton-web.handlers :as bewh]
   [automaton-web.middleware :as bewm]
   [muuntaja.core :as m]
   [reitit.coercion :as coercion]
   [reitit.ring :as rr]))

(defn all-routes [{:keys [routes mdre default-handlers]}]
  (rr/ring-handler
   (rr/router (vector
               routes
               [@rt-routes/routes
                {:compile coercion/compile-request-coercers}])
              {:data {:muuntaja m/instance
                      :middleware mdre}})

   (rr/routes
    (bewh/resource-handler {})
    (bewh/default-handlers default-handlers))
   {:middleware [bewm/wrap-cookies]}))
