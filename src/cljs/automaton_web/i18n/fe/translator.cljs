(ns automaton-web.i18n.fe.translator
  "Translator for the frontend in web technology

  These translation functions are used in the `automaton-web` application, so that the automaton-web dictionary, translation, and  testing of it can be done autonomously.
  So for instance, default text in components (like mailchimp) will use those translation functions.
  But cust-apps will have their own implemntations"
  (:require
   [automaton-core.log :as core-log]
   [automaton-web.events-proxy :as web-events-proxy]
   [automaton-web.events.subs :as web-subs]))

(defprotocol FeTranslator
  "The frontend translator"
  (default-languages [this]
   "sequence of ids of the default languages")
  (translate [this tr-id resources]
   "translate the `:tr-id` with the resources as parameters (first resource is %1, second is %2, ...), trying to translate with first language lang-ids, then second, ...")
  (init-lang [this]
   "language to in init the frontend page with. be aware you cannot search for db to init, as it is not init yet")
  (lang [this]
   "get the currently selected language"))

(defn -init-lang
  "By default, the frontend strategy for language choice is to rely on the parameters in the url, defaulted to the language defined in `default-language` fn
  Params:
  * `this` is an instance of `FeTranslator`
  * `par-lang` the language encoded in the url
  * `cookies-lang_` language stored in the user cookies
  * `main-lang`"
  [this par-lang cookies-lang_ main-lang]
  (if-let [language (or par-lang @cookies-lang_ main-lang)]
    (do (core-log/debug "Init language " language) language)
    (let [default-language (-> (default-languages this)
                               first)]
      (core-log/warn "No language found in the url, default to "
                     default-language)
      default-language)))

(defn -lang
  "By default, the frontend get its language value from `::web-subs/lang`"
  [this]
  (try (some-> (web-events-proxy/subscribe [::web-subs/lang])
               deref)
       (catch :default _
         (core-log/warn
          "The language event has not been found, default language is used")
         (default-languages this))))
