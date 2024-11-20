(ns automaton-web.portfolio.components.spinner
  (:require
   [automaton-web.components.spinner :as sut]
   [automaton-web.portfolio.proxy    :as web-proxy]
   [portfolio.reagent-18             :as           portfolio
                                     :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Spinner"})

(defscene spinner (web-proxy/wrap-component [sut/spinner]))
