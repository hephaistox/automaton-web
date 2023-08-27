(ns automaton-web.dev
  "Will be `refer :all` in the subproject `user` namespace, default namepsace for subproject REPL"
  (:require
   [clj-memory-meter.core :as mm]

   [outpace.config.repl]

   [automaton-web.repl :as repl]))

(defn start
  "Start repl"
  [& _args]
  (repl/start-repl))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn go
  "starts all states defined by defstate"
  []
  (outpace.config.repl/reload)
  (start)
  :ready)

;; See https://github.com/clojure-goes-fast/clj-memory-meter, for details
#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn measure-time-sample
  []
  (mm/measure "hello world"))
