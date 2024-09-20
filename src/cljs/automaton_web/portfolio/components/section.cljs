(ns automaton-web.portfolio.components.section
  (:require
   [automaton-web.components.section :as sut]
   [automaton-web.portfolio.proxy    :as web-proxy]
   [portfolio.reagent-18             :as           portfolio
                                     :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Section"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 section-description
 :params
 {:title "Title"
  :description
  "Hodor. Hodor hodor, hodor. Hodor hodor hodor hodor hodor. Hodor. Hodor! Hodor hodor, hodor; hodor hodor hodor. Hodor. Hodor hodor"}
 [params]
 (web-proxy/wrap-component [sut/section-description params]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 section-description-dark
 :params
 {:title "Dark times are comming"
  :dark? true
  :description
  "Does everybody know that pig named Lorem Ipsum? An ‘extremely credible source’ has called my office and told me that Barack Obama’s placeholder text is a fraud."}
 [params]
 (web-proxy/wrap-component [sut/section-description params]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene section-text-button
          :params
          {:text "This is a text of this section"
           :btn-text "Click me!"}
          [params]
          (web-proxy/wrap-component [sut/section-text-button params]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene section-text-button-dark
          :params
          {:text "This is a text of this section"
           :dark? true
           :btn-text "Click me!"}
          [params]
          (web-proxy/wrap-component [sut/section-text-button params]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 section-text-button-long-text
 :params
 {:text
  "This is a text of this section and when it's reaaaaaly long the button will be centered to fit this section text nicely."
  :btn-text "Click me!"}
 [params]
 (web-proxy/wrap-component [sut/section-text-button params]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene section-text-video
          :params
          {:text "This is a text of this section"
           :title "PUMP IT UP!"
           :btn-props {:text "Click me!"}
           :video-src "https://www.youtube.com/embed/9EcjWd-O4jI"}
          [params]
          (web-proxy/wrap-component [sut/section-text-video params]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene section-text-video-dark
          :params
          {:text "This is a text of this section"
           :subtitle "Boombox"
           :title "PUMP IT UP!"
           :dark? true
           :btn-props {:text "Click me!"}
           :video-src "https://www.youtube.com/embed/9EcjWd-O4jI"}
          [params]
          (web-proxy/wrap-component [sut/section-text-video params]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 section-text-video-long-text
 :params
 {:title "And this is quite long for a title"
  :subtitle "I'm a subtitle"
  :text
  "This is a text of this section and when it's reaaaaaly long the button will be centered to fit this section text nicely."
  :btn-props {:text "Click me!"}
  :video-src "https://www.youtube.com/embed/9EcjWd-O4jI"}
 [params]
 (web-proxy/wrap-component [sut/section-text-video params]))
