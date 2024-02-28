(ns automaton-web.pages.index
  (:require
   [automaton-web.hiccup :as web-hiccup]
   [automaton-web.security.csrf-backend :as anti-forgery]))

(defn build
  "Build a webpage header"
  [{:keys [header-elements meta-tags]} & body]
  (let [{:keys [image description title]} meta-tags
        meta-description [:meta {:name "description"
                                 :content description}]
        meta-image [:meta {:name "image"
                           :content image}]
        meta-title [:meta {:name "title"
                           :content title}]
        icon [:link {:rel "icon"
                     :href "favicon.ico"}]
        css [:link {:type "text/css"
                    :rel "stylesheet"
                    :href "/css/compiled/styles.css"}]
        head-elements [meta-title
                       meta-image
                       meta-description
                       icon
                       css
                       (for [el header-elements] el)]
        body-elements (merge [(anti-forgery/anti-forgery-html-token)] body)]
    (str (web-hiccup/html-core head-elements body-elements))))
