(ns automaton-web.portfolio.components.grid-list
  (:require
   [automaton-web.components.grid-list :as sut]
   [automaton-web.portfolio.proxy :as web-proxy]
   [portfolio.reagent-18
    :as
    portfolio
    :refer-macros
    [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Grid list"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene grid-box-4
          (web-proxy/wrap-component [sut/grid-box {}
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene grid-box-6
          (web-proxy/wrap-component [sut/grid-box {}
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene grid-box-10
          (web-proxy/wrap-component
           [sut/grid-box {}
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]
            [:div {:class ["bg-gray-200"]}
             "hello"]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene medium-grid-box-3
          (web-proxy/wrap-component [sut/grid-box {:size :md}
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene medium-grid-box-5
          (web-proxy/wrap-component [sut/grid-box {:size :md}
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene small-grid-box-2
          (web-proxy/wrap-component [sut/grid-box {:size :sm}
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene small-grid-box-4
          (web-proxy/wrap-component [sut/grid-box {:size :sm}
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]
                                     [:div {:class ["bg-gray-200"]}
                                      "hello"]]))
