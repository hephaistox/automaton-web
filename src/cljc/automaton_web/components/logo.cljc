(ns automaton-web.components.logo)

(defn hephaistox
  []
  [:div {:class ["-m-1.5 p-1.5"]}
   [:span {:class ["sr-only"]}
    "Hephaistox"]
   [:img {:class ["h-16 w-auto"]
          :src "/images/logos/hephaistox_logo.png"
          :alt "Hephaistox logo"}]])
