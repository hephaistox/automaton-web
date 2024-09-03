(ns automaton-web.portfolio.components.checkbox
  (:require
   [automaton-web.components.input :as sut]
   [automaton-web.portfolio.proxy  :as web-proxy]
   [portfolio.reagent-18           :as           portfolio
                                   :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Checkboxes"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkbox (web-proxy/wrap-component [sut/checkbox]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkbox-title
          (web-proxy/wrap-component [sut/checkbox {:title "Name"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkbox-title-description
          (web-proxy/wrap-component
           [sut/checkbox {:title "Name"
                          :description "This is important section to know about etc."}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkbox-required
          (web-proxy/wrap-component [sut/checkbox {:title "Important to know"
                                                   :description
                                                   "This is important section to know about etc."
                                                   :required? true}]))
#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkbox-invalid
          (web-proxy/wrap-component [sut/checkbox {:invalid? true}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkbox-required-invalid
          (web-proxy/wrap-component [sut/checkbox {:title "Invalid"
                                                   :description "This is invalid checkbox"
                                                   :invalid? true
                                                   :required? true}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkbox-error-message
          (web-proxy/wrap-component [sut/checkbox {:title "Invalid checkbox"
                                                   :required? true
                                                   :invalid? true
                                                   :error-message "This needs to be updated!"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkbox-disabled
          (web-proxy/wrap-component [sut/checkbox {:title "Can't click me"
                                                   :disabled? true}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkboxes
          (web-proxy/wrap-component [sut/checkboxes {:title "Name"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkboxes-more
          (web-proxy/wrap-component [sut/checkboxes {:title "Name"}
                                     {:title "Name"}
                                     {:title "Name"}
                                     {:title "Name"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
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
#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkboxes-required
          (web-proxy/wrap-component
           [sut/checkboxes {:title "Important to know"
                            :description "This is important section to know about etc."
                            :required? true}
            {:title "Name"
             :description "This is important section to know about etc."}
            {:title "Name"
             :description "This is important section to know about etc."}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
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

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
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

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene checkboxes-disabled
          (web-proxy/wrap-component [sut/checkboxes {:title "Can't click me"
                                                     :disabled? true}
                                     {:title "Can't click me"
                                      :disabled? true}
                                     {:title "Can't click me"
                                      :disabled? true}]))
