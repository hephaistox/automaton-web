(ns automaton-web.components.simple-select)

(defn simple-select
  "Simple html select with additional options to customize it.
   options should be a collection of [:option] html tags.

   Passing :value in props will require you to also implement :onChange,
   If you want start with default option chosen
   Add on the default option tag attribute selected=\"selected\"

   :class value should be a vector -> it's vector because it's easier to apply some logic that way"
  [{:keys [id name value class onChange] :as _props} options]
  [:select (merge {:id id
                   :name name
                   :class (vec
                           (concat
                            ["mt-2" "block" "w-full" "rounded-md" "border-0" "py-1.5" "pl-3" "pr-10" "text-gray-900" "ring-1" "ring-inset" "ring-gray-300" "focus:ring-2" "focus:ring-indigo-600" "sm:text-sm" "sm:leading-6"]
                            class))
                   :onChange (fn [e]
                               (onChange e))}

                  (when value {:value value}))
   (for [opt options]
     ^{:key (str "option: " opt)}
     opt)])
