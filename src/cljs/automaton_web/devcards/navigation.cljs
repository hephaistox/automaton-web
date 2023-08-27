(ns automaton-web.devcards.navigation
  (:require [devcards.core :as dc :refer [defcard]]

            [automaton-web.components.navigation :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard back-navigation
  (bdu/wrap-component [:div
                       [:h2 "Back navigation"]
                       [sut/back-navigation {:text "Back!"
                                             :href "#"}]
                       [:h2
                        "Back navigation dark"]
                       [:div
                        {:class ["bg-black"]}
                        [sut/back-navigation {:text "Back!"
                                              :href "#"
                                              :dark? true}]]]))
