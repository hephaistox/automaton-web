(ns automaton-web.devcards.utils
  "Devcards present components so they're seen / tested"
  [:require
   [automaton-web.react-proxy :as bwc]

   [automaton-web.components.init-components :as bcic]])

(defn wrap-component
  "A simple wrapper to prevent to spread that code on all cards"
  [cnt]
  (bwc/as-element
   [:f> #(let [_ (bcic/init-rendering)]
           cnt)]))
