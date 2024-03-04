(ns automaton-web.components.spinner)

(defn spinner
  []
  [:img {:class ["m-auto max-w-full max-h-full aspect-square animate-fade-in"]
         :src "/images/toolings/spinner.gif"}])
