(ns automaton-web.i18n.language
  "Main namespace for everything i18n language related but project unrelated.
   Contains both usefull functions and values.
   Holds domain-knowledge and decisions (e.g. french def that holds default value for french language)."
  (:require
   [automaton-core.utils.map :as crusher]
   [automaton-web.i18n.dictionary :as automaton-dict]
   [automaton-web.i18n.tempura :as bit]
   [clojure.set :refer [union]]
   [clojure.string :as cs]))

(def french
  "fr")

(def english
  "en")

(def default-language
  french)

(defn language-accepted
  "Returns language if it's accepted.
  If not -> returns nil"
  [lang]
  (case lang
    "fr" french
    ("en" "com") english
    nil))

(defn path-language
  "Get language from url path. Return nil if it's not there"
  [pathname]
  (let [paths (cs/split (str pathname) #"/")
        lang (first (filter #(>= (count %) 2) paths))]
    (language-accepted lang)))

(defn choose-language
  "Chooses language - thins function is here to enclose the logic of choosing a language from different sources."
  [{:keys [path-lang cookies-lang other-lang]}]
  (if-let [path-language path-lang]
    path-language
    (if-let [cookies-language cookies-lang]
      cookies-language
      (if-let [other-language other-lang]
        other-language
        default-language))))

(def missing-text
  bit/tempura-missing-text)

(defn translate-fn
  [& args]
  (apply bit/tempura-tr args))

(defn translate
  "Translation function that takes value from dictionary based on language and text arguments specified.
   Best to be first instantiated with just a dictionary in a def to not create it each time (look at base-transalte)"
  [dictionary lang & text]
  (apply translate-fn dictionary lang text))

(defn create-dictionary
  "Joins base, missing-text option and provided by you dictionary to create one map structure."
  [dict]
  (crusher/deep-merge
   missing-text
   automaton-dict/automaton-dictionary
   dict))

(def automaton-translate
  "Base translation function instantiated, consists only of base dictionary. So it's not creating dictionary each time."
  (partial translate (create-dictionary {})))

(defn language-report
  "For all keys of a dictionnary, return the list of languages set
  `expected-languages` is the languages sequence the report is limited to"
  [dictionary expected-languages]
  (let [filtered-dictionary (select-keys dictionary expected-languages)]
    (apply merge-with union
           (map (fn [[language dict-map]]
                  (into {}
                        (map (fn [v]
                               [v #{language}])
                             (keys
                              (crusher/crush dict-map)))))
                filtered-dictionary))))

(defn key-with-missing-languages
  "Return a map with the path to a translation, with the list of existing languages
  key-exceptions is a sequence or set of all keys that should be excluded from the error list
  `expected-languages` is the languages the report is limited to"
  [dictionary expected-languages key-exceptions]
  (let [key-set-exceptions (into #{} key-exceptions)]
    (filter (fn [[k v]]
              (and (not (contains? key-set-exceptions k))
                   (not= v expected-languages)))
            (language-report dictionary
                             expected-languages))))
