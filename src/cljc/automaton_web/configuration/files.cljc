(ns automaton-web.configuration.files
  (:require
   [automaton-core.configuration.protocol :as core-conf-prot]
   [automaton-web.configuration.protocol  :as web-prot]
   [automaton-web.js-interop              :as ws-util]
   #?(:clj [automaton-core.configuration.files :as core-conf-files]
      :cljs [automaton-core.utils.keyword :as core-keyword])))

(def js-var
  #?(:clj "_envVars"
     :cljs js/_envVars))

(defn- read-config
  []
  #?(:clj (core-conf-files/read-config)
     :cljs (core-keyword/sanitize-map-keys (js->clj js-var :keywordize-keys true))))

(def ^{:doc "A map of configuration variables."} conf (memoize read-config))

(defrecord SimpleWebConf []
  core-conf-prot/Conf
    (read-conf-param [_this key-path] (get-in (conf) key-path))
    (config [_this] (conf))
  web-prot/ConfWeb
    (config-web-reference [this]
      (str "var " js-var " = " (ws-util/mapToJSmap (core-conf-prot/config this)))))
