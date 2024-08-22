(ns automaton-web.components.tooltip)

(defn tooltip
  "Consists of map with text and direction for the tooltip message.
   And component on which the tooltip is shown."
  [{:keys [text direction]
    :or {text "Hoover text"
         direction "top"}}
   component]
  [:span {:data-twe-toggle "tooltip"
          :data-twe-placement direction
          :title text}
   component])
