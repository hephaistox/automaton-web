(ns automaton-web.devcards.errors
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.errors :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard not-found
         (bdu/wrap-component [:div [:h3 "not found page"]
                              [sut/not-found
                               {:back-link "https://www.wikipedia.com"
                                :title "Page not found"
                                :description "Sorry we couldn't find the page you were looking for."
                                :back-home-text "Back home"}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard internal-error
         (bdu/wrap-component [:div [:h3 "internal error page"]
                              [sut/internal-error
                               {:back-link "https://www.wikipedia.com"
                                :title "Well, this is unexpected..."
                                :description "An error has occured and we are working hard to fix the problem! We'll be running shortly"
                                :back-home-text "Back home"}]]))
