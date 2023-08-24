(ns automaton-web.devcards.select
  (:require
   [devcards.core :as dc :refer [defcard]]

   [automaton-web.components.select :as select]
   [automaton-web.components.simple-select :as ss]
   [automaton-web.devcards.utils :as bdu]))

(defcard select-reagent
  (bdu/wrap-component [select/select {:id "lang"
                                      :name "lang"
                                      :initial-value :en
                                      :class ["bg-red-500"]
                                      :onChange #()}
                       [:option {:key :en :value :en} "en"]
                       [:option {:key :fr :value :fr} "fr"]
                       [:option {:key :pl :value :pl} "pl"]]))

(defcard simple-select-html
  (bdu/wrap-component [ss/simple-select {:id "dinner"
                                         :name "dinner"}
                       [[:option {:key :en :value :en} "en"]
                        [:option {:key :fr :value :fr :selected "selected"} "fr"]
                        [:option {:key :pl :value :pl} "pl"]]]))
