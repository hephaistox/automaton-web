(ns automaton-web.components.link
  (:require [automaton-web.adapters.fe.url :as fe-url]
            [automaton-web.events-proxy :as bwr]
            [automaton-web.events.subs :as web-subs]))

(defn conditional-link-opts
  "Update a html component that support link (typically [:a], [:nav], ...) with disabled and href so it is clickable only if it leads to a different page
  Params
  * `opts` existing option maps"
  [{:keys [href]
    :as opts}]
  (let [current-url? (fe-url/current-location? href)
        ;; This subscription is important to force react to reevaluate this
        ;; in case the url changed
        _ @(bwr/subscribe [::web-subs/route-match])]
    (cond-> opts
      current-url? (-> (dissoc :href)
                       (assoc :disabled? true))
      (not current-url?) (-> (assoc :href href)))))
