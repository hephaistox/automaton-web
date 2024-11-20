(ns automaton-web.portfolio.components.header
  (:require
   [automaton-web.components.header :as sut]
   [automaton-web.components.menu   :as web-menu]
   [automaton-web.portfolio.proxy   :as web-proxy]
   [portfolio.reagent-18            :as           portfolio
                                    :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Header"})

(defn menu
  []
  [web-menu/component {:items [{:title "Menu"
                                :href "/#/"}
                               {:title "Menu1"
                                :href "/#/"}
                               {:title "Menu2"
                                :href "/#/"}]}])

(defscene header (web-proxy/wrap-component [sut/component (menu)]))

(defscene simple-header
          (web-proxy/wrap-component [sut/transparent-header {:logo "I'm your logo"
                                                             :right-section "Right content"}]))

(defscene simple-header-full-size
          (web-proxy/wrap-component [sut/transparent-header {:size :full
                                                             :logo "I'm your logo"
                                                             :right-section "Right content"}]))
