(ns automaton-web.components.select
  (:require
   [automaton-web.components.simple-select :as ss]
   [automaton-web.react-proxy :as bwc]))

(defn select
  "Simple select, that requires props map (could be empty) and options html elements passed to native select.
   :onChange is a function accepting one argument
   :class is a vector"
  [{:keys [onChange id name class initial-value]
    :or {onChange #()
         id "select-id"
         name "select-name"
         class nil
         initial-value ""}}
   & options]
  (let [select-v (bwc/ratom initial-value)
        onChange (fn [e]
                   (onChange e)
                   (reset! select-v e.target.value))]
    (fn []
      [ss/simple-select {:id id
                         :name name
                         :value @select-v
                         :onChange onChange
                         :class class}
       options])))
