(ns automaton-web.handlers
  (:require
   [ring.util.response :as resp]
   [ring.util.http-response :as response]
   [reitit.ring :as rr]

   [automaton-web.pages.index :as index]
   [automaton-web.components.errors :as bce]
   [automaton-web.i18n.language-backend :as bilb]))

(defn not-found-handler [request]
  (-> request
      (index/build (bce/not-found {:not-found-page-title (bilb/btr request :not-found-page)
                                   :not-found-page-description (bilb/btr request :not-found-description)
                                   :back-home-text (bilb/btr request :back-home)}))
      response/not-found))

(defn not-allowed-handler [request]
  (-> request
      (index/build (bce/not-found {:not-found-page-title (bilb/btr request :not-found-page)
                                   :not-found-page-description (bilb/btr request :not-found-description)
                                   :back-home-text (bilb/btr request :back-home)}))
      response/method-not-allowed))

(defn not-acceptable-handler [request]
  (-> request
      (index/build (bce/not-found {:not-found-page-title (bilb/btr request :not-found-page)
                                   :not-found-page-description (bilb/btr request :not-found-description)
                                   :back-home-text (bilb/btr request :back-home)}))
      response/not-acceptable))

(defn resource-handler [{:keys [path root index-files nfh]
                         :or {path "/"
                              root "public"
                              index-files []
                              nfh not-found-handler}}]
  (rr/create-resource-handler {:path path
                               :root root
                               :index-files index-files
                               :not-found-handler nfh}))

(defn default-handlers [{:keys [not-found not-allowed not-acceptable]
                         :or {not-found not-found-handler
                              not-allowed not-allowed-handler
                              not-acceptable not-acceptable-handler}}]
  (rr/create-default-handler
   {:not-found not-found
    :method-not-allowed not-allowed
    :not-acceptable not-acceptable}))

(defn language-handler
  "Redirects user to appropriate language subfolder."
  [req]
  (->> req
       bilb/select-language
       (str "/")
       resp/redirect))
