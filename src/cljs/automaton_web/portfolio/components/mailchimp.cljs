(ns automaton-web.portfolio.components.mailchimp
  (:require
   [portfolio.reagent-18
    :as
    portfolio
    :refer-macros
    [defscene configure-scenes]]
   [automaton-web.portfolio.proxy :as web-proxy]
   [automaton-web.components.button :as web-button]
   [automaton-web.components.modal :as web-modal]
   [automaton-web.components.mailchimp :as sut]))

(configure-scenes {:collection :components
                   :title "Mailchimp"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 mailchimp-failing-newsletter
 "Mailchimp failing newsletter modal (This will not result in any real mail address added to mailchimp)"
 (web-proxy/wrap-component
  (web-modal/wrap-modal-call {:modal-id sut/mailchimp-newsletter-modal-id}
                             [web-button/button {:text "Open modal"}])
  [sut/mailchimp-newsletter-modal {:document (web-proxy/iframe-document)}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene mailchimp-newsletter
          "Mailchimp successful newsletter modal (Will add email to audience)"
          (web-proxy/wrap-component
           (web-modal/wrap-modal-call
            {:modal-id (str "new-" sut/mailchimp-newsletter-modal-id)}
            [web-button/button {:text "Open modal"}])
           [sut/mailchimp-newsletter-modal
            {:modal-id (str "new-" sut/mailchimp-newsletter-modal-id)
             :document (web-proxy/iframe-document)}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene mailchimp-gdpr-compliance
          [sut/mailchimp-gdpr-compliance {:lang :en}])
