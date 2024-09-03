(ns automaton-web.i18n.be.translator.tempura
  "Implements tempura `WebTranslator` - translation for web backend"
  (:require
   [automaton-core.i18n.translator         :as core-translator]
   [automaton-core.i18n.translator.tempura :as tempura-translator]
   [automaton-web.i18n.be.translator       :as be-translator]
   [automaton-web.i18n.dict.resources      :as web-dict-resources]
   [automaton-web.i18n.dict.text           :as web-dict-text]
   [taoensso.tempura                       :as tempura]))

(defrecord TempuraWebTranslator [translator]
  be-translator/BeTranslator
    (wrap-translator [this]
      (fn [handler] (tempura/wrap-ring-request (be-translator/wrap-ring-request this handler) {})))
    (translate-based-on-request [_
                                 {:keys [locales]
                                  :as _http-request}
                                 tr-id
                                 resources]
      (core-translator/translate translator locales tr-id resources))
    (language-choice-strategy [this http-request]
      (be-translator/lang-str-choice-strategy-def this http-request))
    (wrap-ring-request [web-translator handler]
      (fn [{:keys [tempura/accept-langs_]
            :as http-request}]
        (let [locales-str [(be-translator/language-choice-strategy web-translator http-request)]
              updated-request (-> http-request
                                  (assoc :accept-langs accept-langs_ :locales locales-str)
                                  (dissoc :tempura/accept-langs_ :tempura/tr))]
          (-> updated-request
              (assoc :tr
                     (fn
                       ([tr-id resources]
                        (be-translator/translate-based-on-request web-translator
                                                                  updated-request
                                                                  tr-id
                                                                  resources))
                       ([tr-id]
                        (be-translator/translate-based-on-request web-translator
                                                                  updated-request
                                                                  tr-id
                                                                  nil))))
              handler
              (assoc-in [:headers "locales"] locales-str)))))
  core-translator/Translator
    (default-languages [_] (core-translator/default-languages translator))
    (translate [_ lang-ids tr-id resources]
      (core-translator/translate translator lang-ids tr-id resources)))

(defn make-tempura-be-translator
  "Create a TempuraWebTranslator instance
  Params:
  * `dicts` is the dictionary of collections to be used, `tempura` `automaton-core`, `automaton-web` dictionaries are already added, with a lower priority
  * `main-langs` is the ordered sequence of preffered language"
  [main-langs & dicts]
  (-> (apply tempura-translator/make-translator
             main-langs
             automaton-web.i18n.dict.text/dict
             automaton-web.i18n.dict.resources/dict
             dicts)
      (->TempuraWebTranslator)))
