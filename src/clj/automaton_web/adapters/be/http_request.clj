(ns automaton-web.adapters.be.http-request
  "Adapter for server side http requests"
  (:require
   [automaton-core.url :as url]
   [clojure.string     :as str]))

(defn- get-header
  "Internal api to return a header"
  [http-request header-name]
  (-> http-request
      :headers
      (get header-name)))

(defn accepted-languages
  "Return the accepted languages in the http request
  Params:
  * `http-request` an http request"
  [http-request]
  (get-header http-request "accept-language"))

(defn hostname
  "Get host from req headers
  Params:
  * `http-request` an http request"
  [http-request]
  (get-header http-request "host"))

(defn tld-language
  "Get the tld in the host of the http request"
  [http-request]
  (->> http-request
       hostname
       url/extract-tld-from-host))

(defn cookies-language
  "Get cookies value under 'lang' key from req
  Params:
  * `http-request` an http request"
  [http-request]
  (let [lang (-> http-request
                 :cookies
                 (get "lang")
                 :value)]
    (cond
      (= lang "null") nil
      (string? lang) (keyword (str/lower-case lang))
      :else lang)))

(defn get-param
  "Get the parameter matching the key `param-kw`
  * `http-request` an http request
  * `param` keyword of the parameter to retrieve (as wrap-paramaeters is wrapping web handler)"
  [http-request param]
  (get-in http-request [:params param]))
