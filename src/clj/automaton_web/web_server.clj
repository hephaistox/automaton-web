(ns automaton-web.web-server
  "Create the webserver, (http kit), set it up, log uncaught exceptions in thread"
  (:require
   [automaton-web.configuration.core :as conf]
   [automaton-web.log :as log]

   [org.httpkit.server :as http-kit]))

;; Log uncaught exceptions in threads
(Thread/setDefaultUncaughtExceptionHandler
 (reify Thread$UncaughtExceptionHandler
   (uncaughtException [_ thread ex]
     (log/error {:what :uncaught-exception
                 :exception ex
                 :where (str "Uncaught exception on" (.getName thread))}))))

(defn start-server
  "Generate the server, based on the given handler.
  Options are optional, default values are
  - dont-block [true] tells server not to block the thread
  - port: should not be used to enable multiple web servers in the repl
  "
  [handler opts]
  (log/info "Starting Webserver component")
  (let [http-port (conf/read-param [:http-server :port])
        _ (println (str "Started!!! on http://localhost:" http-port "/admin"))
        opts-with-defaults (merge
                            {:port http-port
                             :dont-block false}
                            opts)
        webserver-opts {:port (:port opts-with-defaults)
                        :join? (:dont-block opts-with-defaults)}]
    (log/trace "Params" opts-with-defaults)
    (try
      (http-kit/run-server handler
                           webserver-opts)
      (catch Exception e
        (log/error "Unable to start server" e)))))

(defn stop-server
  [server]
  (server)
  (log/info "Server stopped")
  server)
