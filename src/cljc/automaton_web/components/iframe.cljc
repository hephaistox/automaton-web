(ns automaton-web.components.iframe)

(defn iframe
  [{:keys [id src sandbox policy]}]
  [:iframe {:class ["h-full w-full overflow-hidden"]
            :sandbox (if sandbox sandbox true)
            :referrerPolicy (if policy policy "no-referrer")
            :height "100%"
            :width "100%"
            :frameborder "0"
            :scrolling "no"
            :id id
            :src src}])
