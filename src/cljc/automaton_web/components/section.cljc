(ns automaton-web.components.section
  "Components to visually display and separate content on the page."
  (:require
   [automaton-web.components.button :as web-button]
   [automaton-web.components.card :as web-card]
   [automaton-web.components.grid-list :as web-grid-list]
   [automaton-web.components.modal :as web-modal]))

(defn section-full-container
  [{:keys [dark? class]} & components]
  [:div {:class (vec (concat ["h-fit block p-8"
                              (if dark? "bg-theme-dark" "bg-theme-light")]
                             class))}
   (into [:div {:class ["mx-auto w-full px-6 lg:px-8 self-center"]}]
         (for [component components] component))])

(defn section-text-container [{:keys [class]} & components]
  [:div {:class
         (vec (concat ["mx-auto max-w-3xl text-base leading-7"]
                      class))}
   (into [:div
          (for [component components]
            component)])])

(defn section-description
  [{:keys [title description dark?]}]
  [:div {:class ["mx-auto sm:text-center pt-4"]}
   [:h2 {:class ["text-3xl font-bold tracking-tight sm:text-4xl"
                 (if dark? "text-theme-light" "text-theme-dark")]}
    title]
   [:p {:class
        ["mt-6 max-w-2xl text-lg leading-8 m-auto mt-5"
         (if dark? "text-theme-light-secondary" "text-theme-dark-secondary")]}
    description]])

(defn section-text-button
  "Section with text on the left and button on the right.
   It should not contain to much text.
   Expects map with:
   :text - string for the text to display
   :btn-props - map to pass to the button e.g. :btn-props {:text \"SUBMIT!\"}
   :dark? boolean for styling to decide if the component should be displayed in dark or light version.
   :button - optional, to supply whole button component"
  [{:keys [text button btn-text dark?]}]
  [:div
   {:class
    ["mx-auto grid max-w-7xl grid-cols-1 gap-10 px-6 lg:grid-cols-12 lg:gap-8 lg:px-8"]}
   [:div
    {:class
     ["max-w-xl text-3xl font-bold tracking-tight sm:text-4xl lg:col-span-7 flex"
      (if dark? "text-theme-light" "text-theme-dark")]}
    [:p {:class ["inline sm:block lg:inline xl:block m-auto"]}
     text]]
   [:div {:class ["m-auto lg:col-span-3 flex justify-center items-center"]}
    (if button
      button
      [web-button/button {:text btn-text}])]])

(defn text-section
  [{:keys [title description pre-title dark?]} & subsections]
  [section-text-container {:class [(if dark? "text-theme-light-secondary" "text-theme-dark-secondary")]}
   (when pre-title pre-title)
   [:h1 {:class ["mt-2 text-3xl font-bold tracking-tight sm:text-4xl"
                 (if dark? "text-theme-light" "text-theme-dark")]}
    title]
   [:div {:class ["mt-10 max-w-2xl"]}
    description
    (for [{:keys [title description]} subsections]
      ^{:key (str "subsection: " title)}
      [:div {:class ["mt-16 max-w-2xl"]}
       [:h2 {:class ["text-2xl font-bold tracking-tight"
                     (if dark? "text-theme-light" "text-theme-dark")]}
        title]
       [:div {:class ["mt-6"]}
        description]])]])

(defn section-text-video
  "Section with text on the left and video on the right."
  [{:keys [title subtitle text btn-props btn-link video-src dark?]}]
  [:div
   {:class
    ["mx-auto max-w-7xl p-0  sm:py-32 lg:flex lg:items-center lg:gap-x-10 lg:px-8 lg:py-40"
     (if dark? "bg-theme-dark" "bg-theme-light")]}
   [:div {:class ["flex flex-col gap-8"]}
    [:div {:class ["justify-self-center pr-4"]}
     [:div
      {:class
       ["flex flex-col font-bold leading-tight lg:leading-none w-max m-auto  lg:pt-5 text-4xl lg:text-5xl"
        (if dark? "text-theme-light" "text-theme-dark")]}
      title]
     [:figcaption {:class ["mt-8 text-base text-end"]}
      [:div {:class ["font-semibold"
                     (if dark? "text-theme-light" "text-theme-dark")]}
       subtitle]
      [:div {:class ["mt-1"
                     (if dark?
                       "text-theme-light-secondary"
                       "text-theme-dark-secondary")]}
       text]]]
    [web-button/link-button {:link btn-link}
     (merge btn-props {:class ["hidden lg:block"]})]]
   [:iframe {:frameBorder "0"
             :scrolling "no"
             :class ["w-full aspect-video pt-8 lg:pl-8"]
             :allowFullScreen true
             :src video-src}]
   [web-button/link-button {:link btn-link}
    (merge btn-props {:class ["lg:hidden block w-full mt-8"]})]])

(defn- card-details-modal
  [{:keys [img name description title modal-id sections]}]
  [apply
   web-modal/details-modal
   {:img img
    :name name
    :description description
    :title title
    :modal-id modal-id}
   sections])

(defn section-clickable-cards-modal
  "Section with clickable cards, that after click are opened in a modal to display more details.
  Params:
  * dark? - [optional] color theme
  * current-card - Currently displayed card, expects :img :name :title and :description and (optional) :sections to display that information on the details page
  * change-card-fn - function to change the currently displayed details card - triggered at card :on-click
  * cards - all cards that are displayed in the section, it's a sequence of maps with :title :img :name (optional) :linkedin
  * size - section grid size
  * modal-id - id of a modal to open
  * section - [optional] section description map with :title :description"
  [{:keys [dark? current-card change-card-fn cards size section modal-id]}]
  [section-full-container {:dark? dark?}
   [section-description (merge section {:dark? dark?})]
   [web-grid-list/grid-box {:size size
                            :class ["mt-8"]}
    (web-card/generate-clickable-modal-cards {:cards cards
                                              :modal-id modal-id
                                              :on-click change-card-fn
                                              :dark? dark?})]
   [card-details-modal {:img (:img current-card)
                        :name (:name current-card)
                        :description (:description current-card)
                        :title (:title current-card)
                        :sections (:sections current-card)
                        :modal-id modal-id}]])
