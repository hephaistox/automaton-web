(ns automaton-web.components.modal
  "Namespace for modal components and related."
  (:require [automaton-web.components.button :as bcb]))

(defn wrap-modal-call
  "Wraps component to become a toggle for a modal.
   Requires a modal id of a modal to show."
  [{:keys [modal-id key]} component]
  ^{:key key}
  [:span
   {:data-te-toggle "modal"
    :data-te-target (str "#" modal-id)} component])

(def close-modal-prop {:data-te-modal-dismiss true})

(defn modal-big
  [{:keys [title body id]}]
  [:div
   {:data-te-modal-init true
    :class ["fixed left-0 top-0 z-[1055] hidden h-full w-full overflow-y-hidden overflow-x-hidden outline-none"]
    :id id
    :tabIndex "-1"
    :aria-labelledby id
    :aria-hidden "true"}
   [:div
    {:data-te-modal-dialog-ref true
     :class
     ["overflow-y-auto pointer-events-none relative h-full w-auto translate-y-[-50px] opacity-0 transition-all duration-300 ease-in-out min-[576px]:mx-auto min-[576px]:mt-7 min-[576px]:h-[calc(100%-3.5rem)] min-[576px]:max-w-[500px] min-[992px]:max-w-[800px]"]}
    [:div
     {:class
      ["pointer-events-auto relative flex h-full lg:h-auto max-h-[100%] w-full flex-col overflow-hidden lg:rounded-md border-none bg-white bg-clip-padding text-current shadow-lg outline-none dark:bg-neutral-600"]}
     [:div
      {:class
       ["flex flex-shrink-0 items-center justify-between rounded-t-md border-b-2 border-neutral-100 border-opacity-100 p-4 dark:border-opacity-50"]}
      title [bcb/x-button {:btn-props close-modal-prop}]] [:div {:class ["relative overflow-y-auto p-4"]} body]]]])

(defn details-modal
  "Modal for displaying something in details with title, description, img and sections.

  Accepts a params map for main parts of the modal described with keys:
  :header being composed of name and title.
  :body containing image, description and sections
  and variable number of maps for displaying sections.
  Each map expects :title and :description for the section and :id for reagent"
  [{:keys [modal-id name title img description]} & sections]
  [modal-big
   {:title [:div {:class ["flex gap-4 items-center grid"]} [:h3 {:class ["text-2xl font-bold leading-8 tracking-tight text-gray-900"]} name]
            [:div {:class ["text-lg leading-8 tracking-tight text-gray-900"]} title]]
    :body [:div {:class ["relative overflow-y-auto p-4 text-base leading-7 text-gray-700 mb-8"]}
           [:img
            {:class ["float-left aspect-[4/5] w-36 flex-none rounded-2xl object-cover mb-1 mr-4"]
             :src img
             :alt (str name " profile picture")}] [:p {:class ["text-lg leading-8 float-none"]} description]
           (when (and sections (not (every? nil? sections)))
             (doall (for [{:keys [id title description]} sections]
                      ^{:key (str modal-id "-" id)}
                      [:div {:class ["mt-8"]} [:h3 {:class ["text-xl font-bold tracking-tight text-gray-900"]} title]
                       [:div {:class ["mt-4"]} description]])))]
    :id modal-id}])
