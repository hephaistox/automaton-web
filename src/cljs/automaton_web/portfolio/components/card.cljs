(ns automaton-web.portfolio.components.card
  (:require [automaton-web.components.card :as sut]
            [automaton-web.portfolio.proxy :as web-proxy]
            [portfolio.reagent-18 :as portfolio :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Cards"})

(def card-params
  {:title "Founder"
   :name "Filthy Frank"
   :img
   "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80"
   :linkedin "#"
   :on-click #(js/alert "Clicked!")})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene card :params card-params [params] (web-proxy/wrap-component [sut/card params]))

(defscene dark-card :params (assoc card-params :dark? true) [params] (web-proxy/wrap-component [sut/card params]))
