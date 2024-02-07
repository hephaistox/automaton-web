(ns automaton-web.pages.throw-exception.route)

(defn route
  []
  ["/throw-exception"
   {:summary "Raise intentionally an exception"
    :get (fn [_request]
           (throw (ex-info "This exception is raised intentionally"
                           {:to "check"
                            :what "happens"})))}])
