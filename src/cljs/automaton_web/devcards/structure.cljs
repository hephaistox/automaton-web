(ns automaton-web.devcards.structure
  (:require [automaton-web.components.input :as bci]
            [automaton-web.components.form :as bcform]
            [automaton-web.components.footer :as bcf]
            [automaton-web.components.header :as bch]
            [automaton-web.components.structure :as sut]
            [automaton-web.devcards.utils :as bdu]
            [devcards.core :as dc :refer [defcard]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard structure
         (bdu/wrap-component [:div [:h2 "Just a body"] [sut/structure {} [:div "Hello"]] [:h2 "Header and footer"]
                              [sut/structure
                               {:header [:div "Header"]
                                :footer [:div "Footer"]} [:div "Hello"]] [:h2 "Fancy one"]
                              [sut/structure
                               {:header [bch/simple-header
                                         {:logo "Logo"
                                          :size :full
                                          :right-section [:div "Login"]}]
                                :footer [bcf/simple-footer {:title "Hephaistox footer"}]} [:div {:class ["mt-20" "mb-5"]} "Body!"]
                               [bcform/form-basic {}
                                #(bci/email-field (merge %
                                                         {:id "email"
                                                          :size :full
                                                          :required? true})) #(bci/first-name-field %) #(bci/company-field %)]]]))
