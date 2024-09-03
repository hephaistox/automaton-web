(ns automaton-web.components.cards
  (:require
   [automaton-web.components.card      :as web-card]
   [automaton-web.components.grid-list :as web-grid-list]
   [automaton-web.components.modal     :as web-modal]
   [automaton-web.components.section   :as web-section]
   [automaton-web.react-proxy          :as web-react]))

(defn- card-title
  [name title]
  [:div
   {:class
    ["flex flex-shrink-0 items-center justify-between rounded-t-md border-b-2 border-neutral-100 border-opacity-100 p-4 dark:border-opacity-50"]}
   [:h3 {:class ["text-2xl font-bold leading-8 tracking-tight text-gray-900"]}
    name]
   [:div {:class ["text-lg leading-8 tracking-tight text-gray-900"]}
    title]])

(defn- card-description
  [img name description & sections]
  [:div {:class ["relative overflow-y-auto p-4 text-base leading-7 text-gray-700 mb-8"]}
   [:img {:class ["float-left aspect-[4/5] w-36 flex-none rounded-2xl object-cover mb-1 mr-4"]
          :src img
          :alt (str name " profile picture")}]
   [:p {:class ["text-lg leading-8 float-none"]}
    description]
   (when (and sections (not (every? nil? sections)))
     (into [:span]
           (for [{:keys [title description]} sections]
             [:div {:class ["mt-8"]}
              [:h3 {:class ["text-xl font-bold tracking-tight text-gray-900"]}
               title]
              [:div {:class ["mt-4"]}
               description]])))])

(defn- card-details-modal
  [{:keys [img name description title modal-open? sections]}]
  [web-modal/modal {:modal-open? modal-open?
                    :backdrop? true}
   [web-modal/title [card-title name title]]
   [apply card-description img name description sections]])

(defn- generate-clickable-cards
  [{:keys [cards dark? on-click]}]
  (doall (for [{:keys [title img name linkedin]
                :as card-info}
               cards]
           [web-card/card {:key (str name)
                           :title title
                           :name name
                           :dark? dark?
                           :linkedin linkedin
                           :img img
                           :on-click (fn [] (on-click card-info))}])))

#_{:clj-kondo/ignore [:unused-binding]}
(defn clickable-cards
  "Section with clickable cards, that after click are opened in a modal to display more details.
  Params:
  * dark? - [optional] color theme
  * current-card - Currently displayed card, expects :img :name :title and :description and (optional) :sections to display that information on the details page
  * change-card-fn - function to change the currently displayed details card - triggered at card :on-click
  * cards - all cards that are displayed in the section, it's a sequence of maps with :title :img :name (optional) :linkedin
  * size - section grid size
  * modal-id - id of a modal to open
  * section - [optional] section description map with :title :description"
  [{:keys [dark? current-card change-card-fn cards size section modal-open?]
    :or {modal-open? (web-react/ratom false)}}]
  (fn [{:keys [dark? current-card change-card-fn cards size section]}]
    [web-section/section-full-container {:dark? dark?}
     [web-section/section-description (merge section {:dark? dark?})]
     [web-grid-list/grid-box {:size size
                              :class ["mt-8"]}
      (generate-clickable-cards
       {:cards cards
        :on-click (fn [card-info] (reset! modal-open? true) (change-card-fn card-info))
        :dark? dark?})]
     (when (:name current-card)
       [card-details-modal {:img (:img current-card)
                            :name (:name current-card)
                            :description (:description current-card)
                            :title (:title current-card)
                            :sections (:sections current-card)
                            :modal-open? modal-open?}])]))
