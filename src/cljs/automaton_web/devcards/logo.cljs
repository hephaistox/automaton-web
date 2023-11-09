(ns automaton-web.devcards.logo
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.logo :as logo]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard logo
         (bdu/wrap-component [:div [:h1 {:class ["text-3xl"]} "Logo"] [:h2 {:class ["text-2xl"]} "Normal size"] [:h3 "Default logo"]
                              [logo/logo {:url "/"}]]))
