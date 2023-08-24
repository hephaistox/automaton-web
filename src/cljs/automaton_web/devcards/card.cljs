(ns automaton-web.devcards.card
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.card :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard card
  (bdu/wrap-component [:div
                       [:h3 "card person light"]
                       [:div
                        {:class ["max-w-xs mx-auto"]}
                        [sut/card
                         {:title "Founder"
                          :name "Filthy Frank"
                          :img "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80"
                          :linkedin "#"
                          :on-click #(js/alert "Clicked!")}]]
                       [:h3 "card person dark"]
                       [:div
                        {:class ["max-w-xs mx-auto"]}
                        [sut/card
                         {:title "Founder"
                          :name "Filthy Frank"
                          :img "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80"
                          :linkedin "#"
                          :dark? true
                          :on-click #(js/alert "Clicked!")}]]]))
