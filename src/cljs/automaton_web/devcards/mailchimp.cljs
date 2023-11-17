(ns automaton-web.devcards.mailchimp
  (:require [automaton-web.components.mailchimp :as sut]
            [automaton-web.components.button :as bcb]
            [automaton-web.components.modal :as bcm]
            [automaton-web.devcards.utils :as bdu]
            [devcards.core :as dc :refer [defcard]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard mailchimp-failing-newsletter
         (bdu/wrap-component [:div [:h3 "Mailchimp failing newsletter modal"]
                              (bcm/wrap-modal-call {:modal-id sut/mailchimp-newsletter-modal-id} [bcb/button {:text "Open modal"}])
                              [sut/mailchimp-newsletter-modal]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard mailchimp-newsletter
         (bdu/wrap-component [:div [:h3 "Mailchimp successful newsletter modal (Will add email to audience)"]
                              (bcm/wrap-modal-call {:modal-id (str "new-" sut/mailchimp-newsletter-modal-id)}
                                                   [bcb/button {:text "Open modal"}])
                              [sut/mailchimp-newsletter-modal {:modal-id (str "new-" sut/mailchimp-newsletter-modal-id)}]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard mailchimp-gdpr-compliance (bdu/wrap-component [sut/mailchimp-gdpr-compliance {:lang :en}]))
