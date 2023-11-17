(ns automaton-web.repl
  "REPL component
  Design decision:
  * The repl could have pushed to dev, but leaving it here allows to remotely connect to the remote repl, like la or production"
  (:require [automaton-core.adapters.files :as files]
            [automaton-core.configuration :as conf]
            [automaton-core.log :as log]
            [cider.nrepl :as nrepl-mw]
            [clojure.core.async :refer [<!! chan]]
            [nrepl.server :refer [default-handler start-server stop-server]]))

(def nrepl-port-filename "Name of the `.nrepl-port` file" ".nrepl-port")

(defn custom-nrepl-handler "We build our own custom nrepl handler" [nrepl-mws] (apply default-handler nrepl-mws))

(def repl "Store the repl instance in the atom" (atom {}))

(defn get-nrepl-port-parameter [] (conf/read-param [:dev :clj-nrepl-port] 8000))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn get-active-nrepl-port "Retrieve the nrepl port, available for REPL" [] (:nrepl-port @repl))

(defn stop-repl "Stop the repl" [repl-port] (log/info "Stop nrepl server on port" repl-port) (stop-server (:repl @repl)) (reset! repl {}))

(defn create-nrepl-files
  "Consider all deps.edn files as the root of a clojure project and creates a .nrepl-port file next to it"
  [repl-port]
  (let [build-configs (files/search-files "" "**build_config.edn")
        nrepl-ports (map #(files/file-in-same-dir % nrepl-port-filename) build-configs)]
    (doseq [nrepl-port nrepl-ports] (files/write-file (str repl-port) nrepl-port))))

(defn start-repl*
  "Launch a new repl"
  [{:keys [middleware]}]
  (let [repl-port (get-nrepl-port-parameter)]
    (create-nrepl-files repl-port)
    (reset! repl {:nrepl-port repl-port
                  :repl (do (log/info "nrepl available on port " repl-port)
                            (println "repl port is available on:" repl-port)
                            (start-server :port repl-port :handler (custom-nrepl-handler middleware)))})
    (.addShutdownHook (Runtime/getRuntime)
                      (Thread. #(do (log/info "SHUTDOWN in progress, stop repl on port `" repl-port "`")
                                    (-> (files/search-files "" (str "**" nrepl-port-filename))
                                        (files/delete-files))
                                    (stop-repl repl-port))))))

(defn start-repl
  "Start repl, setup and catch errors"
  []
  (try (start-repl* {:middleware (conj nrepl-mw/cider-middleware 'refactor-nrepl.middleware/wrap-refactor)})
       :started
       (catch Exception e (log/error (ex-info "Uncaught exception" {:error e})))))

(defn -main
  "Entry point for simple / emergency repl"
  [& _args]
  (let [c (chan)]
    (start-repl)
    (<!! c)))
