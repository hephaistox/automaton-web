(ns automaton-web.i18n.language-frontend
  "Translation function ready to be used."
  (:require
   ["react" :as react]
   [automaton-web.i18n.language :as bil]
   [clojure.string :as cs]))

(defn cookies-language
  "Get cookies value under 'lang' key from client browser cookies"
  []
  (let [cookies (str (.-cookie js/document))
        cookies-vec (cs/split cookies #"; ")
        lang (some (fn [cookie]
                     (let [[k v] (cs/split (str cookie) #"=")]
                       (when (= k "lang")
                         v)))
                   cookies-vec)]
    lang))

(defn select-language
  "Selects the language based on js enviroment."
  []
  (let [path-lang (bil/path-language (. js/window.location -pathname))
        cookies-lang (cookies-language)]
    (bil/choose-language {:path-lang path-lang :cookies-lang cookies-lang})))

(defn btr
  "Base tr, it's called to translate text in base - using only base dictionary.
   If you want to translate text with custom dictionary you need to recreate base-translate and btr."
  [& args]
  (apply bil/automaton-translate (select-language) args))

(defonce language
  (react/createContext (select-language)))

(def language-provider
  "Provider for creating language react context"
  (.-Provider language))

(defn current-language-context
  "Get's current language from the react context"
  []
  (react/useContext language))
