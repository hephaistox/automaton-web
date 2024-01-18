(ns automaton-web.middleware.log-http
  "For logging the request
  Having that middleware appart allows to manage the log parameters for that query only, as it may be huge on production"
  (:require
   [automaton-core.log :as core-log]))

(defn wrap-log
  "Write the response in the log
   The request and response are set as data into logs.
  As this are numerous and big, formatting has been removed.
  May be removed in static rule of log in the future when it will be available.
   The formatting is turned off to limit resource usage
  Params:
  * `handler`"
  [handler]
  (fn [request]
    (core-log/trace-data request "Trace request")
    (let [response (handler request)]
      (core-log/trace-data response "Trace response")
      response)))
