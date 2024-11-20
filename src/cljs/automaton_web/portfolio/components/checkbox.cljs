(ns automaton-web.portfolio.components.checkbox
  (:require
   [automaton-web.components.input :as sut]
   [automaton-web.portfolio.proxy  :as web-proxy]
   [portfolio.reagent-18           :as           portfolio
                                   :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Checkboxes"})

(defscene checkbox (web-proxy/wrap-component [sut/checkbox]))

(defscene checkbox-title
          (web-proxy/wrap-component [sut/checkbox {:title "Name"}]))

(defscene checkbox-title-description
          (web-proxy/wrap-component
           [sut/checkbox {:title "Name"
                          :description "This is important section to know about etc."}]))

(defscene checkbox-required
          (web-proxy/wrap-component [sut/checkbox {:title "Important to know"
                                                   :description
                                                   "This is important section to know about etc."
                                                   :required? true}]))
(defscene checkbox-invalid
          (web-proxy/wrap-component [sut/checkbox {:invalid? true}]))

(defscene checkbox-required-invalid
          (web-proxy/wrap-component [sut/checkbox {:title "Invalid"
                                                   :description "This is invalid checkbox"
                                                   :invalid? true
                                                   :required? true}]))

(defscene checkbox-error-message
          (web-proxy/wrap-component [sut/checkbox {:title "Invalid checkbox"
                                                   :required? true
                                                   :invalid? true
                                                   :error-message "This needs to be updated!"}]))

(defscene checkbox-disabled
          (web-proxy/wrap-component [sut/checkbox {:title "Can't click me"
                                                   :disabled? true}]))

(defscene checkboxes
          (web-proxy/wrap-component [sut/checkboxes {:title "Name"}]))

(defscene checkboxes-more
          (web-proxy/wrap-component [sut/checkboxes {:title "Name"}
                                     {:title "Name"}
                                     {:title "Name"}
                                     {:title "Name"}]))

(defscene checkboxes-description
          (web-proxy/wrap-component
           [sut/checkboxes {:title "Name"
                            :description "This is important section to know about etc."}
            {:title "Name"
             :description "This is important section to know about etc."}
            {:title "Name"
             :description "This is important section to know about etc."}
            {:title "Name"
             :description "This is important section to know about etc."}]))
(defscene checkboxes-required
          (web-proxy/wrap-component
           [sut/checkboxes {:title "Important to know"
                            :description "This is important section to know about etc."
                            :required? true}
            {:title "Name"
             :description "This is important section to know about etc."}
            {:title "Name"
             :description "This is important section to know about etc."}]))

(defscene checkboxes-required-invalid
          (web-proxy/wrap-component [sut/checkboxes {:title "Invalid"
                                                     :description "This is invalid checkbox"
                                                     :invalid? true
                                                     :required? true}
                                     {:title "Invalid"
                                      :description "This is invalid checkbox"
                                      :invalid? true
                                      :required? true}
                                     {:title "Name"
                                      :description
                                      "This is important section to know about etc."}]))

(defscene checkboxes-error-message
          (web-proxy/wrap-component [sut/checkboxes {:title "Invalid checkbox"
                                                     :required? true
                                                     :invalid? true
                                                     :error-message "This needs to be updated!"}
                                     {:title "Invalid checkbox"
                                      :required? true
                                      :invalid? true
                                      :error-message "This needs to be updated!"}
                                     {:title "Name"
                                      :description
                                      "This is important section to know about etc."}]))

(defscene checkboxes-disabled
          (web-proxy/wrap-component [sut/checkboxes {:title "Can't click me"
                                                     :disabled? true}
                                     {:title "Can't click me"
                                      :disabled? true}
                                     {:title "Can't click me"
                                      :disabled? true}]))
