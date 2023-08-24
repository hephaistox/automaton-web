(ns automaton-web.devcards.errors
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.errors :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard not-found
  (bdu/wrap-component [:div
                       [:h3 "not found page"]
                       [sut/not-found
                        {:back-link "https://www.wikipedia.com"
                         :not-found-page-title "Page not found"
                         :not-found-page-description "Sorry we couldn't find the page you were looking for."
                         :back-home-text "Back home"}]]))
