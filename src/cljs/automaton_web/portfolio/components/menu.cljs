(ns automaton-web.portfolio.components.menu
  (:require
   [portfolio.reagent-18
    :as
    portfolio
    :refer-macros
    [defscene configure-scenes]]
   [automaton-web.portfolio.proxy :as web-proxy]
   [automaton-web.components.menu :as sut]))

(configure-scenes {:collection :components
                   :title "Menu"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene menu
          (web-proxy/wrap-component [sut/component {:items [{:title "Menu1"
                                                             :href "/"}
                                                            {:title "Menu2"
                                                             :href "/"
                                                             :selected true}
                                                            {:title "Menu3"
                                                             :href "/"}
                                                            {:title "Menu4"
                                                             :href "/"}]}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene menu-auto-select
          (web-proxy/wrap-component [sut/component
                                     {:items [{:title "Menu1"
                                               :href "/"}
                                              {:title "Menu2"
                                               :href "/"
                                               :selected true}
                                              {:title "Menu3"
                                               :href "/"}
                                              {:title "Menu4"
                                               :href "/"}]
                                      :path (.. js/window -location -href)
                                      :burger-position :left}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene menu-burger
          (web-proxy/wrap-component [sut/component
                                     {:items [{:title "Menu1"
                                               :href "/"}
                                              {:title "Menu2"
                                               :href "/"
                                               :selected true}
                                              {:title "Menu3"
                                               :href "/"}
                                              {:title "Menu4"
                                               :href "/"}]
                                      :path (.. js/window -location -href)
                                      :force-burger? true
                                      :burger-position :left}]))
