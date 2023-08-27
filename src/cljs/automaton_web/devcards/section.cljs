(ns automaton-web.devcards.section
  (:require [devcards.core :as dc :refer [defcard]]
            [automaton-web.components.section :as sut]
            [automaton-web.devcards.utils :as bdu]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard section-description
  (bdu/wrap-component [:div
                       [:h3 "description light"]
                       [sut/section-description {:title "Title"
                                                 :description
                                                 "Hodor. Hodor hodor, hodor. Hodor hodor hodor hodor hodor. Hodor. Hodor! Hodor hodor, hodor; hodor hodor hodor. Hodor. Hodor hodor"}]
                       [:h3 "description dark"]
                       [:div
                        {:class ["bg-gray-900"]}
                        [sut/section-description {:title "Dark times are comming"
                                                  :dark? true
                                                  :description "Does everybody know that pig named Lorem Ipsum? An ‘extremely credible source’ has called my office and told me that Barack Obama’s placeholder text is a fraud."}]]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard section-text-button
  (bdu/wrap-component
   [:div
    [:h3 "Short text"]
    [sut/section-text-button
     {:text "This is a text of this section"
      :btn-text "Click me!"}]
    [:h3 "Dark mode"]
    [:div
     {:class ["bg-gray-900"]}
     [sut/section-text-button
      {:text "This is a text of this section"
       :dark? true
       :btn-text "Click me!"}]]
    [:h3 "Long text"]
    [sut/section-text-button
     {:text "This is a text of this section and when it's reaaaaaly long the button will be centered to fit this section text nicely."
      :btn-text "Click me!"}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard section-text-video
  (bdu/wrap-component
   [:div
    [:h3 "Short text"]
    [sut/section-text-video
     {:text "This is a text of this section"
      :title "PUMP IT UP!"
      :btn-props {:text "Click me!"}
      :video-src "https://www.youtube.com/embed/9EcjWd-O4jI"}]
    [:h3 "Long text"]
    [sut/section-text-video
     {:title "And this is quite long for a title"
      :subtitle "I'm a subtitle"
      :text "This is a text of this section and when it's reaaaaaly long the button will be centered to fit this section text nicely."
      :btn-props {:text "Click me!"}
      :video-src "https://www.youtube.com/embed/9EcjWd-O4jI"}]
    [:h3 "Dark mode"]
    [sut/section-text-video
     {:text "This is a text of this section"
      :subtitle "Boombox"
      :title "PUMP IT UP!"
      :dark? true
      :btn-props {:text "Click me!"}
      :video-src "https://www.youtube.com/embed/9EcjWd-O4jI"}]]))
