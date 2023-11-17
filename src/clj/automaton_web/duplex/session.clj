(ns automaton-web.duplex.session
  "Manage session, stored in memory for now
  Session information are described there : https://github.com/ring-clojure/ring/wiki/Sessions"
  (:require [ring.middleware.session.memory :as ring-memory]))

(def session-store "Atom containing all active sequences" (atom {}))

(comment
  @session-store
  ;
  (reset! session-store {})
  (swap! session-store assoc :foo :bar))

(def options
  "Options "
  {:store (ring-memory/memory-store session-store)
   :cookies-attrs {:http-only true}})

(defn sessions "Return sessions" [] @session-store)
