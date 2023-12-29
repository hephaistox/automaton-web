(ns automaton-web.components.menu-item
  "Menu item component.
  Based on `https://tailgrids.com/components/navbars`

  The parameter map contains:
  * title: what to display to user
  * href: the adress to go to
  * on-click: executed when clicked, overseed href
  * selected: display differently if true
  ")

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn component
  "Build a menu entry"
  [{:keys [title href selected on-click]}]
  [:a
   (merge {:key (str "menu-" title)
           :class ["select-none" (if selected "cursor-default" "cursor-pointer")]}
          (when-not selected (if on-click {:on-click on-click} {:href href})))
   [:div
    {:class
     ["py-4 px-6 lg:px-8 xl:px-10 mx-0 lg:mx-2 xl:mx-4 border-none rounded-md inline-flex items-center justify-center text-center text-base"
      (if selected "underline underline-offset-8" "hover:text-black")]} title]])
