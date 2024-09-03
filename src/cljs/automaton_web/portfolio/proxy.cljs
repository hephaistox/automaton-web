(ns automaton-web.portfolio.proxy
  (:require
   [automaton-core.utils.uuid-gen :as uuid-gen]
   [portfolio.data                :as data]
   [portfolio.ui                  :as ui]
   [portfolio.ui.search           :as search]))

(defn iframe-document [] (.-contentDocument (aget (.getElementsByTagName js/document "iframe") 0)))

(def start! ui/start!)

(def search-index search/create-index)

(def register-collection! data/register-collection!)

(defn wrap-component
  "A simple wrapper to enable additional code to be added for each scene.
   Right now it's empty"
  [& cmps]
  [:span (doall (for [cmp cmps] ^{:key (str (uuid-gen/unguessable))} cmp))])
