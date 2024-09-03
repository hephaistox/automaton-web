(ns automaton-web.pages.index
  (:require
   [automaton-web.hiccup                :as web-hiccup]
   [automaton-web.security.csrf-backend :as anti-forgery]))

(defn build
  "Build a webpage header"
  [{:keys [header-elements meta-tags]} & body]
  (let [{:keys [image description title type url twitter-content twitter-site author icon]
         :or {icon "/favicon.ico"}}
        meta-tags
        meta-title [:meta {:name "title"
                           :property "og:title"
                           :content title}]
        meta-type [:meta {:name "og:type"
                          :property "og:type"
                          :content type}]
        meta-description [:meta {:name "description"
                                 :property "og:description"
                                 :content description}]
        meta-image [:meta {:name "image"
                           :property "og:image"
                           :content image}]
        meta-url [:meta {:name "og:url"
                         :property "og:url"
                         :content url}]
        meta-author [:meta {:name "author"
                            :content author}]
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
                     :href icon}]
        css [:link {:type "text/css"
                    :rel "stylesheet"
                    :href "/css/compiled/styles.css"}]
        html-title [:title title]
        head-elements [meta-title
                       meta-type
                       meta-url
                       meta-image
                       meta-author
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
