(ns automaton-web.components.navigation)

(defn back-navigation [{:keys [href text dark?]}]
  [:a
   {:href href
    :class ["font-semibold leading-7"
            (if dark?
              "text-additional"
              "text-primary")]}
   [:span {:aria-hidden "true"} "← "]
   text])
