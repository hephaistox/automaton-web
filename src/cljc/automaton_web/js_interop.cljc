(ns automaton-web.js-interop
  "Utility functions for webserver"
  (:require
   [clojure.string :as str]))

(defn- kw-to-js
  "Transform a keyword in a javascript compatible name"
  [k]
  (str "\"" (str/replace (name k) #"-" "_") "\""))

(defn mapToJSmap
  "Transform a map to a javascript map"
  [m]
  (str "{"
       (str/join ",\n"
                 (for [[k v] m]
                   (str (kw-to-js k)
                        ": "
                        (cond
                          (string? v) (str "\"" v "\"")
                          (keyword? v) (kw-to-js v)
                          (map? v) (mapToJSmap v)
                          (nil? v) 'null
                          :else v))))
       "}"))
