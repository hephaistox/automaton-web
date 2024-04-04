(ns automaton-web.i18n.language
  "Defines the possible languages for the apps based on `automaton-web`
  Is a specialisation of `automaton-core.i18n.language`. All languages set here will be checked by the test suite to be in `automaton-core.i18n.language` also

  Note all of them won't be implementened for all cust-apps
  See `automaton-web.i18n.language` as all possible values for a web app"
  (:require
   [automaton-core.i18n.language :as core-lang]
   [clojure.string               :as str]))

(def main-langs
  "Default language if all language strategy fail to select a language
  As it is not supposed to happen, this is set to `:en`, as the rest of the default are french"
  [:en])

(def ^:private web-languages-map
  "Is a map defining all supported languages in a web app and add specific data
  First level of keyword is the id of the language, used internally, should be present in `automaton-core.i18n.language`
  Then, the map has the following data:
  * `:tld` name of the language in the tld, e.g. hephaistox.com will be directed to `:en`"
  {:fr {:tld "fr"}
   :en {:tld "com"}})

(defprotocol WebLanguages
  (tlds [this]
   "List possible tld\n")
  (cors-domain-routes [this main-domain]
   "Creates all route filter to route the uri for CORS\n  Params:\n  * `selected-languages` list of languages selected in the cust-app, selected-languages could be smaller for some cust-app\n  * `main-domain` is the domain where the app is deployed")
  (create-ui-languages [this]
   "Create data map with all possible languages, especially useful to display them in a combobox")
  (ui-str-to-id [this lang-ui-text]
   "Transform a ui string of a language to its id, comparison is based on string is not not case sensitive\n  Params:\n  * `selected-languages` list of languages selected in the cust-app, selected-languages could be smaller for some cust-app\n  * `lang-ui-text` the text to search"))

(defrecord AutomatonWebLanguages [core-language]
  core-lang/Languages
    (language [_ id] (core-lang/language core-language id))
    (languages [this] (core-lang/languages this))
    (languages-ids [_] (core-lang/languages-ids core-language))
    (dict-languages-ids [_] (core-lang/dict-languages-ids core-language))
  WebLanguages
    (tlds [_]
      (->> core-language
           core-lang/languages
           vals
           (mapv :tld)))
    (cors-domain-routes [this main-domain]
      (let [tlds (tlds this)]
        (->> (for [tld tlds] (str/join "." [main-domain tld]))
             (mapv (fn [domain] (re-pattern (str ".*" domain "$")))))))
    (create-ui-languages [_]
      (->> core-language
           core-lang/languages
           (map (fn [[lang-id lang]]
                  (let [ui-text (:ui-text lang)]
                    {:name (name lang-id)
                     :id lang-id
                     :value ui-text})))))
    (ui-str-to-id [_ lang-ui-text]
      (->> core-language
           core-lang/languages
           (filter (fn [[_ lang]]
                     (when (every? string? [lang-ui-text (:ui-text lang)])
                       (= (str/upper-case lang-ui-text)
                          (str/upper-case (:ui-text lang))))))
           ffirst)))

(defn make-automaton-web-languages
  "Create a `SelectedLanguages` instance
  Params:
  * `selected-languages` is a dictionary,
  The final map consists in the languages defined in both `selected-languages` `core-lang/base-languages`
  The language data map are merged, see `merge-languages-map` for details"
  [& selected-languages-seq]
  (->AutomatonWebLanguages (apply core-lang/make-automaton-core-languages
                                  web-languages-map
                                  selected-languages-seq)))

(def automaton-web-languages
  "Languages available in `automaton-web`, instance of `Languages`"
  (make-automaton-web-languages))

(def get-web-languages-ids
  "Known language ids in `automaton-web`"
  (core-lang/languages-ids automaton-web-languages))

(defn get-web-lang
  "Return the language linked to `lang-id`"
  [lang-id]
  (core-lang/language automaton-web-languages lang-id))
