(ns automaton-web.web-server
  "Create the webserver, (http kit), set it up, log uncaught exceptions in thread"
  (:require
   [automaton-core.log          :as core-log]
   [automaton-web.configuration :as web-conf]
   [org.httpkit.server          :as http-kit]))

;; Log uncaught exceptions in threads
(Thread/setDefaultUncaughtExceptionHandler
 (reify
  Thread$UncaughtExceptionHandler
    (uncaughtException [_ thread ex]
      (core-log/error (ex-info (str "Uncaught exception on" (.getName thread))
                               {:error ex})))))

(defn start-server
  "Generate the server, based on the given handler.
  Options are optional, default values are
  - dont-block [true] tells server not to block the thread
  - port: should not be used to enable multiple web servers in the repl
  "
  [handler opts]
  (core-log/info "Starting Webserver component")
  (let [http-port (web-conf/read-param [:http-server :port] 8080)
        _ (println (str "Started!!! on http://localhost:" http-port "/admin"))
        opts-with-defaults (merge {:port http-port
                                   :dont-block false}
                                  opts)
        webserver-opts {:port (:port opts-with-defaults)
                        :join? (:dont-block opts-with-defaults)}]
    (core-log/trace "Params" opts-with-defaults)
    (try (http-kit/run-server handler webserver-opts)
         (catch Exception e
           (core-log/error (ex-info "Unable to start server" {:error e}))))))

(defn stop-server [server] (server) (core-log/info "Server stopped") server)
