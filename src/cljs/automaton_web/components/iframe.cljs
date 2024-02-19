(ns automaton-web.components.iframe
  (:require
   [automaton-web.react-proxy :as web-react]))

(defn- get-scroll-height
  "Scroll height is calculated from `iframe-id` body children height"
  [id]
  (let [main-el (.getElementById js/document id)
        mutation-children (.. main-el -contentWindow -document -body -children)
        scrollHeight (apply + (map #(.. % -scrollHeight) mutation-children))]
    scrollHeight))

(defn- calculate-new-height
  "Calculates new height, 50 is added to make sure everything is visible at the bottom of the page."
  [height]
  (+ 50 height))

(defn- set-scroll-height
  [id height]
  (set! (.. (.getElementById js/document id) -style -height) (str height "px")))

(defn- waitForIframeContentAndObserve
  "Waits for `iframe-id` DOM body children, when they exist for each observe fn is run on `observer` and `resolve-fn`.
   Optionally accepts `path` to compare it with iframe document pathname."
  [{:keys [resolve-fn iframe-id observer path]}]
  (let [iframe-elm (.getElementById js/document iframe-id)
        pathname (some-> iframe-elm
                         .-contentDocument
                         .-location
                         .-pathname)
        iframe-body-children (some-> iframe-elm
                                     .-contentWindow
                                     .-document
                                     .-body
                                     .-children)]
    (if (and iframe-body-children
             (> (.-length iframe-body-children) 0)
             (or (nil? path) (= pathname path)))
      (do (doseq [el iframe-body-children] (.observe observer el))
          (resolve-fn iframe-body-children))
      (js/setTimeout #(waitForIframeContentAndObserve {:resolve-fn resolve-fn
                                                       :iframe-id iframe-id
                                                       :observer observer})
                     200))))

(defn iframe
  [{:keys [id src]}]
  (let [initialSH (web-react/ratom 0)
        iframeContentHeightObserver
        (js/ResizeObserver. (fn [_]
                              (let [scrollHeight (get-scroll-height id)
                                    new-height (calculate-new-height
                                                scrollHeight)]
                                (when-not (= @initialSH scrollHeight)
                                  (reset! initialSH new-height)
                                  (set-scroll-height id new-height)))))]
    (web-react/create-class
     {:component-did-update (fn [_]
                              (new js/Promise
                                   (fn [resolve]
                                     (waitForIframeContentAndObserve
                                      {:resolve-fn resolve
                                       :iframe-id id
                                       :observer iframeContentHeightObserver
                                       :path src}))))
      :reagent-render
      (fn [{:keys [id src sandbox policy]
            :or {sandbox "allow-same-origin"
                 policy "no-referrer"}}]
        [:iframe {:class ["h-full w-full overflow-hidden"]
                  :sandbox sandbox
                  :referrerPolicy policy
                  :ref (fn [_]
                         (new js/Promise
                              (fn [resolve]
                                (waitForIframeContentAndObserve
                                 {:resolve-fn resolve
                                  :iframe-id id
                                  :observer iframeContentHeightObserver}))))
                  :height "100%"
                  :width "100%"
                  :frameBorder "0"
                  :scrolling "no"
                  :id id
                  :src src}])})))
