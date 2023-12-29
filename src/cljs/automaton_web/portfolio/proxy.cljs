(ns automaton-web.portfolio.proxy
  (:require [portfolio.ui :as ui]
            [portfolio.data :as data]
            [automaton-web.react-proxy :as web-react]
            [automaton-web.components.init-components :as web-init-components]
            [portfolio.ui.search :as search]))


(defn iframe-document [] (.-contentDocument (aget (.getElementsByTagName js/document "iframe") 0)))

(def start! ui/start!)

(def search-index search/create-index)

(def register-collection! data/register-collection!)

(defn wrap-component
  "A simple wrapper to prevent to spread that code on all scenes"
  [& cmps]
  (web-react/as-element [:f>
                         #(let [_ (web-init-components/init-elements-js (iframe-document))] (into [:<>] (doall (for [cmp cmps] cmp))))]))
