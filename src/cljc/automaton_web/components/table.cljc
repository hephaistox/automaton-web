(ns automaton-web.components.table "For table components")

(defn map->table
  "Turns clojure map into table"
  [map-data]
  [:table
   {:class
    ["max-w-full w-full bg-transparent border-spacing-0 border-solid border-gray-500 border-2"]}
   [:tbody
    (for [[k v] map-data]
      [:tr
       [:td
        (merge {:class
                ["border-spacing-0 border-solid border-gray-500 border-2 p-0"]}
               (when (map? v) {:colspan "2"}))
        [:div {:class ["p-2 font-bold float-left"]}
         k]
        (if (map? v)
          (map->table v)
          [:td
           [:div {:class ["p-2 bg-stone-100"]}
            v]])]])]])

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
           [:th
            {:scope "col"
             :class
             ["py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-6"]}
            header])]]
       [:tbody {:class ["divide-y divide-gray-200 bg-white"]}
        (for [row rows]
          [:tr
           (for [cell row]
             [:td
              {:class
               ["whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-6"]}
              cell])])]]]]]])
