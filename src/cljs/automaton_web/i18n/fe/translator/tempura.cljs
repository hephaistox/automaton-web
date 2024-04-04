(ns automaton-web.i18n.fe.translator.tempura
  "Tempura implementation for frontend translation
  Implementing `fe-translator/FeTranslator`"
  (:require
   [automaton-core.i18n.translator.tempura :as tempura-translator]
   [automaton-core.log                     :as core-log]
   [automaton-web.adapters.fe.cookies      :as fe-cookies]
   [automaton-web.adapters.fe.url          :as fe-url]
   [automaton-web.events-proxy             :as web-events-proxy]
   [automaton-web.events.subs              :as web-subs]
   [automaton-web.i18n.dict.resources]
   [automaton-web.i18n.dict.text]
   [automaton-web.i18n.fe.translator       :as fe-translator]
   [taoensso.tempura                       :as tempura]))

(defrecord FeTempuraTranslator [opts main-langs ui-str-to-id]
  fe-translator/FeTranslator
    (default-languages [_] main-langs)
    (translate [_ tr-id resources]
      (let [lang (some-> (web-events-proxy/subscribe [::web-subs/lang])
                         deref)
            translated-text
            (tempura/tr opts
                        (vec (concat (when (keyword? lang) [lang]) main-langs))
                        [tr-id]
                        resources)]
        (core-log/trace "Translate key `"
                        tr-id
                        "`,with locales `"
                        (vec (concat (when (keyword? lang) [lang]) main-langs))
                        "`, -> `"
                        translated-text
                        "`")
        translated-text))
    (init-lang [this]
      (fe-translator/-init-lang this
                                (-> (fe-url/current-url)
                                    fe-url/lang-in-url-par
                                    ui-str-to-id)
                                (delay (-> "lang"
                                           fe-cookies/get-cookie-val
                                           ui-str-to-id))
                                main-langs))
    (lang [this] (fe-translator/-lang this)))

(defn make-fe-tempura-translator
  "Create a frontend tempura translator
  The language will be based on the `::web-subs/lang`
    Params:
  * `dicts` ordered list of dictionaries, the last is lower priority. The following dictionaries are appended, with lower priorities than yours so you can override them:
      * default text for `automaton-web` components
      * missing keys of tempura
  * `ui-str-to-id`
  * `main-langs` languages defaulted by tempura if not found "
  [main-langs ui-str-to-id & dicts]
  ;;Adding web-dict here allows to use that keyword in cust apps
  (->FeTempuraTranslator (apply tempura-translator/create-opts
                                automaton-web.i18n.dict.text/dict
                                automaton-web.i18n.dict.resources/dict
                                dicts)
                         main-langs
                         ui-str-to-id))
