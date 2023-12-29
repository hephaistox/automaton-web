(ns automaton-web.portfolio.components.icons
  (:require [automaton-web.components.icons :as sut]
            [automaton-web.portfolio.proxy :as web-proxy]
            [portfolio.reagent-18 :as portfolio :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Icons"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene icon (web-proxy/wrap-component [sut/icon {}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene icon-small-size (web-proxy/wrap-component [sut/icon {:size 0.5}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene icon-big-size (web-proxy/wrap-component [sut/icon {:size 2}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene icon-green (web-proxy/wrap-component [sut/icon {:class ["icon-green"]}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene icon-red (web-proxy/wrap-component [sut/icon {:class ["icon-red"]}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene icon-yellow (web-proxy/wrap-component [sut/icon {:class ["icon-yellow"]}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene all-icons
          (web-proxy/wrap-component
           (vec (concat [:div] (for [i sut/icons-path] (let [[name] i] [:div [:h2 (str "Icon " name)] [sut/icon {:path-kw name}]]))))))
