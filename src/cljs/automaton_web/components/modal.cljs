(ns automaton-web.components.modal
  "Namespace for modal components and related."
  (:require
   ["@headlessui/react" :refer (Dialog DialogBackdrop DialogPanel DialogTitle)]))

(defn backdrop
  []
  [:>
   DialogBackdrop
   {:transition true
    :className
    "fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity data-[closed]:opacity-0 data-[enter]:duration-300 data-[leave]:duration-200 data-[enter]:ease-out data-[leave]:ease-in"}])

(defn title [props & els] [:> DialogTitle (or props {}) (into [:span] (for [el els] el))])

(defn modal
  [{:keys [modal-open? backdrop?]
    :or {modal-open? false}}
   &
   els]
  [:>
   Dialog
   {:open @modal-open?
    :className "relative z-10 h-full w-full"
    :onClose (fn [] (reset! modal-open? false))}
   (when backdrop? [backdrop])
   [:div {:className "fixed inset-0 w-screen overflow-y-auto p-4"}
    [:div {:className "flex min-h-full items-center justify-center"}
     [:>
      DialogPanel
      {:transition true
       :as "div"
       :class
       ["overflow-hidden pointer-events-none relative h-full max-w-lg translate-y-[-50px] min-[576px]:mx-auto min-[576px]:mt-7 min-[576px]:h-[calc(100%-3.5rem)] min-[576px]:max-w-[500px] min-[992px]:max-w-[800px] lg:rounded-md border-none bg-white bg-clip-padding text-current shadow-lg outline-none dark:bg-neutral-600"
        "transform transition-all data-[closed]:translate-y-4 data-[closed]:opacity-0 data-[enter]:duration-300 data-[leave]:duration-200 data-[enter]:ease-out data-[leave]:ease-in data-[closed]:sm:translate-y-0 data-[closed]:sm:scale-95"]}
      (when (and els (not (every? nil? els))) (into [:span] (for [el els] el)))]]]])
