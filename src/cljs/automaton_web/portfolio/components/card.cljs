(ns automaton-web.portfolio.components.card
  (:require
   [automaton-web.components.card  :as sut]
   [automaton-web.components.cards :as web-cards]
   [automaton-web.portfolio.proxy  :as web-proxy]
   [automaton-web.react-proxy      :as web-react]
   [portfolio.reagent-18           :as           portfolio
                                   :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Cards"})

(def card-params
  {:title "Founder"
   :name "Filthy Frank"
   :img
   "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80"
   :linkedin "#"
   :on-click #(js/alert "Clicked!")})

(defscene card :params card-params [params] (web-proxy/wrap-component [sut/card params]))

(defscene dark-card
          :params
          (assoc card-params :dark? true)
          [params]
          (web-proxy/wrap-component [sut/card params]))

(defscene clickable-cards
          (web-proxy/wrap-component
           [:div "clickable cards will not open properly due to portfolio modal issue"]))

(def section-clickable-cards-modal-params
  {:current-card (web-react/ratom {})
   :cards
   [{:img
     "https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
     :name "Mateusz Mazurczak"
     :title "Is a real human person, not an alien"
     :description "lorem ipsum lorem ipsum"
     :linkedin "https://www.linkedin.com/in/mateuszmazurczak/"}
    {:img
     "https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
     :name "Bill Gates"
     :title "Co-chair, Bill & Melinda Gates Foundation"
     :description "lorem ipsum lorem ipsum ipsum ipsum"
     :linkedin "https://www.linkedin.com/in/williamhgates/"}]})

(def modal-open? (web-react/ratom false))

(defscene section-clickable-cards-modal
          :params
          section-clickable-cards-modal-params
          [params]
          (web-proxy/wrap-component [web-cards/clickable-cards
                                     {:current-card @(:current-card params)
                                      :change-card-fn (fn [card]
                                                        (reset! (:current-card params) card))
                                      :cards (:cards params)
                                      :section {:title "hello"
                                                :description "more words"}
                                      :size :sm
                                      :modal-open? modal-open?}]))

(defscene section-clickable-cards-modal-dark
          :params
          (merge section-clickable-cards-modal-params {:dark? true})
          [params]
          (web-proxy/wrap-component [web-cards/clickable-cards
                                     {:current-card @(:current-card params)
                                      :change-card-fn (fn [card]
                                                        (reset! (:current-card params) card))
                                      :cards (:cards params)
                                      :section {:title "hello"
                                                :description "more words"}
                                      :size :sm
                                      :modal-open? modal-open?}]))
