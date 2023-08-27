(ns automaton-web.components.header
  "Simple header")

(defn component
  "Display a header component, as of https://tailwindui.com/components/marketing/elements/headers"
  [content]
  [:header
   {:class ["mx-auto max-w-7xl"]}
   [:div
    {:class ["flex relative items-center justify-between"
             "-mx-4"]}
    content]])

(defn simple-header [{:keys [size logo right-section]}]
  [:header {:class ["absolute inset-x-0 top-0 z-50"
                    (if (= :full size)
                      "w-full"
                      "w-full lg:w-1/2")]}
   [:nav {:class ["flex items-center content-between justify-between p-6 lg:px-8"]
          :aria-label "Global"}
    logo
    [:div
     right-section]]])
