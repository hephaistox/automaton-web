(ns automaton-web.components.fe-language-select
  "A selector component to select the used language
  Is wired with database and will update the app language"
  (:require
   [automaton-web.components.simple-select :as web-simple-select]
   [automaton-web.events.subs :as web-subs]
   [automaton-web.events-proxy :as web-events-proxy]))

(defn language-select
  "UI component for selecting the language
  Params:
  * `change-lang-kw` is the name of the event to change the language
  * `ui-languages` set of values of language to display in the ui
  * `lang-id-to-str-fn` function to change the language id to a string"
  [change-lang-kw ui-languages lang-id-to-str-fn]
  (let [selected-value (-> (web-events-proxy/subscribe-value [::web-subs/lang])
                           lang-id-to-str-fn)]
    [web-simple-select/simple-select
     {:id "lang"
      :name "lang"
      :on-change #(web-events-proxy/dispatch [change-lang-kw %])
      :value selected-value
      :options (map (fn [{:keys [value]}] [:option {:value value}
                                           value])
                    ui-languages)}]))
