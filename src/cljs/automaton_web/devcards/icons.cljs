(ns automaton-web.devcards.icons
  (:require [automaton-web.react-proxy :as bwc]

            [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.icons :as sut]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard check-alert
  (bwc/as-element
   [:div
    [:h1
     {:class ["text-2xl"]}
     "Show alerts in different situations"]
    [:h2 "Default alert"]
    [sut/icon {}]
    [:h2 "Change size"]
    [sut/icon {:width 30}]
    [:h2 "Big"]
    [sut/icon {:width 60}]

    [:h2 "Set color to green"]
    [sut/icon {:class ["icon-green"]}]
    [:h2 "Set color to red"]
    [sut/icon {:class ["icon-red"]}]
    [:h2 "Set color to yellow"]
    [sut/icon {:class ["icon-yellow"]}]

    [:h1
     {:class ["text-2xl"]}
     "Different shapes, doubles"]
    (vec
     (concat [:div]
             (for [i sut/icons-path]
               (let [[name] i]
                 [:div
                  [:h2 (str "Icon " name)]
                  [sut/icon {:path-kw name
                             :color-stroke :green
                             :color-fill :green
                             }]]))))]))
