(ns automaton-web.i18n.be.auto-web-translator
  "Translator for `automaton-web`, used to have default translation in the components, so `automaton-web` is autonomous
  The default implementation is a tempura one, with internal dictionaries.
  That implementation could be overriden"
  (:require
   [automaton-web.i18n.be.translator         :as be-translator]
   [automaton-web.i18n.be.translator.tempura :as be-tempura-translator]
   [automaton-web.i18n.language              :as web-language]))

(def be-components-translator
  (be-tempura-translator/make-tempura-be-translator web-language/main-langs))

(defn tr
  "Translate the `tr-id` with `resources`
  Params:
  * `http-request` http request containg translation data
  * `tr-id` id of the translation to translate. Point a keyword of `automaton-web` dictionary, but the value may be overriden by a cust-app
  * `resources` resources for the translation"
  [http-request tr-id & resources]
  (be-translator/translate-based-on-request be-components-translator
                                            http-request
                                            tr-id
                                            (vec resources)))
