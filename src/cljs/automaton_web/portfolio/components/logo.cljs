(ns automaton-web.portfolio.components.logo
  (:require
   [automaton-web.components.logo :as sut]
   [automaton-web.portfolio.proxy :as web-proxy]
   [portfolio.reagent-18          :as           portfolio
                                  :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Logo"})

(defscene logo (web-proxy/wrap-component [sut/hephaistox]))
