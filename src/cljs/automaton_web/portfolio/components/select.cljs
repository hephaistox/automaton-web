(ns automaton-web.portfolio.components.select
  (:require
   [automaton-web.components.simple-select :as sut]
   [automaton-web.portfolio.proxy          :as web-proxy]
   [portfolio.reagent-18                   :as           portfolio
                                           :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Select"})

(defscene simple-select-reagent
          (web-proxy/wrap-component
           [sut/simple-select {:id "lang-id"
                               :html-name "lang-html"
                               :class ["bg-red-500"]
                               :on-change #(js/console.log "On changed happened for lang")}
            [:option {:key :en}
             "en"]
            [:option {:key :fr}
             "fr"]
            [:option {:key :pl}
             "pl"]]))

(defscene simple-select-html
          (web-proxy/wrap-component [sut/simple-select {:id "dinner"}
                                     [:option {:key :en
                                               :value :en}
                                      "en"]
                                     [:option {:key :fr
                                               :value :fr}
                                      "fr"]
                                     [:option {:key :pl
                                               :value :pl}
                                      "pl"]]))
