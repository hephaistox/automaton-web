(ns automaton-web.pages.index
  (:require
   [automaton-web.hiccup :as web-hiccup]
   [automaton-web.security.csrf-backend :as anti-forgery]))

(defn build
  "Build a webpage header"
  [{:keys [header-elements meta-tags]} & body]
  (let [{:keys [image description title type url twitter-content twitter-site]}
        meta-tags
        meta-basic-title [:meta {:name "title"
                                 :content title}]
        meta-basic-description [:meta {:name "description"
                                       :content description}]
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
        twitter-meta-card [:meta {:name "twitter:card"
                                  :content twitter-content}]
        twitter-meta-description [:meta {:name "twitter:description"
                                         :content description}]
        twitter-meta-image [:meta {:name "twitter:image"
                                   :content image}]
        twitter-meta-title [:meta {:name "twitter:title"
                                   :content title}]
        twitter-meta-site [:meta {:name "twitter:site"
                                  :content twitter-site}]
        icon [:link {:rel "icon"
                     :href "favicon.ico"}]
        css [:link {:type "text/css"
                    :rel "stylesheet"
                    :href "/css/compiled/styles.css"}]
        html-title [:title title]
        head-elements [meta-basic-title
                       meta-basic-description
                       meta-title
                       meta-type
                       meta-url
                       meta-image
                       meta-description
                       twitter-meta-card
                       twitter-meta-title
                       twitter-meta-site
                       twitter-meta-image
                       twitter-meta-description
                       icon
                       css
                       (for [el header-elements] el)
                       html-title]
        body-elements (merge [(anti-forgery/anti-forgery-html-token)] body)]
    (str (web-hiccup/html-core head-elements body-elements))))
