(ns automaton-web.hiccup
  (:require [hiccup2.core :as hiccup2]
            [hiccup.page :as hiccup-page]))

(defn html-core
  [head-elements body]
  (hiccup2/html (hiccup-page/doctype :html5)
                [:html
                 [:head [:meta {:charset "utf-8"}]
                  [:meta
                   {:content "width=device-width,initial-scale=1"
                    :name "viewport"}] (when head-elements (for [header-el head-elements] header-el))] [:body (for [el body] el)]]))

(defn js-script-raw
  "Uses `content` inside of a js script hiccup.
   Content is treated as raw string."
  [content]
  [:script {:type "text/javascript"} (hiccup2/raw content)])
