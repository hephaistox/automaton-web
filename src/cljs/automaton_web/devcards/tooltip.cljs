(ns automaton-web.devcards.tooltip
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.tooltip :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard tooltip
         (bdu/wrap-component [:div {:class ["flex flex-col gap-4 w-fit"]} [sut/tooltip {} [:div "tooltip default"]]
                              [sut/tooltip
                               {:text "hello"
                                :direction "right"} [:div "tooltip right"]] [sut/tooltip {:direction "left"} [:div "tooltip left"]]
                              [sut/tooltip {:direction "bottom"} [:div "tooltip bottom"]]]))
