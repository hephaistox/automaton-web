(ns automaton-web.components.logo)

(defn logo
  []
  [:div {:class ["-m-1.5 p-1.5"]} [:span {:class ["sr-only"]} "Hephaistox"]
   [:img
    {:class ["h-20 w-auto"]
     :src "/img/hephaistox_logo.png"
     :alt "Hephaistox logo picturing hephaistos god of craftsmanship"}]])
