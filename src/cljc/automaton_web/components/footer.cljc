(ns automaton-web.components.footer
  (:require [automaton-web.web-elt.id :as web-elt-id]
            [automaton-web.components.version :as bcv]))

(defn- footer-link
  [{:keys [title items dark? disabled?]
    :as _footer-category} class]
  [:div {:class class} [:h3 {:class ["text-base font-medium" (if dark? "text-theme-light" "text-theme-dark")]} title]
   (vec (concat [:ul
                 {:class ["mt-4 space-y-4"]
                  :role "list"}]
                (for [{:keys [text href]} items]
                  [:li
                   {:class
                    ["text-base"
                     (if dark? "text-theme-light-secondary hover:text-theme-light" "text-theme-dark-secondary hover:text-theme-dark")]}
                   [:a
                    {:href href
                     :class ["text-sm leading-6" (if disabled? "cursor-not-allowed" "cursor-pointer")
                             (if dark?
                               (if disabled? "text-theme-light" "text-theme-light-secondary hover:text-theme-light")
                               (if disabled? "text-theme-dark" "text-theme-dark-secondary hover:text-theme-dark"))]} text]])))])

(defn- simple-footer-link
  [{:keys [title href dark? disabled? on-click]}]
  [:div {:class ["pb-6"]}
   [:a
    (merge (if on-click {:on-click on-click} {:href href})
           {:class ["text-sm leading-6" (if disabled? "pointer-events-none" "cursor-pointer")
                    (if dark?
                      (if disabled? "text-theme-light" "text-theme-light-secondary hover:text-theme-light")
                      (if disabled? "text-theme-dark" "text-theme-dark-secondary hover:text-theme-dark"))]}) title]])

(defn- social-networks-comp
  [social-networks dark?]
  (for [[icon-key icon-comp] social-networks] ^{:key (str "icon-" icon-key)} (assoc-in icon-comp [1 :dark?] dark?)))

(defn footer
  "Display a footer,
  Is managing the different size of screens
  * `company-name` used as a alt of the image
  * `badge` is an url of the badge to display on the left
  * `quotation` text to show under the badge
  * `title` is the far bottom of the footer
  * `footer-lists` vector of maps with
     * `title` the title of a section,
     * `items` a vector of links, each is a map of
        * `href` for target adress,
        * and `text` for what to display
  * `social-networks` is a map
      * where key is an primitive type id
      * and value is an component that will be displayed as an icon
  * `version` is the string to show the version
  "
  [{:keys [social-networks footer-lists title quotation badge company-name release dark?]}]
  [:footer
   {:class [(if dark? "bg-theme-dark" "bg-theme-light")]
    :aria-labelledby "footer-heading"}
   [:div {:class ["mx-auto max-w-7xl py-12 px-4 sm:px-6 lg:py-16 lg:px-8"]}
    [:div {:class ["xl:gap-8 xl:grid xl:grid-cols-3"]}
     [:div {:class ["xl:col-span-1 space-y-8"]}
      [:img
       {:src badge
        :alt company-name
        :class ["h-10"]}] [:p {:class ["text-base" (if dark? "text-theme-light-secondary" "text-theme-dark-secondary")]} quotation]
      (vec (concat [:div {:class ["flex gap-8"]}] (social-networks-comp social-networks dark?)))]
     (let [[footer1 footer2 footer3 footer4] footer-lists]
       [:div {:class ["grid grid-cols-2 gap-8 xl:col-span-2 mt-12 xl:mt-0"]}
        [:div {:class ["md:grid md:grid-cols-2 md:gap-8"]} [footer-link (merge footer1 {:dark? dark?})]
         [footer-link (merge footer2 {:dark? dark?}) ["mt-12 md:mt-0"]]]
        [:div {:class ["md:grid md:grid-cols-2 md:gap-8"]} [footer-link (merge footer3 {:dark? dark?})]
         [footer-link (merge footer4 {:dark? dark?}) ["mt-12 md:mt-0"]]]])]
    [:div {:class ["mt-12 border-t border-gray-200 pt-8 relative flex items-center"]}
     (when release
       [bcv/component
        {:version release
         :dark? dark?}])
     [:p {:class ["text-base xl:text-center w-full" (if dark? "text-theme-light-secondary" "text-theme-dark-secondary")]} title]]]])

(defn simple-footer
  "Minimal version of footer component.
   All the keys are optional.
   * `footer-lists` are used to display links
        it is a vector of maps, each map can contain:
        * title - string of the text in a link
        * on-click / href - for making the link clickable
        * disabled? - true to display as disabled
   * `release` - a string with version to display
   * `title` - text next to release
   * `dark?` - color mode
   * `social-networks` is a map
      * where key is an primitive type id
      * and value is an component that will be displayed as an icon"
  [{:keys [footer-lists release social-networks title dark?]}]
  [:footer {:class [(if dark? "bg-theme-dark" "bg-theme-light")]}
   [:div {:class ["mx-auto max-w-7xl overflow-hidden px-6 py-10 sm:py-12 lg:px-8"]}
    [:nav
     {:class ["-mb-6 columns-2 sm:flex sm:justify-center sm:space-x-12"]
      :aria-label "Footer"}
     (for [footer footer-lists]
       ^{:key (str "simple-footer-link-" (web-elt-id/string-to-id (:title footer)))} [simple-footer-link (merge {:dark? dark?} footer)])]
    (into [:div {:class ["mt-10 flex justify-center space-x-10 relative"]}
           (when release
             [bcv/component
              {:version release
               :dark? dark?}])]
          (social-networks-comp social-networks dark?))
    [:p {:class ["mt-10 text-center text-xs leading-5" (if dark? "text-theme-light-secondary" "text-theme-dark-secondary")]} title]]])
