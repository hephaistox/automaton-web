(ns automaton-web.i18n.be.translator
  "Protocol for a backend translator"
  (:require [automaton-core.i18n.translator :as core-translator]
            [automaton-core.log :as core-log]
            [automaton-web.adapters.be.http-request :as http-request]
            [clojure.string :as str]))

(defprotocol BeTranslator
  (wrap-translator [this]
   "Wrap an handler in a middleware. Add `:tr` in the request which is a translation method (signature (tr [tr-id resources]))")
  (default-languages [this]
   "Return the default language of the translation (if language choice strategy find no language, this one will be chosen)")
  (translate-based-on-request [this http-request tr-id resources]
   "Translate the `tr-id` with resources. The language is chosen through the data in the request")
  (wrap-ring-request [this handler]
   "Wrap a ring request and add to the request`:tr` and `:accept-language`\n* `tr` is a function ready for translation, usage: `(tr :foo-text [\"oliver\" \"twist\"])`* `accept-language` is all the accepted languages setup by the the user in its browser/os\n Params:\n * `web-translator` the translator\n * `handler` handler to wrap\n * `language-choice-strategy-fn`")
  (language-choice-strategy [this http-request]
   "Parse the http request to decide what is the default language to use for that user"))

(defn language-choice-strategy*
  "Apply the language selection strategy
  - If a parameter language is set in the path, just use it,
  - Else If a language is set in the cookie, use it
  - Use the tld
  - If none is set, use the main-lang as a default language
  All variables with trailing _ are delays, so they'll be evaluted only if the previous step has failed to find a value
  Params:
  * `par-lang` language imposed in the parameters
  * `cookies-lang_` language stored in the cookie
  * `accepted-languages_` accepted languages by the user browser
  * `tld-lang_` is the language in the tld
  * `main-lang_` main language"
  [par-lang cookies-lang_ accepted-languages_ tld-lang_ main-lang_]
  (or par-lang @cookies-lang_ @accepted-languages_ @tld-lang_ @main-lang_))

(defn lang-str-choice-strategy-def
  "Parse an http request to apply the strategy to decide which language we use
  This is a design choice to let the strategy decides how default-language is used, so it is not left to tempura to apply default
  Params:
  * `web-translator` the translator instance to know the default languages
  * `http-request` request to parse"
  [web-translator http-request]
  (let [par-lang (http-request/get-param http-request :lang)
        lang-str (if (or (not (string? par-lang)) (str/blank? par-lang))
                   (language-choice-strategy* par-lang
                                              (delay (http-request/cookies-language http-request))
                                              (delay (some-> (http-request/accepted-languages http-request)
                                                             (subs 0 2)))
                                              (delay (some-> http-request
                                                             http-request/tld-language))
                                              (delay (first (core-translator/default-languages web-translator))))
                   (do (core-log/trace "Language `" par-lang "` imposed by parameter") par-lang))]
    lang-str))
