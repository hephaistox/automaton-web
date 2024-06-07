(ns automaton-web.components.button
  "Clickable buttons"
  (:require
   [automaton-web.components.icons :as web-icons]))

(defn button
  "Basic button component, accepting map with keys:
   :text -> string: for text on the button
   :on-click -> fn: for onclick function to be called when someone clicks on the button accepting event of click
   :class -> vector: for css classes
   :disabled -> bool: to make it uncklickable
   :type -> string: defaults to 'button'"
  [{:keys [text on-click class disabled type]
    :or {type "button"}}]
  [:button
   (merge
    {:data-te-ripple-init true
     :data-te-ripple-color "light"
     :type type
     :disabled disabled
     :class
     (vec
      (concat
       ["inline-block bg-secondary py-3 px-4 lg:px-8 text-white font-semibold rounded-md shadow-[0_4px_9px_-4px_#FF9E4F] transition duration-150 ease-in-out hover:bg-additional hover:shadow-[0_8px_9px_-4px_rgba(255,158,79,0.3),0_4px_18px_0_rgba(255,158,79,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(255,158,79,0.3),0_4px_18px_0_rgba(255,158,79,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(255,158,79,0.3),0_4px_18px_0_rgba(255,158,79,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(255,158,79,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(255,158,79,0.2),0_4px_18px_0_rgba(255,158,79,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(255,158,79,0.2),0_4px_18px_0_rgba(255,158,79,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(255,158,79,0.2),0_4px_18px_0_rgba(255,158,79,0.1)]"
        "disabled:cursor-not-allowed disabled:opacity-70"]
       class))}
    (when on-click {:on-click (fn [e] (on-click e))}))
   text])

(defn icon-button
  [{:keys [btn-props on-click icon-path icon-size]}]
  [:button
   (merge
    {:type "button"
     :class
     ["box-content rounded-none border-none hover:no-underline hover:opacity-75 focus:opacity-100 focus:shadow-none focus:outline-none"]
     :aria-label "Close"
     :on-click on-click}
    btn-props)
   (web-icons/icon {:path-kw icon-path
                    :size icon-size})])

(defn link-button
  "Wraps button with a clickable link, giving quick native way to put hrefs."
  [{:keys [link]} btn-props]
  [:div {:class ["flex justify-center mt-3 lg:mt-5"]}
   [:a {:href link
        :target "_blank"}
    [button btn-props]]])

(defn x-button
  "Exit 'X' button."
  [{:keys [btn-props on-click icon-size]}]
  [icon-button {:btn-props btn-props
                :on-click on-click
                :icon-size icon-size
                :icon-path :svg/x-mark}])
