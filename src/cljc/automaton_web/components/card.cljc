(ns automaton-web.components.card
  "Namespace for card components"
  (:require [automaton-web.components.icons :as bci]))

(defn card
  "Displaying a clickable card/box with content (image, title, name) and optional linkedin icon"
  [{:keys [img name title linkedin on-click additional-props dark?]}]
  [:div  (merge {:class ["rounded-2xl px-8 py-10 cursor-pointer flex flex-col text-center"
                         (if dark?
                           "bg-gray-800 hover:bg-gray-600"
                           "bg-gray-50 hover:bg-orange-100 drop-shadow-md")]

                 :data-te-ripple-init true
                 :data-te-ripple-color "light"
                 :on-click on-click}
                additional-props)
   [:img
    {:class ["mx-auto h-24 w-24 rounded-full"]
     :src img
     :alt ""}]
   [:h3
    {:class ["mt-6 font-bold"
             (if dark?
               "text-white"
               "text-gray-800")]
     :style {:fontFamily "'Plus Jakarta Sans'"}} name]
   [:p
    {:class ["text-sm leading-6"
             (if dark?
               "text-gray-400"
               "text-gray-600")]
     :style {:fontFamily "'Plus Jakarta Sans'"}}
    title]
   [:ul
    {:class ["mt-6 flex justify-center gap-x-6 grow items-end"]
     :role "list"}
    (when linkedin
      [:li
       [bci/icon
        {:path-kw :svg/linkedin
         :dark? dark?
         :href linkedin}]])]])
