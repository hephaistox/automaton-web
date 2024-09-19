(ns automaton-web.components.tooltip)

(defn tooltip
  "Consists of map with text and direction for the tooltip message.
   And component on which the tooltip is shown."
  [{:keys [text direction]
    :or {text "Hoover text"
         direction "top"}}
   &
   rest]
  [:span {:class ["tooltip"]}
   (for [el rest] ^{:key (str (random-uuid))} el)
   [:span {:class ["tooltiptext" (str "tooltiptext-" direction)]}
    text]])
