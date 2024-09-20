(ns automaton-optimization.repl.entry-point
  "repl for `automaton-optimization` stand alone"
  (:require
   [automaton-core.log  :as core-log]
   [automaton-core.repl :as core-repl]
   [mount.core          :as mount]
   [mount.tools.graph   :as mount-graph])
  (:gen-class))

(defn- stub [& _args] (constantly nil))

(defn -main
  "Main entry point for repl"
  [& args]
  (core-log/info "Start `automaton-simulation-de`")
  (core-log/trace "Component dependencies: " (mount-graph/states-with-deps))
  (core-repl/start-repl args (core-repl/default-middleware) stub)
  (mount/start))
