(ns automaton-web.devcards.menu-item
  (:require
   [automaton-web.components.menu-item :as sut]

   [devcards.core :as dc :refer [defcard]]
   [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard menu-item
  (bdu/wrap-component [:div
                       [sut/component {:title "Normal menu"
                                       :href "/coucou"}]

                       [sut/component {:title "Selected menu"
                                       :selected true
                                       :href "/coucou"}]

                       [sut/component {:title "Check click action works"
                                       :on-click #(js/alert "Hey, click action works!")}]]))
