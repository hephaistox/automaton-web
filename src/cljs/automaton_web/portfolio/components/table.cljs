(ns automaton-web.portfolio.components.table
  (:require
   [automaton-web.components.table :as sut]
   [automaton-web.portfolio.proxy  :as web-proxy]
   [portfolio.reagent-18           :as           portfolio
                                   :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Table"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene map-table
          (web-proxy/wrap-component [sut/map->table
                                     {:single-kv 1
                                      :double-kv {:one-el 2}
                                      :another-double-kv {:one-el "Hello"
                                                          :two-el "Beboop"
                                                          :three-el "I'm third"}
                                      :first-level {:second {:third {:fourth 42}
                                                             :third-2 222}
                                                    :second-2 10
                                                    :second-3 20}}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene table
          (web-proxy/wrap-component [sut/table {:headers ["one" "two" "three"]
                                                :rows
                                                [["1" "2" "3"]
                                                 ["first" "second" "third"]
                                                 ["idk" "more" "more"]]}]))
