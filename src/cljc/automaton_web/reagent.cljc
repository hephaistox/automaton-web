(ns automaton-web.reagent "Proxy for reagent functions")

(defn add-opt
  "Add an option to an existing reagent object
  Manages both case where the option map is already existing or not
  Params:
  * `comp` reagent component to update
  * `k` key to add to the options of the component
  * `v` value to add
  * `kv` is an optional serie of further key value pair"
  [component k v & kv]
  (let [[comp-key & comp-rest] component
        maybe-opt (first comp-rest)]
    (if (map? maybe-opt)
      (apply vector comp-key (apply assoc maybe-opt k v kv) (rest comp-rest))
      (apply vector comp-key (apply assoc {} k v kv) comp-rest))))

(defn reagent-option
  "Return the option of an existing reagent object
  Manages both case where the option map is already existing or not
  Params:
  * `comp` reagent component to update "
  [component]
  (let [maybe-opt (second component)] (if (map? maybe-opt) maybe-opt {})))

(defn update-reagent-options
  "Update the reagent component to insert `options`
  Manage both cases where the option map already exist or not
  Params:
  * `options` reagent options to be inserted
  * `component` reagent component to update"
  [options component]
  (let [[comp-key & comp-rest] component
        maybe-opt (first comp-rest)
        updated-options (if (map? maybe-opt) (apply vector comp-key options (rest comp-rest)) (apply vector comp-key options comp-rest))]
    updated-options))
