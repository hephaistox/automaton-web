(ns automaton-web.portfolio.pages.error
  (:require
   [automaton-web.components.errors :as sut]
   [portfolio.reagent-18            :as           portfolio
                                    :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :pages
                   :title "Error pages"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene not-found
          [:div {:class ["h-full w-full"]}
           [sut/not-found {:back-link "https://www.wikipedia.com"
                           :title "Page not found"
                           :description "Sorry we couldn't find the page you were looking for."
                           :back-home-text "Back home"}]])

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 internal-error
 [sut/internal-error
  {:back-link "https://www.wikipedia.com"
   :title "Well, this is unexpected..."
   :description
   "An error has occured and we are working hard to fix the problem! We'll be running shortly"
   :back-home-text "Back home"}])
