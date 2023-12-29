(ns automaton-web.pages.index
  (:require [automaton-web.hiccup :as web-hiccup]
            [automaton-web.security.csrf-backend :as anti-forgery]))

(defn build
  "Build a webpage header"
  [{:keys [header-elements app-name]} & body]
  (let
    [meta-description
     [:meta
      {:name "description"
       :content
       "With over two decades of expertise in supply chain and IT, working with many industries, we have the tools and knowledge to help you grow!"}]
     icon [:link
           {:rel "icon"
            :href "favicon.ico"}]
     css [:link
          {:type "text/css"
           :rel "stylesheet"
           :href "/css/compiled/styles.css"}]
     title [:title app-name]
     head-elements [meta-description icon css (for [el header-elements] el) title]
     body-elements (merge [(anti-forgery/anti-forgery-html-token)] body)]
    (str (web-hiccup/html-core head-elements body-elements))))
