(ns automaton-web.middleware
  "Middlewares for web server"
  (:require
   [ring.util.response :as resp]
   [ring.middleware.content-type :as rmct]
   [ring.middleware.cors :as rmc]
   [ring.middleware.session :as rms]
   [ring.middleware.gzip :as rmg]
   [ring.middleware.anti-forgery :as rma]
   [ring.middleware.cookies :as rmcs]
   [ring.middleware.keyword-params :as rmkp]

   [automaton-web.duplex.session :as session]

   [reitit.ring.coercion :as rrc]
   [reitit.ring.middleware.parameters :as rrmp]
   [reitit.ring.middleware.muuntaja :as rrmm]
   [reitit.ring.middleware.exception :as rrme]))

(defn wrap-anti-forgery
  "Prevents CSRF attack"
  ([handler]
   (rma/wrap-anti-forgery handler))
  ([handler options]
   (rma/wrap-anti-forgery handler options)))

(defn wrap-session
  "Manage user session in cookies.
  Wrapper to read in the request:
  * the session key in :headers \"cookie\" \"ring-session=xxx\", read session data in memory, and store it in :session
  * the respond can contains an updated :session key which will be saved to the cookie"
  [handler]
  (rms/wrap-session handler
                    session/options))

(defn wrap-transit
  "Transit json acceptance"
  [handler]
  (fn [request]
    (let [response (handler request)]
      (resp/header response
                   "Accept" "application/transit+json"))))

(defn wrap-deny-frame
  "Disallow iFrame"
  [handler]
  (fn [request]
    (let [resp (handler request)]
      (resp/header resp
                   "X-Frame-Options"
                   "DENY"))))

(defn wrap-copy-params
  "Copy in :request-copied key the request
  For dev only"
  ([handler kw]
   (fn [request]
     (let [response (handler request)]
       (assoc response
              kw request))))
  ([handler]
   (wrap-copy-params handler :request-copied)))

(defn wrap-gzip
  "Compress response before sending to the client. Needs to be called last"
  [handler]
  (rmg/wrap-gzip handler))

(defn wrap-cors
  "allow certain domains only to access information"
  [handler & access-control]
  (apply rmc/wrap-cors handler access-control))

(defn wrap-content-type
  "to accept svg"
  [handler]
  (rmct/wrap-content-type handler))

(defn wrap-keyword-params
  "Translate string keys in the :params to keywords"
  [handler]
  (rmkp/wrap-keyword-params handler))

(defn wrap-cookies
  ([handler]
   (rmcs/wrap-cookies handler))
  ([handler options]
   (rmcs/wrap-cookies handler options)))

(def parameters-middleware
  "map :query-params, :form-params, :params -> reitit wrapper for ring wrap-params"
  rrmp/parameters-middleware)

(def exception-middleware
  "Top-level exception handler to log and format errors for clients should be done before request decoding"
  rrme/exception-middleware)

(def format-negotiate-middleware
  "Negotiates a request body based on Content-Type header and response body based on Accept and Accept-Charset headers. Publishes the negotiation results as :muuntaja/request and :muuntaja/response keys into the request."
  rrmm/format-negotiate-middleware)

(def format-request-middleware
  "Request decoding"
  rrmm/format-request-middleware)

(def format-response-middleware
  "Response encoding"
  rrmm/format-response-middleware)

(def coerce-exceptions-middleware
  rrc/coerce-exceptions-middleware)

(def coerce-request-middleware
  rrc/coerce-request-middleware)

(def coerce-response-middleware
  rrc/coerce-response-middleware)
