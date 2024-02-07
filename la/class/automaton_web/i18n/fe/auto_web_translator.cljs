(ns automaton-web.i18n.fe.auto-web-translator
  "Translator for `automaton-web`, used to have default translation in the components, so `automaton-web` is autonomous
  The default implementation is a tempura one, with internal dictionaries.
  That implementation could be overriden"
  (:require
   [automaton-web.i18n.fe.translator :as fe-translator]
   [automaton-web.i18n.fe.translator.tempura :as fe-tempura-translator]
   [automaton-web.i18n.language :as web-language]))

(def fe-components-translator
  (fe-tempura-translator/make-fe-tempura-translator
   web-language/main-langs
   (partial web-language/ui-str-to-id web-language/automaton-web-languages)))

(defn tr
  "Translate the `tr-id` with `resources`
  Params:
  * `tr-id` id of the translation to translate. Point a keyword of `automaton-web` dictionary, but the value may be overriden by a cust-app
  * `resources` resources for the translation"
  [tr-id & resources]
  (fe-translator/translate fe-components-translator tr-id (vec resources)))

(defn get-lang
  "Return the currently selected language"
  []
  (fe-translator/lang fe-components-translator))
