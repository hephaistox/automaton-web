(ns automaton-web.middleware
  "Middlewares for web server"
  (:require
   [automaton-core.log                      :as core-log]
   [automaton-web.adapters.be.http-response :as http-response]
   [automaton-web.duplex.session            :as web-session]
   [automaton-web.i18n.be.translator        :as be-translator]
   [automaton-web.middleware.log-http       :as log-request]
   [automaton-web.pages.errors              :as error-pages]
   [reitit.ring.coercion                    :as rrc]
   [reitit.ring.middleware.muuntaja         :as rrmm]
   [reitit.ring.middleware.parameters       :as rrmp]
   [ring.middleware.anti-forgery            :as ring-anti-forgery]
   [ring.middleware.content-type            :as ring-content-type]
   [ring.middleware.cookies                 :as ring-cookies]
   [ring.middleware.cors                    :as ring-cors]
   [ring.middleware.gzip                    :as ring-gzip]
   [ring.middleware.keyword-params          :as ring-keyword-params]
   [ring.middleware.session                 :as ring-session]
   [ring.util.response                      :as ring-response]))

(defn wrap-throw-error
  "Throws an error, usefull for testing error handling."
  [_]
  (fn
    ([_] (throw (AssertionError. "this ERROR is thrown sync on purpose")))
    ([_ _ _] (throw (AssertionError. "this ERROR is thrown async on purpose")))))

(defn wrap-throw-exception
  "Throws an exception, usefull for testing exception handling."
  [_]
  (fn
    ([_] (throw (Exception. "this exception is thrown sync on purpose")))
    ([_ _ _] (throw (Exception. "this exception is thrown async on purpose")))))

(defn wrap-anti-forgery
  "Prevents CSRF attack"
  ([handler] (ring-anti-forgery/wrap-anti-forgery handler))
  ([handler options] (ring-anti-forgery/wrap-anti-forgery handler options)))

(defn wrap-session
  "Manage user session in cookies.
  Wrapper to read in the request:
  * the session key in :headers \"cookie\" \"ring-session=xxx\", read session data in memory, and store it in :session
  * the respond can contains an updated :session key which will be saved to the cookie"
  [handler]
  (ring-session/wrap-session handler web-session/options))

(defn wrap-transit
  "Transit json acceptance"
  [handler]
  (fn [request]
    (let [response (handler request)]
      (ring-response/header response "Accept" "application/transit+json"))))

(defn wrap-deny-frame
  "Disallow iFrame"
  [handler]
  (fn [request]
    (let [resp (handler request)] (ring-response/header resp "X-Frame-Options" "DENY"))))

(defn wrap-copy-params
  "Copy in :request-copied key the request
  For dev only"
  ([handler kw] (fn [request] (let [response (handler request)] (assoc response kw request))))
  ([handler] (wrap-copy-params handler :request-copied)))

(defn wrap-gzip
  "Compress response before sending to the client. Needs to be called last"
  [handler]
  (ring-gzip/wrap-gzip handler))

(defn wrap-cors
  "allow certain domains only to access information"
  [handler & access-control]
  (apply ring-cors/wrap-cors handler access-control))

(defn wrap-content-type "to accept svg" [handler] (ring-content-type/wrap-content-type handler))

(defn wrap-keyword-params
  "Translate string keys in the :params to keywords"
  [handler]
  (ring-keyword-params/wrap-keyword-params handler))

(defn wrap-cookies
  ([handler] (ring-cookies/wrap-cookies handler))
  ([handler options] (ring-cookies/wrap-cookies handler options)))

(def parameters-middleware
  "map :query-params, :form-params, :params -> reitit wrapper for ring wrap-params"
  rrmp/parameters-middleware)

(def format-negotiate-middleware
  "Negotiates a request body based on Content-Type header and response body based on Accept and Accept-Charset headers. Publishes the negotiation results as :muuntaja/request and :muuntaja/response keys into the request."
  rrmm/format-negotiate-middleware)

(def format-request-middleware "Request decoding" rrmm/format-request-middleware)

(def format-response-middleware "Response encoding" rrmm/format-response-middleware)

(def coerce-exceptions-middleware rrc/coerce-exceptions-middleware)

(def coerce-request-middleware rrc/coerce-request-middleware)

(def coerce-response-middleware rrc/coerce-response-middleware)

(defn wrap-translator
  "Insert the :tr key for translation"
  [web-translate]
  (be-translator/wrap-translator web-translate))

(def wrap-log "Log the http request" log-request/wrap-log)

(defn translation-middlewares
  "Set of middlewares needed for translation
  Params:
  * `translator`"
  [translator]
  [wrap-cookies          ;; It's important to have cookies before
                         ;; translator to allow strategy based on cookie
                         ;; lang
   parameters-middleware ;; It's important to have parameters before
                         ;; translator to allow strategy based on
                         ;; parameters lang
   wrap-keyword-params   ;; Translator use keyworded parameters
   (wrap-translator translator)])

(defn- wrap-exception-handling*
  "Core of wrap-exception-handling fn, moved here for readability. Covers both sync and async version."
  ([handler request body-fn]
   (try
     (handler request)
     (catch Exception e (core-log/error (ex-info "Fail in sync middleware." {:error e})) (body-fn))
     (catch Error e (core-log/fatal (ex-info "Fail in sync middleware." {:error e})) (body-fn))))
  ([handler request respond raise body-fn]
   (try (handler request respond raise)
        (catch Exception e
          (core-log/error (ex-info "Fail in async middleware." {:error e}))
          (respond (body-fn)))
        (catch Error e
          (core-log/fatal (ex-info "Fail in async middleware." {:error e}))
          (respond (body-fn))))))

(defn wrap-exception-handling
  "Handles exceptions that were thrown along the line, handles both sync and async requests.
  Logs the exception and returns 500
  Params:
  * (optional) `body` - defaults to building internal error page, but could be supplied with anything suitable."
  ([handler]
   (fn
     ([request]
      (wrap-exception-handling* handler
                                request
                                #(http-response/internal-server-error
                                  (error-pages/internal-error-page request))))
     ([request respond raise]
      (wrap-exception-handling* handler
                                request
                                respond
                                raise
                                #(http-response/internal-server-error
                                  (error-pages/internal-error-page request))))))
  ([handler body]
   (fn
     ([request]
      (wrap-exception-handling* handler request #(http-response/internal-server-error body)))
     ([request respond raise]
      (wrap-exception-handling* handler
                                request
                                respond
                                raise
                                #(http-response/internal-server-error body))))))
