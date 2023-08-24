(ns automaton-web.components.menu
  "Display a menu component"
  (:require
   [clojure.string]

   [automaton-web.components.menu-item :as menu-item]
   [automaton-web.components.icons :as bci]
   [automaton-core.utils.url :as url]))

(defn- horizontal-version
  "Shown as horizontal following buttons"
  [{:keys [items force-burger?]}]
  (vec (concat
        [:div
         {:class [(when-not force-burger?
                    "xl:block")
                  "flex flex-col xl:flex-row gap-1 hidden"]}]
        items)))

(defn- burger-version
  "Show the minified - burger version"
  [{:keys [items burger-position force-burger?]}]
  [:div
   {:class ["block h-20 p-4"
            (when-not force-burger?
              "xl:hidden")]}
   [:div {:class ["absolute"
                  (if (= :left burger-position)
                    "left-10"
                    "right-10")]}
    [:div
     {:class ["overflow-hidden hover:h-fit hover:w-fit w-5 relative h-8"]}
     [:div
      {:class ["flex flex-col absolute"
               (if (= :left burger-position)
                 "left-0"
                 "right-0")]}
      [:div
       {:class ["cursor-pointer"]}
       [bci/icon {:path-kw :svg/burger}]]]
     (vec
      (concat
       [:div
        {:class ["h-fit flex flex-col mt-10 max-w-[250] border shadow shadow-xl bg-white"]}]
       items))]]])

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn component
  "An horizontal menu adapting to small screen to become vertical
  The parameter is a map with the following keys:
  * `items` is a vector a menu-item, See the `automaton-web.components.menu-item` for more options
  * `path` if set the menu is auto marked as selecting the matching menu (the one which href is contained in this parameter)
  * `force-burger?` if the menu should be minified as a burger, whatever the size of the screen is
  * `burger-position` where the minified version (burger) is shown on the remaining space, `:left` or `:right`
  "
  [{:keys [items path]
    :as params}]
  (let [auto-select? (not (nil? path))
        items (vec
               (for [{:keys [href]
                      :as item} items]
                 (let [menu-match? (when (and (string? path)
                                              (string? href))
                                     (let [uri (url/get-uri path)]
                                       (= uri
                                          href)))]
                   [menu-item/component (merge (assoc item
                                                      :path path
                                                      :auto-select? auto-select?)
                                               (when auto-select?
                                                 {:selected menu-match?}))])))
        params (assoc params :items items)]
    [:div
     {:class ["w-full z-10"]}
     [horizontal-version params]
     [burger-version params]]))
