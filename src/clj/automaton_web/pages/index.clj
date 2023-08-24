(ns automaton-web.pages.index
  (:require
   [hiccup2.core :as h.page]
   [automaton-web.security.csrf-backend :as anti-forgery]
   [automaton-web.js-interop :as ws-util]))

(defn build
  "Build a webpage header"
  [{:keys [header-elements app-name envVars]} & body]
  (str (h.page/html
        [:html
         [:head
          [:meta {:charset "utf-8"}]
          [:meta
           {:content "width=device-width,initial-scale=1", :name "viewport"}]
          [:meta {:name "description" :content "With over two decades of expertise in supply chain and IT, working with many industries, we have the tools and knowledge to help you grow!"}]
          [:link {:rel "icon" :href "favicon.ico"}]
          [:link {:type "text/css", :rel "stylesheet", :href "/css/compiled/main.css"}]
          (when header-elements
            (for [header-el header-elements]
              header-el))

          [:script
           (h.page/raw (format "var _envVars =%s"
                               (ws-util/mapToJSmap envVars)))]
          [:title app-name]]
         (vec
          (concat
           [:body
            (anti-forgery/anti-forgery-html-token)
            (for [el body]
              el)]))])))
