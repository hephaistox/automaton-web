(ns automaton-web.devcards.button
  (:require [devcards.core :as dc :refer [defcard]]

            [automaton-web.components.button :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard button
  (bdu/wrap-component [:div
                       [:h3 "Normal"]
                       [sut/button {:text "Button"}]
                       [:h3 "Click function"]
                       [sut/button {:text "Button"
                                    :on-click (fn [_] (js/alert "Hello"))}]
                       [:h3 "Disabled"]
                       [sut/button {:disabled true
                                    :text "Can't click"}]
                       [:h3 "Custom class"]
                       [sut/button {:text "Custom"
                                    :class ["text-violet-400 bg-green-400"]}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard link-button
  (bdu/wrap-component [sut/link-button
                       {:link "https://www.wikipedia.com"}
                       {:text "Underneath I'm same as button, but click me and see"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard x-button
  (bdu/wrap-component [sut/x-button {:on-click (fn [_]
                                                 (js/alert "Hello"))}]))
