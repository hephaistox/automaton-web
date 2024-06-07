(ns automaton-web.components.player
  "Player component that ties controls with function execution, similar to the control of a video, it contains the play, stop, pause, previous and next buttons.
   Implementations of that actions are the responsibility of the user of that component."
  (:require
   [automaton-web.components.button :as web-button]))

(defn player
  [{:keys
    [play-fn pause-fn pause? next-fn prev-fn fast-forward-fn fast-backward-fn]}]
  [:div
   {:class
    ["flex flex-col rounded-lg bg-white shadow-xl shadow-black/5 ring-1 ring-slate-700/10"]}
   [:div {:class ["flex items-center justify-center space-x-4 px-6 py-4"]}
    (when fast-backward-fn
      [web-button/icon-button {:icon-path :svg/fast-backward
                               :icon-size 1.5
                               :on-click fast-backward-fn}])
    (when prev-fn
      [web-button/icon-button {:icon-path :svg/backward-step
                               :icon-size 1.5
                               :on-click prev-fn}])
    (if pause?
      [web-button/icon-button {:icon-path :svg/circle-pause
                               :icon-size 2.5
                               :on-click pause-fn}]
      [web-button/icon-button {:icon-path :svg/circle-play
                               :icon-size 2.5
                               :on-click play-fn}])
    (when next-fn
      [web-button/icon-button {:icon-path :svg/forward-step
                               :icon-size 1.5
                               :on-click next-fn}])
    (when fast-forward-fn
      [web-button/icon-button {:icon-path :svg/fast-forward
                               :icon-size 1.5
                               :on-click fast-forward-fn}])]])
