(ns automaton-web.portfolio.components.menu-item
  (:require
   [automaton-web.components.menu-item :as sut]
   [automaton-web.portfolio.proxy      :as web-proxy]
   [portfolio.reagent-18               :as           portfolio
                                       :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Menu Item"})

(defscene menu-item
          (web-proxy/wrap-component [sut/component {:title "Normal menu"
                                                    :href "/coucou"}]))

(defscene menu-item-selected
          (web-proxy/wrap-component [sut/component {:title "Selected menu"
                                                    :selected true
                                                    :href "/coucou"}]))

(defscene menu-item-click-action
          (web-proxy/wrap-component [sut/component {:title "Check click action works"
                                                    :on-click #(js/alert
                                                                "Hey, click action works!")}]))
