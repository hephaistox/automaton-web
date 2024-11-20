(ns automaton-web.portfolio.components.menu
  (:require
   [automaton-web.components.menu :as sut]
   [automaton-web.portfolio.proxy :as web-proxy]
   [portfolio.reagent-18          :as           portfolio
                                  :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Menu"})

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

(defscene menu-auto-select
          (web-proxy/wrap-component [sut/component {:items [{:title "Menu1"
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

(defscene menu-burger
          (web-proxy/wrap-component [sut/component {:items [{:title "Menu1"
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
