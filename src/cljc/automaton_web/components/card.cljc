(ns automaton-web.components.card
  "Namespace for card components"
  (:require
   [automaton-web.components.icons :as web-icons]
   [automaton-web.components.modal :as web-modal]))

(defn card
  "Displaying a clickable card/box with content (image, title, name) and optional linkedin icon"
  [{:keys [img name title linkedin on-click additional-props dark?]}]
  [:div
   (merge
    {:class
     ["rounded-2xl px-8 py-10 cursor-pointer flex flex-col text-center h-full"
      (if dark?
        "bg-theme-light hover:bg-orange-100 drop-shadow-md"
        "bg-theme-dark hover:bg-orange-300 drop-shadow-md")]
     :data-twe-ripple-init true
     :data-twe-ripple-color "light"
     :on-click on-click}
    additional-props)
   [:img {:class ["mx-auto h-24 w-24 rounded-full"]
          :src img
          :alt ""}]
   [:h3 {:class ["mt-6 font-bold"
                 (if dark? "text-theme-dark" "text-theme-light")]
         :style {:fontFamily "'Plus Jakarta Sans'"}}
    name]
   [:p {:class
        ["text-sm leading-6"
         (if dark? "text-theme-dark-secondary" "text-theme-light-secondary")]
        :style {:fontFamily "'Plus Jakarta Sans'"}}
    title]
   [:ul {:class ["mt-6 flex justify-center gap-x-6 grow items-end"]
         :role "list"}
    (when linkedin
      [:li
       [web-icons/icon {:path-kw :svg/linkedin
                        :size 1.5
                        :dark? (not dark?)
                        :href linkedin}]])]])

(defn generate-clickable-modal-cards
  [{:keys [cards dark? on-click modal-id]}]
  (doall (for [{:keys [title img name linkedin]
                :as card-info}
               cards]
           (web-modal/wrap-modal-call {:modal-id modal-id
                                       :key (str name)}
                                      [card {:key (str name)
                                             :title title
                                             :name name
                                             :dark? dark?
                                             :linkedin linkedin
                                             :img img
                                             :on-click #(on-click
                                                         card-info)}]))))
