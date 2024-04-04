(ns automaton-web.portfolio.portfolio
  (:require
   [automaton-web.portfolio.components.alert]
   [automaton-web.portfolio.components.button]
   [automaton-web.portfolio.components.card]
   [automaton-web.portfolio.components.checkbox]
   [automaton-web.portfolio.components.footer]
   [automaton-web.portfolio.components.form]
   [automaton-web.portfolio.components.grid-list]
   [automaton-web.portfolio.components.header]
   [automaton-web.portfolio.components.icons]
   [automaton-web.portfolio.components.input]
   [automaton-web.portfolio.components.logo]
   [automaton-web.portfolio.components.mailchimp]
   [automaton-web.portfolio.components.menu]
   [automaton-web.portfolio.components.menu-item]
   [automaton-web.portfolio.components.modal]
   [automaton-web.portfolio.components.navigation]
   [automaton-web.portfolio.components.section]
   [automaton-web.portfolio.components.select]
   [automaton-web.portfolio.components.structure]
   [automaton-web.portfolio.components.table]
   [automaton-web.portfolio.components.tooltip]
   [automaton-web.portfolio.components.version]
   [automaton-web.portfolio.pages.error]
   [automaton-web.portfolio.proxy :as web-proxy]))

(defonce app
  (web-proxy/start!
   {:config {:css-paths ["/css/compiled/styles.css"]
             :viewport/options [{:title "<sm | small phone"
                                 :value {:viewport/width 390
                                         :viewport/height 640}}
                                {:title "Tailwind sm (iPhone)"
                                 :value {:viewport/width 640
                                         :viewport/height 1136}}
                                {:title "Tailwind md (iPad Mini)"
                                 :value {:viewport/width 768
                                         :viewport/height 1024}}
                                {:title "Tailwind lg (iPad Pro)"
                                 :value {:viewport/width 1024
                                         :viewport/height 1366}}
                                {:title "Tailwind xl (laptop)"
                                 :value {:viewport/width 1280
                                         :viewport/height 800}}
                                {:title "Tailwind 2xl (Macbook Pro)"
                                 :value {:viewport/width 1536
                                         :viewport/height 960}}
                                {:title ">2xl | Big monitor"
                                 :value {:viewport/width 2560
                                         :viewport/height 1440}}
                                {:title "Auto"
                                 :value {:viewport/width "100%"
                                         :viewport/height "100%"}}]
             ;; Default - tailwind sm
             :viewport/defaults {:viewport/width 640
                                 :viewport/height 1136}}
    :index (web-proxy/search-index)}))

(defn init [] app)

(web-proxy/register-collection! :components {:title "Components"})

(web-proxy/register-collection! :pages {:title "Pages"})

