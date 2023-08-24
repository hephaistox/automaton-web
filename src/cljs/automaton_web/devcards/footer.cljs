(ns automaton-web.devcards.footer
  (:require
   [devcards.core :as dc :refer [defcard]]

   [automaton-web.components.icons :as bci]
   [automaton-web.components.footer :as sut]
   [automaton-web.devcards.utils :as bdu]))

(def social-networks
  {"youtube"  [bci/icon {:path-kw :svg/youtube
                         :href "https://www.youtube.com/@Hephaistoxsc"}]
   "linkedin" [bci/icon {:path-kw :svg/linkedin
                         :href "https://www.linkedin.com/company/hephaistox"}]
   "github"   [bci/icon {:path-kw :svg/github
                         :href "https://github.com/hephaistox"}]
   "twitter"  [bci/icon {:path-kw :svg/twitter
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
(defcard footer
  (bdu/wrap-component [:div
                       [:h1 "Footer"]
                       [sut/footer {:social-networks social-networks
                                    :footer-lists footer-lists
                                    :quotation "Making the world a better place through constructing elegant hierarchies."
                                    :badge "https://tailwindui.com/img/logos/mark.svg?color=gray&shade=300",
                                    :company-name "Hephaistox"
                                    :release "2022-1"
                                    :title "© 2022 Hephaistox, Inc. All rights reserved!"}]
                       [:h1 "Dark Footer"]
                       [sut/footer {:dark? true
                                    :social-networks social-networks
                                    :footer-lists footer-lists
                                    :quotation "Making the world a better place through constructing elegant hierarchies."
                                    :badge "https://tailwindui.com/img/logos/mark.svg?color=gray&shade=300",
                                    :company-name "Hephaistox"
                                    :release "2022-1"
                                    :title "© 2022 Hephaistox, Inc. All rights reserved!"}]]))

(defcard simple-footer
  (bdu/wrap-component [:div
                       [:h1 "Simple footer"]
                       [sut/simple-footer
                        {:title "© 2020 Your Company, Inc. All rights reserved."
                         :footer-lists simple-footer-list
                         :release "2022-1"
                         :social-networks social-networks}]
                       [:h1 "Simple footer dark"]
                       [sut/simple-footer
                        {:title "© 2020 Your Company, Inc. All rights reserved."
                         :footer-lists simple-footer-list
                         :release "2022-1"
                         :social-networks social-networks
                         :dark? true}]]))
