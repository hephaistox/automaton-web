(ns automaton-web.components.iframe)

(defn iframe
  [{:keys [id src sandbox policy]
    :or {policy "no-referrer"}}]
  [:iframe {:class ["h-full w-full overflow-hidden"]
            :sandbox sandbox
            :referrerPolicy policy
            :height "100%"
            :width "100%"
            :frameBorder "0"
            :scrolling "no"
            :id id
            :src src}])
