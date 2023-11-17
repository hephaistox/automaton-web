(ns automaton-web.devcards.version
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.version :as sut]
            [automaton-web.devcards.utils :as bdu]))

(defcard version
         (bdu/wrap-component [:div {:class ["flex flex-col gap-8"]}
                              [:div {:class ["relative flex"]} [:h1 {:class ["text-3xl"]} "Show the version"]
                               [sut/component {:version "1234"}]]
                              [:div {:class ["relative flex bg-black h-12"]} [:h1 {:class ["text-xl text-white"]} "Dark version"]
                               [sut/component
                                {:version "1234"
                                 :dark? true}]]]))
