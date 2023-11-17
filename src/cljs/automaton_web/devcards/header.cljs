(ns automaton-web.devcards.header
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.header :as sut]
            [automaton-web.components.menu :as menu]
            [automaton-web.devcards.utils :as bdu]))

(defn menu
  []
  [menu/component
   {:items [{:title "Menu"
             :href "/#/"}
            {:title "Menu1"
             :href "/#/"}
            {:title "Menu2"
             :href "/#/"}]}])

(defcard header (bdu/wrap-component [:div [:h3 {:class ["text-3xl"]} "Show header"] [sut/component (menu)]]))

(defcard simple-header
         (bdu/wrap-component [:div {:class ["flex flex-col gap-8"]}
                              [:div {:class ["h-16"]} [:h3 "Simple-header"]
                               [:div {:class ["relative"]}
                                [sut/simple-header
                                 {:logo "I'm your logo"
                                  :right-section "Right content"}]]]
                              [:div {:class ["h-16"]} [:h3 "Simple-header full size"]
                               [:div {:class ["relative"]}
                                [sut/simple-header
                                 {:size :full
                                  :logo "I'm your logo"
                                  :right-section "Right content"}]]]]))
