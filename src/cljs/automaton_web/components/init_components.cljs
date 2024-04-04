(ns automaton-web.components.init-components
  "Put here all initialisation needed for components classes (not for each component instance, which should be spread in the code). For instance, everything which is supposed to be done once for each component."
  (:require
   ["react"            :as react]
   ["tw-elements"      :refer [Modal Ripple Tooltip initTE]]
   [automaton-core.log :as core-log]))

(defn- for-each-root-rendering
  "Init for each root rendering.
  List here all:
  * tailwind element components."
  [document]
  (core-log/trace "Starting Tailwind element")
  (try (initTE #js {:Modal Modal
                    :Ripple Ripple})
       ;;https://github.com/mdbootstrap/Tailwind-Elements/issues/1765#issuecomment-1623821125
       ;;https://tailwind-elements.com/docs/standard/components/tooltip/#docsTabsAPI
       ;;tooltips are not initalized yet with initTE despite what
       ;;documentation say, but they plan to add it
       (let [tooltipTriggerList (.call (.-slice #js [])
                                       (.querySelectorAll
                                        document
                                        "[data-te-toggle=\"tooltip\"]"))]
         (.map tooltipTriggerList
               (fn [tooltipTriggerEl] (new Tooltip tooltipTriggerEl))))
       (catch js/Error e
         (core-log/error-exception (ex-info "Unexpected tailwind-element issue "
                                            {:error e}))))
  (core-log/trace "Ending Tailwind-element")
  js/undefined)

(defn init-rendering
  "To be called at the root of components rendering"
  ([] (init-rendering js/document))
  ([document]
   (core-log/trace "Rendering init for base components")
   (react/useEffect #(for-each-root-rendering document))))

(defn init-elements-js
  "To be called to initialize tw-elements in js"
  [document]
  (let
    [script (.createElement document "script")
     _add-script-src
     (set!
      (.-src script)
      "https://cdn.jsdelivr.net/npm/tw-elements/dist/js/tw-elements.umd.min.js")]
    (.appendChild (aget (.getElementsByTagName document "head") 0) script)))

(defn init-modal [el] (Modal. el))

(defn get-modal [el] (.getInstance Modal el))

(defn hide-modal
  ([modal-id] (hide-modal js/document modal-id))
  ([document modal-id]
   (some-> document
           (.getElementById modal-id)
           (get-modal)
           (.hide))))

(defn on-modal-hide
  ([modal-id on-hide-fn] (on-modal-hide js/document modal-id on-hide-fn))
  ([document modal-id on-hide-fn]
   (some-> document
           (.getElementById modal-id)
           (.addEventListener "hidden.te.modal" on-hide-fn))))
