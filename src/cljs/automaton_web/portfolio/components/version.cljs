(ns automaton-web.portfolio.components.version
  (:require [portfolio.reagent-18 :as portfolio :refer-macros [defscene configure-scenes]]
            [automaton-web.portfolio.proxy :as web-proxy]
            [automaton-web.components.version :as sut]))

(configure-scenes {:collection :components
                   :title "Version"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene version (web-proxy/wrap-component [sut/component {:version "1234"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene version-dark
          (web-proxy/wrap-component [sut/component
                                     {:version "1234"
                                      :dark? true}]))
