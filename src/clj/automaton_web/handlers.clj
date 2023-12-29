(ns automaton-web.handlers
  "Predefined handlers to manage error pages, and resources (in the sense of classpath in java)"
  (:require [automaton-web.adapters.be.http-response :as http-response]
            [automaton-web.pages.errors :as error-pages]
            [reitit.ring :as reitit-ring]))

(defn not-found-handler
  [request]
  (-> request
      error-pages/not-found-page
      http-response/not-found))

(defn not-allowed-handler
  [request]
  (-> request
      error-pages/not-found-page
      http-response/method-not-allowed))

(defn not-acceptable-handler
  [request]
  (-> request
      error-pages/not-found-page
      http-response/not-acceptable))

(defn resource-handler
  [{:keys [path root index-files nfh]
    :or {path "/"
         root "public"
         index-files []
         nfh not-found-handler}}]
  (reitit-ring/create-resource-handler {:path path
                                        :root root
                                        :index-files index-files
                                        :not-found-handler nfh}))

(defn apply-middlewares
  "Apply the collection of middlewares to the handler
  Params:
  * `handler` handler to wrap
  * `middlewares` is a collection of middlewares, could be a function or compile middlewares"
  [handler middlewares]
  (reduce (fn [handler middleware] (if (fn? middleware) (middleware handler) ((:wrap middleware) handler))) handler middlewares))

(defn default-handlers
  [{:keys [not-found not-allowed not-acceptable]
    :or {not-found not-found-handler
         not-allowed not-allowed-handler
         not-acceptable not-acceptable-handler}} middlewares]
  (reitit-ring/create-default-handler {:not-found (apply-middlewares not-found middlewares)
                                       :method-not-allowed (apply-middlewares not-allowed middlewares)
                                       :not-acceptable (apply-middlewares not-acceptable middlewares)}))
