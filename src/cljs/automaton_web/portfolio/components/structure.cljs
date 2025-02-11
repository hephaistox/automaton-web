(ns automaton-web.portfolio.components.structure
  (:require
   [automaton-web.components.footer    :as web-footer]
   [automaton-web.components.form      :as web-form]
   [automaton-web.components.header    :as web-header]
   [automaton-web.components.input     :as web-input]
   [automaton-web.components.structure :as sut]
   [automaton-web.portfolio.proxy      :as web-proxy]
   [portfolio.reagent-18               :as           portfolio
                                       :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Structure"})

(defscene structure
          (web-proxy/wrap-component [sut/structure {}
                                     [:div "Hello"]]))

(defscene structure-header-footer
          (web-proxy/wrap-component [sut/structure {:header [:div "Header"]
                                                    :footer [:div "Footer"]}
                                     [:div "Hello"]]))

(defscene structure-fancy
          (web-proxy/wrap-component
           [sut/structure {:header [web-header/transparent-header {:logo "Logo"
                                                                   :size :full
                                                                   :right-section [:div "Login"]}]
                           :footer [web-footer/simple-footer {:title "Hephaistox footer"}]}
            [:div {:class ["mt-20" "mb-5"]}
             "Body!"]
            [web-form/form-basic {}
             #(web-input/email-field (merge %
                                            {:id "email"
                                             :size :full
                                             :required? true}))
             #(web-input/first-name-field %)
             #(web-input/company-field %)]]))
