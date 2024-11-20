(ns automaton-web.portfolio.components.tooltip
  (:require
   [automaton-web.components.tooltip :as sut]
   [automaton-web.portfolio.proxy    :as web-proxy]
   [portfolio.reagent-18             :as           portfolio
                                     :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Tooltip"})

(defscene tooltip
          (web-proxy/wrap-component [:div {:class ["text-center mt-10"]}
                                     [sut/tooltip {}
                                      [:div "tooltip default"]]]))

(defscene tooltip-right
          (web-proxy/wrap-component [:div {:class ["text-center mt-10"]}
                                     [sut/tooltip {:text "hello"
                                                   :direction "right"}
                                      [:div "tooltip right"]]]))

(defscene tooltip-left
          (web-proxy/wrap-component [:div {:class ["text-center mt-10"]}
                                     [sut/tooltip {:direction "left"}
                                      [:div "tooltip left"]]]))

(defscene tooltip-bottom
          (web-proxy/wrap-component [:div {:class ["text-center mt-10"]}
                                     [sut/tooltip {:direction "bottom"}
                                      [:div "tooltip bottom"]]]))
