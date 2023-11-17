(ns automaton-web.devcards.input
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.input :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard text-field
         (bdu/wrap-component
          [:div [:h3 "Empty"] [sut/text-field] [:h3 "Title"] [sut/text-field {:text "Name"}] [:h3 "Placeholder"]
           [sut/text-field
            {:text "Name"
             :placeholder "e.g. John"}] [:h3 "Size comparison"]
           [:div {:class ["grid grid-cols-2"]} [sut/text-field] [sut/text-field {:size :full}]] [:h3 "Required"]
           [sut/text-field
            {:text "Password"
             :required? true}] [:h3 "Invalid"]
           [sut/text-field
            {:text "Invalid field"
             :invalid? true}] [:h3 "Required and invalid"]
           [sut/text-field
            {:text "Invalid field"
             :invalid? true
             :required? true}] [:h3 "And with error message"]
           [sut/text-field
            {:text "Invalid field"
             :required? true
             :invalid? true
             :error-message "This needs to be updated!"}] [:h3 "Disabled"]
           [sut/text-field
            {:text "Name"
             :disabled? true}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard checkbox
         (bdu/wrap-component
          [:div [:h3 "Empty"] [sut/checkbox] [:h3 "Title"] [sut/checkbox {:title "Name"}] [:h3 "Title & description"]
           [sut/checkbox
            {:title "Name"
             :description "This is important section to know about etc."}] [:h3 "Required"]
           [sut/checkbox
            {:title "Important to know"
             :description "This is important section to know about etc."
             :required? true}] [:h3 "Invalid"] [sut/checkbox {:invalid? true}] [:h3 "Required and invalid"]
           [sut/checkbox
            {:title "Invalid"
             :description "This is invalid checkbox"
             :invalid? true
             :required? true}] [:h3 "And with error message"]
           [sut/checkbox
            {:title "Invalid checkbox"
             :required? true
             :invalid? true
             :error-message "This needs to be updated!"}] [:h3 "Disabled"]
           [sut/checkbox
            {:title "Can't click me"
             :disabled? true}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard email-field
         (bdu/wrap-component [:div [:h3 "Empty"] [sut/email-field] [:h3 "Size comparison"]
                              [:div {:class ["grid grid-cols-2"]} [sut/email-field] [sut/email-field {:size :full}]] [:h3 "Required"]
                              [sut/email-field {:required? true}] [:h3 "Invalid"] [sut/email-field {:invalid? true}]
                              [:h3 "Required and invalid"]
                              [sut/email-field
                               {:invalid? true
                                :required? true}] [:h3 "And with error message"]
                              [sut/email-field
                               {:required? true
                                :invalid? true
                                :error-message "This needs to be updated!"}] [:h3 "Disabled"] [sut/email-field {:disabled? true}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard first-name-field
         (bdu/wrap-component [:div [:h3 "Empty"] [sut/first-name-field] [:h3 "Size comparison"]
                              [:div {:class ["grid grid-cols-2"]} [sut/first-name-field] [sut/first-name-field {:size :full}]]
                              [:h3 "Required"] [sut/first-name-field {:required? true}] [:h3 "Invalid"]
                              [sut/first-name-field {:invalid? true}] [:h3 "Required and invalid"]
                              [sut/first-name-field
                               {:invalid? true
                                :required? true}] [:h3 "And with error message"]
                              [sut/first-name-field
                               {:required? true
                                :invalid? true
                                :error-message "This needs to be updated!"}] [:h3 "Disabled"] [sut/first-name-field {:disabled? true}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard last-name-field
         (bdu/wrap-component [:div [:h3 "Empty"] [sut/last-name-field] [:h3 "Size comparison"]
                              [:div {:class ["grid grid-cols-2"]} [sut/last-name-field] [sut/last-name-field {:size :full}]]
                              [:h3 "Required"] [sut/last-name-field {:required? true}] [:h3 "Invalid"]
                              [sut/last-name-field {:invalid? true}] [:h3 "Required and invalid"]
                              [sut/last-name-field
                               {:invalid? true
                                :required? true}] [:h3 "And with error message"]
                              [sut/last-name-field
                               {:required? true
                                :invalid? true
                                :error-message "This needs to be updated!"}] [:h3 "Disabled"] [sut/last-name-field {:disabled? true}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard company-field
         (bdu/wrap-component [:div [:h3 "Empty"] [sut/company-field] [:h3 "Size comparison"]
                              [:div {:class ["grid grid-cols-2"]} [sut/company-field] [sut/company-field {:size :full}]] [:h3 "Required"]
                              [sut/company-field {:required? true}] [:h3 "Invalid"] [sut/company-field {:invalid? true}]
                              [:h3 "Required and invalid"]
                              [sut/company-field
                               {:invalid? true
                                :required? true}] [:h3 "And with error message"]
                              [sut/company-field
                               {:required? true
                                :invalid? true
                                :error-message "This needs to be updated!"}] [:h3 "Disabled"] [sut/company-field {:disabled? true}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard
 checkboxes
 (bdu/wrap-component
  [:div [:h3 "Single"] [sut/checkboxes {:title "Name"}] [:h3 "Title"]
   [sut/checkboxes {:title "Name"} {:title "Name"} {:title "Name"} {:title "Name"}] [:h3 "Title & description"]
   [sut/checkboxes
    {:title "Name"
     :description "This is important section to know about etc."}
    {:title "Name"
     :description "This is important section to know about etc."}
    {:title "Name"
     :description "This is important section to know about etc."}
    {:title "Name"
     :description "This is important section to know about etc."}] [:h3 "Required"]
   [sut/checkboxes
    {:title "Important to know"
     :description "This is important section to know about etc."
     :required? true}
    {:title "Name"
     :description "This is important section to know about etc."}
    {:title "Name"
     :description "This is important section to know about etc."}] [:h3 "Required and invalid"]
   [sut/checkboxes
    {:title "Invalid"
     :description "This is invalid checkbox"
     :invalid? true
     :required? true}
    {:title "Invalid"
     :description "This is invalid checkbox"
     :invalid? true
     :required? true}
    {:title "Name"
     :description "This is important section to know about etc."}] [:h3 "And with error message"]
   [sut/checkboxes
    {:title "Invalid checkbox"
     :required? true
     :invalid? true
     :error-message "This needs to be updated!"}
    {:title "Invalid checkbox"
     :required? true
     :invalid? true
     :error-message "This needs to be updated!"}
    {:title "Name"
     :description "This is important section to know about etc."}] [:h3 "Disabled"]
   [sut/checkboxes
    {:title "Can't click me"
     :disabled? true}
    {:title "Can't click me"
     :disabled? true}
    {:title "Can't click me"
     :disabled? true}]]))
