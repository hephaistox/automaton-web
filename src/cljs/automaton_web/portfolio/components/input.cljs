(ns automaton-web.portfolio.components.input
  (:require
   [automaton-web.components.input :as sut]
   [automaton-web.portfolio.proxy  :as web-proxy]
   [portfolio.reagent-18           :as           portfolio
                                   :refer-macros [defscene configure-scenes]]
   [reagent.core                   :as r]))

(configure-scenes {:collection :components
                   :title "Inputs"})

(defscene text-field (web-proxy/wrap-component [sut/text-field]))

(defscene text-field-title
          (web-proxy/wrap-component [sut/text-field {:text "Name"}]))

(defscene text-field-placeholder
          (web-proxy/wrap-component [sut/text-field {:text "Name"
                                                     :placeholder "e.g. John"}]))

(defscene text-field-value-controlled-outside
          (let [text-value (r/atom "Initial-text")]
            (fn []
              (web-proxy/wrap-component [sut/text-field {:text "Iniated-field"
                                                         :on-change-fn (fn [v]
                                                                         (reset! text-value v))
                                                         :value @text-value}]))))

(defscene text-field-full
          (web-proxy/wrap-component [sut/text-field {:size :full}]))

(defscene text-field-required
          (web-proxy/wrap-component [sut/text-field {:text "Password"
                                                     :required? true}]))

(defscene text-field-invalid
          (web-proxy/wrap-component [sut/text-field {:text "Invalid field"
                                                     :invalid? true}]))

(defscene text-field-required-invalid
          (web-proxy/wrap-component [sut/text-field {:text "Invalid field"
                                                     :invalid? true
                                                     :required? true}]))

(defscene text-field-error-message
          (web-proxy/wrap-component [sut/text-field {:text "Invalid field"
                                                     :required? true
                                                     :invalid? true
                                                     :error-message "This needs to be updated!"}]))

(defscene text-field-disabled
          (web-proxy/wrap-component [sut/text-field {:text "Name"
                                                     :disabled? true}]))

(defscene email-field
          "It also follows all the states of text-field."
          (web-proxy/wrap-component [sut/email-field]))

(defscene password-field
          "It also follows all the states of text-field."
          (web-proxy/wrap-component [sut/password-field]))

(defscene first-name-field
          "It also follows all the states of text-field."
          (web-proxy/wrap-component [sut/first-name-field]))

(defscene last-name-field
          "It also follows all the states of text-field."
          (web-proxy/wrap-component [sut/last-name-field]))

(defscene company-field
          "It also follows all the states of text-field."
          (web-proxy/wrap-component [sut/company-field]))
