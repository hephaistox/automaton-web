(ns automaton-web.components.header "Simple header")

(defn component
  "Display a header component, as of https://tailwindui.com/components/marketing/elements/headers"
  [content]
  [:header {:class ["mx-auto max-w-7xl"]}
   [:div {:class ["flex relative items-center justify-between" "-mx-4"]}
    content]])

(defn- base-header
  [{:keys [size sticky? border?]} content]
  [:header {:class [(if sticky? "sticky" "absolute")
                    (when border?
                      "border border-solid border-b-theme-dark bg-theme-light")
                    "inset-x-0 top-0 z-50"
                    "py-2"
                    (if (= :full size) "w-full" "w-full lg:w-1/2")]}
   content])

(defn transparent-header
  [{:keys [size sticky? border? logo right-section]}]
  [base-header {:size size
                :sticky? sticky?
                :border? border?}
   [:nav {:class
          ["flex items-center content-between justify-between px-6 lg:px-8"]}
    logo
    [:div right-section]]])


(defn header
  [{:keys [size logo sticky? border? right-section]} & menu-items]
  [base-header {:size size
                :sticky? sticky?
                :border? border?}
   [:nav {:class
          ["flex items-center content-between justify-between px-6 lg:px-8"]}
    logo
    [:div {:class ["hidden lg:flex lg:gap-x-12"]}
     (for [{:keys [title href]} menu-items]
       [:a {:href href
            :class ["text-sm font-semibold leading-6 text-gray-900"]}
        title])]
    [:div right-section]]])
