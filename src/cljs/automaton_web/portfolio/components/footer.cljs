(ns automaton-web.portfolio.components.footer
  (:require
   [automaton-web.components.footer :as sut]
   [automaton-web.components.icons  :as web-icons]
   [automaton-web.portfolio.proxy   :as web-proxy]
   [portfolio.reagent-18            :as           portfolio
                                    :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Footer"})

(def social-networks
  {"youtube" [web-icons/icon {:path-kw :svg/youtube
                              :href "https://www.youtube.com/@Hephaistoxsc"}]
   "linkedin" [web-icons/icon {:path-kw :svg/linkedin
                               :href
                               "https://www.linkedin.com/company/hephaistox"}]
   "github" [web-icons/icon {:path-kw :svg/github
                             :href "https://github.com/hephaistox"}]
   "twitter" [web-icons/icon {:path-kw :svg/twitter
                              :href ""}]})

(def footer-lists
  [{:title "Solutions"
    :items [{:text "Marketing"
             :href "#"}
            {:text "Analytics"
             :href "#"}
            {:text "Commerce"
             :href "#"}
            {:text "Insights"
             :href "#"}]}
   {:title "Support"
    :items [{:text "Pricing"
             :href "#"}
            {:text "Documentation"
             :href "#"}
            {:text "Guides"
             :href "#"}
            {:text "API Status"
             :href "#"}]}
   {:title "Company"
    :items [{:text "About"
             :href "#"}
            {:text "Blog"
             :href "#"}
            {:text "Jobs"
             :href "#"}
            {:text "Press"
             :href "#"}
            {:text "Partners"
             :href "#"}]}
   {:title "Legal"
    :items [{:text "Claim"
             :href "#"}
            {:text "Privacy"
             :href "#"}
            {:text "Terms"
             :href "#"}]}])

(def simple-footer-list
  [{:id "solutions"
    :title "Solutions"
    :href "#"}
   {:id "support"
    :title "Support"
    :href "#"}
   {:id "company"
    :title "Company"
    :href "#"}
   {:id "legal"
    :title "Legal"
    :href "#"}])

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 footer
 (web-proxy/wrap-component
  [sut/footer
   {:social-networks social-networks
    :footer-lists footer-lists
    :quotation
    "Making the world a better place through constructing elegant hierarchies."
    :badge "https://tailwindui.com/img/logos/mark.svg?color=gray&shade=300"
    :company-name "Hephaistox"
    :release "2022-1"
    :title "© 2022 Hephaistox, Inc. All rights reserved!"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 footer-dark
 (web-proxy/wrap-component
  [sut/footer
   {:dark? true
    :social-networks social-networks
    :footer-lists footer-lists
    :quotation
    "Making the world a better place through constructing elegant hierarchies."
    :badge "https://tailwindui.com/img/logos/mark.svg?color=gray&shade=300"
    :company-name "Hephaistox"
    :release "2022-1"
    :title "© 2022 Hephaistox, Inc. All rights reserved!"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene simple-footer
          (web-proxy/wrap-component
           [sut/simple-footer {:title
                               "© 2020 Your Company, Inc. All rights reserved."
                               :footer-lists simple-footer-list
                               :release "2022-1"
                               :social-networks social-networks}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene simple-footer-dark
          (web-proxy/wrap-component
           [sut/simple-footer {:title
                               "© 2020 Your Company, Inc. All rights reserved."
                               :footer-lists simple-footer-list
                               :release "2022-1"
                               :social-networks social-networks
                               :dark? true}]))
