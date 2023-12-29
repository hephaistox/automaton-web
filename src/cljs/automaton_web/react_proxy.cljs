(ns automaton-web.react-proxy
  "Commonly used symbols for easy access in the ClojureScript REPL during
  development."
  (:require [reagent.dom.client :as rdc]
            [reagent.core :as r]))

(defn render-id
  [app-id component]
  (let [el (js/document.getElementById app-id)
        root (rdc/create-root el)]
    (rdc/render root component)
    root))

(defn render-ids
  [& ids]
  (loop [[app-id component & r] ids]
    (render-id app-id component)
    (when r (recur r))))

(def ratom r/atom)

(def create-class r/create-class)

(def as-element r/as-element)
