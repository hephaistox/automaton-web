(ns automaton-web.devcards.alert
  (:require
   [devcards.core :as dc :refer [defcard]]

   [automaton-web.components.alert :as sut]
   [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard check-alert
  (bdu/wrap-component [:div
                       [:h1
                        {:class ["text-3xl"]}
                        "Show alerts in different situations"]

                       [:h2
                        {:class ["text-2xl"]}
                        "Normal size, type :error"]
                       [:h3 "Normal simple alert"]
                       [sut/component {:title "Title"
                                       :type :error
                                       :message "Message"}]
                       [:h3 "Normal simple warning, type :message"]
                       [sut/component {:title "Title"
                                       :type :warning
                                       :message "Message"}]
                       [:h3 "Assert a message, type :assert"]
                       [sut/component {:title "Title"
                                       :type :assert
                                       :message "Message"}]

                       [:h3 "Title only"]
                       [sut/component {:title "Title"}]

                       [:h3 "Message only"]
                       [sut/component {:message "Message"}]

                       [:h2
                        {:class ["text-2xl w-60 mt-10"]}
                        "Small screen"]

                       (for [type sut/alert-types]
                         [:div {:key (str "alert-list-" type)}
                          [:h3 (str "Type " type)]
                          [sut/component {:title "Title"
                                          :type type
                                          :message "Message"}]])]))
