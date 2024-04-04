(ns automaton-web.components.mailchimp
  (:require
   [automaton-web.components.form             :as web-form]
   [automaton-web.components.init-components  :as web-init-components]
   [automaton-web.components.input            :as web-input]
   [automaton-web.components.modal            :as web-modal]
   [automaton-web.i18n.fe.auto-web-translator :as auto-web-translator]))

(def mailchimp-newsletter-modal-id "mailchimp-modal")

(def mailchimp-newsletter-post-link
  "https://hephaistox.us21.list-manage.com/subscribe/post?u=72bfbe492591e717f2da7307d&amp;id=30720956cc&amp;v_id=121&amp;f_id=00e058e1f0")

(defn mailchimp-gdpr-compliance
  "GDPR compliance checkbox on mailchimp data usage."
  [props]
  [web-input/checkbox
   (merge props
          {:title (auto-web-translator/tr :gdpr-required-email)
           :description (auto-web-translator/tr
                         :gdpr-mailchimp-email-description)
           :size :full
           :id "gdpr_1240"
           :name "gdpr[1240]"
           :required? true})])

(defn mailchimp-newsletter-validation
  [{:keys [values]}]
  (let [email (get values "EMAIL" "")
        fname (get values "FNAME" "")
        lname (get values "LNAME" "")
        gdpr-accepted (get values "gdpr[1240]")]
    (cond-> {}
      (empty? fname) (assoc "FNAME"
                            (auto-web-translator/tr :first-name-required))
      (empty? lname) (assoc "LNAME"
                            (auto-web-translator/tr :last-name-required))
      (not (re-matches #".+@.+\..+" email))
      (assoc "EMAIL" (auto-web-translator/tr :email-structure-invalid))
      (empty? email) (assoc "EMAIL" (auto-web-translator/tr :email-required))
      (or (not gdpr-accepted) (false? gdpr-accepted))
      (assoc "gdpr[1240]"
             (auto-web-translator/tr :mailchimp-validation/gdpr-unaccepted)))))

(defn mailchimp-language-input
  [document]
  (let [input (.createElement document "input")
        _setName (.setAttribute input "name" "LANGUAGE")
        _setValue (.setAttribute input "value" (auto-web-translator/get-lang))]
    input))

(defn mailchimp-newsletter-form
  [{:keys [modal-id mailchimp-post-link form-id document]
    :or {document js/document}}]
  [web-form/form-basic
   {:form-id form-id
    :action mailchimp-post-link
    :method "post"
    :on-submit (fn [_props]
                 (web-form/append-form form-id
                                       (mailchimp-language-input document))
                 (web-form/submit-form form-id)
                 (web-init-components/hide-modal document modal-id))
    :component-did-mount
    (fn [{:keys [reset]}]
      (web-init-components/on-modal-hide document modal-id #(reset)))
    :validation mailchimp-newsletter-validation
    :text (auto-web-translator/tr :submit)}
   #(web-input/email-field (merge %
                                  {:id "mailchimp-email"
                                   :name "EMAIL"
                                   :size :full
                                   :required? true}))
   #(web-input/first-name-field (merge %
                                       {:id "mailchimp-first-name"
                                        :name "FNAME"
                                        :required? true}))
   #(web-input/last-name-field (merge %
                                      {:id "mailchimp-last-name"
                                       :name "LNAME"
                                       :required? true}))
   #(web-input/company-field (merge %
                                    {:id "mailchimp-company"
                                     :name "COMPANY"
                                     :size :full}))
   #(mailchimp-gdpr-compliance %)])

(defn mailchimp-newsletter-modal
  [{:keys [modal-id post-link document]
    :or {modal-id mailchimp-newsletter-modal-id
         post-link mailchimp-newsletter-post-link
         document js/document}}]
  [web-modal/modal-big
   {:title [:h2
            {:class
             ["text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl"]}
            (auto-web-translator/tr :newsletter-subscribe-and-materials)]
    :body [mailchimp-newsletter-form {:modal-id modal-id
                                      :mailchimp-post-link post-link
                                      :document document
                                      :form-id (str modal-id
                                                    "-mailchimp-form")}]
    :id modal-id}])
