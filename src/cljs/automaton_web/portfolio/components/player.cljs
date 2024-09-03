(ns automaton-web.portfolio.components.player
  (:require
   [automaton-web.components.player :as sut]
   [automaton-web.portfolio.proxy   :as web-proxy]
   [automaton-web.react-proxy       :as web-react]
   [portfolio.reagent-18            :as           portfolio
                                    :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Player"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene player [] (web-proxy/wrap-component [sut/player]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene player-controls
          []
          (let [pause? (web-react/ratom false)]
            (fn []
              (web-proxy/wrap-component [sut/player {:play-fn (fn [] (reset! pause? true))
                                                     :pause-fn (fn [] (reset! pause? false))
                                                     :pause? @pause?
                                                     :next-fn #(js/alert "next!")
                                                     :prev-fn #(js/alert "previous!")}]))))
