(ns automaton-web.portfolio.components.navigation
  (:require
   [automaton-web.components.navigation :as sut]
   [automaton-web.portfolio.proxy       :as web-proxy]
   [portfolio.reagent-18                :as           portfolio
                                        :refer-macros [defscene
                                                       configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Navigation"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene back-navigation
          (web-proxy/wrap-component [sut/back-navigation {:text "Back!"
                                                          :href ""}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene back-navigation-dark
          (web-proxy/wrap-component [sut/back-navigation {:text "Back!"
                                                          :href ""
                                                          :dark? true}]))
