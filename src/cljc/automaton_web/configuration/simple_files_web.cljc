(ns automaton-web.configuration.simple-files-web
  (:require [automaton-core.configuration.protocol :as core-conf-prot]
            [automaton-web.configuration.protocol :as web-prot]
            [automaton-web.js-interop :as ws-util]
            #?(:clj [automaton-core.configuration.simple-files :as sf]
               :cljs [automaton-core.utils.keyword :as utils-keyword])))

(def js-var
  #?(:clj "_envVars"
     :cljs js/_envVars))

(defn- read-config
  []
  #?(:clj (sf/read-config)
     :cljs (utils-keyword/sanitize-map-keys (js->clj js-var :keywordize-keys true))))

(def ^{:doc "A map of configuration variables."} conf (memoize read-config))

(defrecord SimpleWebConf []
  core-conf-prot/Conf
    (read-conf-param [_this key-path] (get-in (conf) key-path))
    (config [_this] (conf))
  web-prot/ConfWeb
    (config-web-reference [this] (str "var " js-var " = " (ws-util/mapToJSmap (core-conf-prot/config this)))))
