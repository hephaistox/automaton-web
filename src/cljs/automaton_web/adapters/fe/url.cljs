(ns automaton-web.adapters.fe.url
  "Adapter to frontend url
  Utilities function to manipulate that strings"
  (:require
   [automaton-core.url :as url]))

(defn current-url "Current location in the browser" [] (str js/window.location))

(defn current-path [] (str (.. js/window -location -pathname)))

(defn current-location?
  "Is the `url` parameter matching the current location?
  Params:
  * `urls` - urls that you want to compare with current url "
  [& urls]
  (apply url/compare-locations (current-url) urls))

(defn current-path?
  "Is the `path` parameter matching the current location?
  Params:
  * `path` - path that you want to compare with current path"
  [path]
  (apply url/compare-locations (current-path) path))

(defn lang-in-url-par
  "Return the language parameter in the url
  Params:
  * `url`"
  [url]
  (-> url
      url/parse-queries
      :lang))

(defn navigate!
  "Navigate to the `url` and decide if browser should `preserve-history?`
  Params:
  * `url`"
  ([url] (navigate! url true))
  ([url preserve-history?]
   (if preserve-history?
     (.pushState js/window.history nil "" url)
     (.replaceState js/window.history nil "" url))))
