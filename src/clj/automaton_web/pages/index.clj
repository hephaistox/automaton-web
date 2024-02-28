(ns automaton-web.pages.index
  (:require
   [automaton-web.hiccup :as web-hiccup]
   [automaton-web.security.csrf-backend :as anti-forgery]))

(defn build
  "Build a webpage header"
  [{:keys [header-elements meta-tags]} & body]
  (let [{:keys [image description title type url]} meta-tags
        meta-type [:meta {:name "og:type"
                          :content type}]
        meta-description [:meta {:name "og:description"
                                 :content description}]
        meta-image [:meta {:name "og:image"
                           :content image}]
        meta-title [:meta {:name "og:title"
                           :content title}]
        meta-url [:meta {:name "og:url"
                         :content url}]
        icon [:link {:rel "icon"
                     :href "favicon.ico"}]
        css [:link {:type "text/css"
                    :rel "stylesheet"
                    :href "/css/compiled/styles.css"}]
        head-elements [meta-title
                       meta-type
                       meta-url
                       meta-image
                       meta-description
                       icon
                       css
                       (for [el header-elements] el)]
        body-elements (merge [(anti-forgery/anti-forgery-html-token)] body)]
    (str (web-hiccup/html-core head-elements body-elements))))
