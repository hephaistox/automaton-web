(ns automaton-web.components.alert
  "An alert component to present a message and its title,
  based on https://tailwindui.com/components/application-ui/feedback/alerts"
  (:require [automaton-web.components.icons :as bci]))

(def alert-types [:warning :assert :error])

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn component
  "Component to show an alert
  * title: what to display as a first highlighted line
  * message: what to display as a second line
  * type: one of the value :warning, :assert or :error (default to :error)
  "
  [{:keys [title message type]}]
  [:div
   {:class ["flex"
            (case type
              :warning "border-l-4 border-yellow-400 bg-yellow-50 p-4 m-4"
              :assert "border-l-4 border-green-400 bg-green-50 p-4 m-4"
              "border-l-4 border-red-400 bg-red-50 p-4 m-4")]}
   [:div {:class ["flex-shrink-0"]}
    (bci/icon {:path-kw (case type
                          :warning :svg/exclamation
                          :assert :svg/checked
                          :svg/x-circle)
               :class [(case type
                         :warning "icon-yellow"
                         :assert "icon-green"
                         "icon-red")]})]
   [:div {:class ["ml-3"]}
    (when title
      [:h3
       {:class [(case type
                  :warning "text-base font-medium text-yellow-800"
                  :assert "text-base font-medium text-green-800"
                  "text-base font-medium text-red-800")]} title])
    (when message
      [:p
       {:class ["text-sm"
                (case type
                  :warning "text-yellow-700"
                  :assert "text-green-700"
                  "text-red-700") (when title "mt-2")]} message])]
   [:div {:class ["ml-auto pl-3"]}
    [:div {:class ["cursor-pointer -mx-1.5 -my-1.5"]}
     [:div
      {:class ["inline-flex p-2 rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2"
               (case type
                 :warning "text-yellow-500 hover:bg-yellow-100  focus:ring-yellow-600 focus:ring-offset-yellow-50"
                 :assert "text-green-500 hover:bg-green-100  focus:ring-green-600 focus:ring-offset-green-50"
                 "text-red-500 hover:bg-red-100 focus:ring-red-600 focus:ring-offset-red-50")]} (bci/icon {:path-kw :svg/x-mark})]]]])
