(ns automaton-web.portfolio.components.modal
  (:require
   [automaton-web.components.button :as web-button]
   [automaton-web.components.modal  :as sut]
   [automaton-web.portfolio.proxy   :as web-proxy]
   [automaton-web.react-proxy       :as web-react]
   [portfolio.reagent-18            :as           portfolio
                                    :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Modal"})

(def modal-open? (web-react/ratom false))

#_{:clj-kondo/ignore [:unused-binding]}
(defn modal
  [modal-open?]
  (fn [modal-open?] [sut/modal {:modal-open? modal-open?}
                     [sut/backdrop]
                     [sut/title {:name "hello"
                                 :title "world"}]
                     [:div "hello"]
                     [:div "Body"]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 modal-big
 (web-proxy/wrap-component
  [:div
   "For now modal component is not working with portfolio, due to React Portal that renders itself in the root of the document, which is problematic when iframe is used"]
  [web-button/button {:text "Call modal"
                      :on-click (fn [] (swap! modal-open? not))}]
  [:div [modal modal-open?]]))

