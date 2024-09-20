(ns automaton-web.portfolio.components.mailchimp
  (:require
   [automaton-web.components.button    :as web-button]
   [automaton-web.components.mailchimp :as sut]
   [automaton-web.portfolio.proxy      :as web-proxy]
   [portfolio.reagent-18               :as           portfolio
                                       :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Mailchimp"})

(defscene mailchimp
          (web-proxy/wrap-component
           [:div "mailchimp will not open properly due to portfolio modal issue"]))

(defscene
 mailchimp-failing-newsletter
 "Mailchimp failing newsletter modal (This will not result in any real mail address added to mailchimp)"
 (web-proxy/wrap-component [web-button/button {:text "Open modal"}]
                           [sut/mailchimp-newsletter-modal]))

(defscene mailchimp-newsletter
          "Mailchimp successful newsletter modal (Will add email to audience)"
          (web-proxy/wrap-component [web-button/button {:text "Open modal"}]
                                    [sut/mailchimp-newsletter-modal
                                     {:modal-id (str "new-" sut/mailchimp-newsletter-modal-id)}]))

(defscene mailchimp-gdpr-compliance
          [sut/mailchimp-gdpr-compliance {:lang :en}])
