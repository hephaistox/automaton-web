(ns automaton-web.components.table
  "For table components"
  (:require
   [automaton-core.utils.uuid-gen :as uuid-gen]))

(defn- vector->hiccup
  [v]
  [:div {:class ["p-2 flex flex-col"]}
   "[ "
   (for [i v] ^{:key (uuid-gen/unguessable)} [:span (str i)])
   " ]"])

#_{:clj-kondo/ignore [:clojure-lsp/unused-private-var]}
(defn map->invisible-table
  "Transform clojure map to hiccup"
  [m i]
  [:ul
   (map (fn [[k v]] [:li {:class [(str "ml-" i)]}
                     [:span {:class ["font-bold"]}
                      k
                      ": "]
                     [:span
                      (cond
                        (map? v) (map->invisible-table v (+ i 1))
                        (vector? v) (vector->hiccup v)
                        :else (str v))]])
        m)])

(defn map->table
  "Turns clojure map into table"
  [map-data]
  [:div
   {:class
    ["max-w-full w-full bg-transparent border-spacing-0 border-solid border-gray-500 border-2"]}
   [:div
    (for [[k v] map-data]
      ^{:key (str "k: " k)}
      [:div
       [:div
        (merge
         {:class
          ["border-spacing-0 border-solid border-gray-500 border-2 p-0 flex"]}
         (when (map? v) {:colSpan "2"}))
        [:div {:class ["p-2 font-bold float-left"]}
         k]
        (cond
          (map? v) (map->table v)
          (vector? v) [:div {:class ["p-2 float-left bg-stone-100"]}
                       (vector->hiccup v)]
          :else [:div {:class ["p-2 float-left"]}
                 [:div {:class ["bg-stone-100"]}
                  (str v)]])]])]])

(defn table
  "Just plain regular table.
   Params:
   * `headers` - vector of table headers
   * `rows` - two-dimensional vector, where each vector is a separate row and inside those vectors are cells."
  [{:keys [headers rows]}]
  [:div {:class ["mt-8 flow-root"]}
   [:div {:class ["-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8"]}
    [:div {:class ["inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8"]}
     [:div
      {:class
       ["overflow-hidden shadow ring-1 ring-black ring-opacity-5 sm:rounded-lg"]}
      [:table {:class ["min-w-full divide-y divide-gray-300"]}
       [:thead {:class ["bg-gray-50"]}
        [:tr
         (for [header headers]
           ^{:key (uuid-gen/unguessable)}
           [:th
            {:scope "col"
             :class
             ["py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-6"]}
            header])]]
       [:tbody {:class ["divide-y divide-gray-200 bg-white"]}
        (for [row rows]
          [:tr
           (for [cell row]
             ^{:key (uuid-gen/unguessable)}
             [:td
              {:class
               ["whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-6"]}
              cell])])]]]]]])
