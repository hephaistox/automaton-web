(ns automaton-web.i18n.tempura
  (:require [taoensso.tempura :as tempura]))

(def tempura-missing-text
  {:en {:missing "The text is missing! :( Please let us know at info@hephaistox.com"}
   :fr {:missing "Le texte est manquant! :( Veuillez nous en informer à l'adresse info@hephaistox.com"}})

(defn tempura-tr
  [dictionary lang & text]
  (tempura/tr {:dict dictionary} (vector lang) (vector (first text))
              (vec (rest text))))
