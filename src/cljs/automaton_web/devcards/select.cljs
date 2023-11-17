(ns automaton-web.devcards.select
  (:require [automaton-web.components.simple-select :as ss]
            [automaton-web.devcards.utils :as bdu]
            [devcards.core :as dc :refer [defcard]]))

(defcard select-reagent
         (bdu/wrap-component [ss/simple-select
                              {:id "lang-id"
                               :html-name "lang-html"
                               :class ["bg-red-500"]
                               :on-change #(js/console.log "On changed happened for lang")} [:option {:key :en} "en"]
                              [:option {:key :fr} "fr"] [:option {:key :pl} "pl"]]))

(defcard simple-select-html
         (bdu/wrap-component [ss/simple-select {:id "dinner"}
                              [:option
                               {:key :en
                                :value :en} "en"]
                              [:option
                               {:key :fr
                                :value :fr} "fr"]
                              [:option
                               {:key :pl
                                :value :pl} "pl"]]))
