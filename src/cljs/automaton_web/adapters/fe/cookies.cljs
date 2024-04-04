(ns automaton-web.adapters.fe.cookies
  "Adapter to store a cookie on the frontend"
  (:require
   [automaton-web.persistence.cookies :as cookies]
   [goog.net.Cookies                  :as gnc]))

(defn ^:export set-cookie
  "Set the salue of the cookie
  Params:
  * `k` is the key, transformed to a str, so keyword are transformed
  * `v` is the value associated to k
  * `time` is the timestamp of that value
  * `path` path for which that cookie is sent. It and its subdomains"
  ([k v time path] (.set (gnc/getInstance) (str k) v time path))
  ([k v time] (.set (gnc/getInstance) (str k) v time "/"))
  ([k v] (.set (gnc/getInstance) (str k) v -1)))

(defn get-cookie
  "Get the the cookie map of data"
  []
  (cookies/parse-cookie (str (.-cookie js/document))))

(defn get-cookie-val
  "Get the value of a key
  Params:
  * `key` the key which value should be returned, is a keyword to be mapped in a string"
  [key]
  (get (get-cookie) (str key)))
