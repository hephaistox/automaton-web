(ns automaton-web.configuration
  "Configuration parameters, stored in configuration file.
   This namespace is the entry point to call conf.

  We use prn instead of log and try to limit outside dependencies as much as possible here on purpose to be able to use configuration everywhere.

  For a parameter `p`:
  * Create the parameter, in the current implementation, in the `util/conf.clj`
  * Read the parameter with  `conf/read-param`"
  (:require
   [automaton-core.configuration.protocol :as core-conf-prot]
   [automaton-web.configuration.files     :as web-conf-files]
   [automaton-web.configuration.protocol  :as web-prot]
   [mount.core                            :refer [defstate in-cljc-mode]]))

;; Force the use of `cljc mode` in mount library, so call to `@` will work
(in-cljc-mode)

(defn start-conf
  []
  (try (prn "Starting web configuration component")
       (let [conf (web-conf-files/->SimpleWebConf)]
         (prn "Web configuration component is started")
         conf)
       (catch #?(:clj Throwable
                 :cljs :default)
         e
         (prn "Web configuration failed" e))))

(defn stop-conf [] (prn "Stop web configuration component"))

(defstate conf-state :start (start-conf) :stop (stop-conf))

(defn read-param
  "Returns value under `key-path` vector."
  ([key-path default-value]
   (let [value (core-conf-prot/read-conf-param @conf-state key-path)]
     (if (nil? value)
       (do (prn "Value for " key-path " is not set, use default value" default-value) default-value)
       (do (prn "Read key-path " key-path " = " value) value))))
  ([key-path] (read-param key-path nil)))

(defn all-config
  "Returns whole configuration map, with all the keys and values."
  []
  (core-conf-prot/config @conf-state))

(defn config-web-reference
  "Configuration variable that is used to save configuration in js code."
  []
  (web-prot/config-web-reference @conf-state))
