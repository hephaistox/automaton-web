(ns automaton-web.repl-core
  "repl for `automaton-web` stand alone
  This namespace is called to start a repl on `automaton-core` only"
  (:require [automaton-core.log :as log]
            [automaton-core.repl :as repl]
            [clojure.core.async :refer [<! chan go]]
            [mount.core :as mount]
            [mount.tools.graph :as graph])
  (:gen-class))

(defn -main
  "Main entry point for repl"
  [& _args]
  (log/info "Start `automaton-core`")
  (log/trace "Component dependencies: " (graph/states-with-deps))
  (mount/start)
  (repl/start-repl)
  (let [c (chan 1)] (go (<! c))))
/
