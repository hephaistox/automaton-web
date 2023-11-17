(ns automaton-web.components.todos
  (:require [automaton-web.react-proxy :as bwc]))

(defn todo-form
  [_todos on-submit-todo]
  (let [new-item (bwc/ratom "")
        new-item-completed (bwc/ratom false)]
    (fn [] [:form
            {:on-submit (fn [e]
                          (.preventDefault e)
                          (on-submit-todo {:desc @new-item
                                           :completed @new-item-completed})
                          (reset! new-item "")
                          (reset! new-item-completed false))}
            [:input
             {:type "checkbox"
              :checked @new-item-completed
              :on-change #(reset! new-item-completed (-> %
                                                         .-target
                                                         .-checked))}]
            [:input
             {:type "text"
              :value @new-item
              :placeholder "Add a new item"
              :on-change (fn [e] (reset! new-item (.-value (.-target e))))}]])))

(defn todo-item
  [todo]
  [:li
   {:class [(if (:completed todo) "text-green-400" "text-red-400")]
    :key (str "li-todo: " todo)} (:desc todo)])

(defn todo-list
  [{:keys [todos item]
    :or {item todo-item}}]
  [:ul (for [todo todos] ^{:key (str "todo-item: " todo)} (item todo))])
