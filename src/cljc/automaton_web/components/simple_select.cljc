(ns automaton-web.components.simple-select
  "Simple select component"
  (:require
   [automaton-core.utils.string-to-id :refer [string-to-id]]
   [automaton-web.reagent             :as automaton-web-reagent]))

(defn- update-select-options
  "Add options to select options components.
  Generate a key based on `select-id` and `opt-value`"
  [{:keys [opt-value key]
    :as opt}
   select-id]
  (assoc opt :key (or key (str select-id "-" (string-to-id opt-value)))))

(defn simple-select
  "Simple html select

  Params:
  * `props` properties to tweak the selector
      * `id` Optional (default to string-to-id of html-name) is the html id of the component
      * `html-name` name to represent the data stored if that data are POSTed in a form
      * `class`  css attributes to add to default presentation
      * `value` is a currently selected value
      * `on-change` method to call on change of the value, typically dispatch an event
      * `options` a list of option, as `options-arg`, easier to use if you already handle a collection of options
  * `options-arg` options should be a collection of [:option] html tags. This value is useful to directly pass options as a variadic arguments. It's superseeding `options` keyword."
  [{:keys [id html-name class on-change value options]
    :as _props}
   &
   options-arg]
  (let [options (for [select-option (or options-arg options)]
                  (-> select-option
                      automaton-web-reagent/reagent-option
                      (update-select-options id)
                      (automaton-web-reagent/update-reagent-options select-option)))]
    (fn [] [:select {:id id
                     :name html-name
                     :default-value value
                     :class (vec (concat ["block"
                                          "w-full"
                                          "rounded-md"
                                          "border-0"
                                          "py-1"
                                          "pl-3"
                                          "pr-10"
                                          "text-gray-900"
                                          "ring-1"
                                          "ring-inset"
                                          "ring-gray-300"
                                          "focus:ring-2"
                                          "focus:ring-indigo-600"
                                          "sm:text-sm"
                                          "sm:leading-6"]
                                         class))
                     :on-change on-change}
            options])))
