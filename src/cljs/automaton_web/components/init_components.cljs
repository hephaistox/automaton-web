(ns automaton-web.components.init-components
  "Put here all initialisation needed for components classes (not for each component instance, which should be spread in the code). For instance, everything which is supposed to be done once for each component."
  (:require
   ["tw-elements" :refer [initTE Modal Ripple Tooltip]]
   ["react" :as react]

   [automaton-web.log :as log]))

(defn- for-each-root-rendering
  "Init for each root rendering.
  List here all:
  * tailwind element components."
  []
  (log/trace "Starting Tailwind element")
  (try
    (initTE #js {:Modal Modal
                 :Ripple Ripple})
    ;;https://github.com/mdbootstrap/Tailwind-Elements/issues/1765#issuecomment-1623821125
    ;;https://tailwind-elements.com/docs/standard/components/tooltip/#docsTabsAPI
    ;;tooltips are not initalized yet with initTE despite what documentation say, but they plan to add it
    (let
     [tooltipTriggerList
      (.call
       (.-slice #js [])
       (.querySelectorAll js/document "[data-te-toggle=\"tooltip\"]"))]
      (.map
       tooltipTriggerList
       (fn [tooltipTriggerEl] (new Tooltip tooltipTriggerEl))))

    (catch js/Error e
      (log/error "Unexpected tailwind element issue " e)))
  (log/trace "Ending Tailwind element"))

(defn init-rendering
  "To be called at the root of components rendering"
  []
  (log/trace "Rendering init for base components")
  (react/useEffect for-each-root-rendering
                   #js []))

(defn init-modal [el]
  (Modal. el))

(defn get-modal [el]
  (.getInstance Modal el))

(defn hide-modal [modal-id]
  (-> js/document
      (.getElementById modal-id)
      (get-modal)
      (.hide)))

(defn on-modal-hide [modal-id on-hide-fn]
  (-> js/document
      (.getElementById modal-id)
      (.addEventListener "hidden.te.modal"
                         on-hide-fn)))
