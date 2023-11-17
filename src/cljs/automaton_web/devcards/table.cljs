(ns automaton-web.devcards.table
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.table :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard map-table
         (bdu/wrap-component [:div [:h2 {:class ["text-xl"]} "Map->table"] [:h3 "Clojure map translated to table"]
                              [sut/map->table
                               {:single-kv 1
                                :double-kv {:one-el 2}
                                :another-double-kv {:one-el "Hello"
                                                    :two-el "Beboop"
                                                    :three-el "I'm third"}
                                :first-level {:second {:third {:fourth 42}
                                                       :third-2 222}
                                              :second-2 10
                                              :second-3 20}}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard table
         (bdu/wrap-component [:div [:h2 {:class ["text-xl"]} "Simple table"] [:h3 "Clojure map translated to table"]
                              [sut/table
                               {:headers ["one" "two" "three"]
                                :rows [["1" "2" "3"] ["first" "second" "third"] ["idk" "more" "more"]]}]]))
