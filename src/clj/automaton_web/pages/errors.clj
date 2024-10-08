(ns automaton-web.pages.errors
  "Gathering default implementations of error pages."
  (:require
   [automaton-core.utils.fallback   :as fallback]
   [automaton-web.components.errors :as web-comp-errors]
   [automaton-web.pages.index       :as web-pages-index]))

(defn not-found-page
  "Build default not found page"
  [{:keys [tr]
    :or {tr str}
    :as request}]
  (let [title (fallback/always-return #(tr :not-found-page) "This was unexpected")
        description (fallback/always-return #(tr :not-found-description)
                                            "But we are working on it!")
        back-home (fallback/always-return #(tr :back-home) "Back")]
    (web-pages-index/build request
                           (web-comp-errors/not-found {:title title
                                                       :description description
                                                       :back-home-text back-home}))))

(defn internal-error-page
  "Build default internal error page"
  [{:keys [tr]
    :or {tr str}
    :as request}]
  (let [title (fallback/always-return #(tr request :this-is-unexpected) "This was unexpected")
        description (fallback/always-return #(tr request :we-are-working-on-it)
                                            "But we are working on it!")
        back-home (fallback/always-return #(tr request :back-home) "Back")]
    (web-pages-index/build request
                           (web-comp-errors/internal-error {:title title
                                                            :description description
                                                            :back-home-text back-home}))))
