(ns automaton-web.portfolio.components.button
  (:require
   [automaton-web.components.button :as sut]
   [automaton-web.portfolio.proxy   :as web-proxy]
   [portfolio.reagent-18            :as           portfolio
                                    :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Buttons"})

(defscene button
          (web-proxy/wrap-component [sut/button {:text "Button"
                                                 :on-click #(js/alert "Hello")}]))

(defscene disabled-button
          (web-proxy/wrap-component [sut/button {:disabled true
                                                 :text "Can't click"}]))

(defscene styled-button
          (web-proxy/wrap-component [sut/button {:text "Custom"
                                                 :class ["text-violet-400 bg-green-400"]}]))

(defscene link-button
          (web-proxy/wrap-component [sut/link-button {:link "https://www.wikipedia.com"}
                                     {:text
                                      "Underneath I'm same as button, but click me and see"}]))

(defscene x-button
          (web-proxy/wrap-component [sut/x-button {:on-click #(js/alert "Hello")}]))
