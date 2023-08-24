(ns automaton-web.devcards.grid-list
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.grid-list :as sut]
            [automaton-web.devcards.utils :as bdu]))
#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard grid-box
  (bdu/wrap-component [:div
                       [:h2
                        {:class ["text-xl"]}
                        "Regular grid-box"]
                       [:h3 "regular grid-box 4 items"]
                       [sut/grid-box {}
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]]

                       [:h3 "regular grid-box 6 items"]
                       [sut/grid-box {}
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]]
                       [:h3 "regular grid-box 10 items"]
                       [sut/grid-box {}
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]]
                       [:h2
                        {:class ["text-xl"]}
                        "Medium grid-box"]
                       [:h3 "Medium grid-box 3 items"]
                       [sut/grid-box {:size :md}
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]]
                       [:h3 "Medium grid-box 5 items"]
                       [sut/grid-box {:size :md}
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]]
                       [:h2
                        {:class ["text-xl"]}
                        "Small grid-box"]
                       [:h3 "Small grid-box 2 items"]
                       [sut/grid-box {:size :sm}
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]]
                       [:h3 "Small grid-box 4 items"]
                       [sut/grid-box {:size :sm}
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]
                        [:div {:class ["bg-gray-200"]} "hello"]]]))
