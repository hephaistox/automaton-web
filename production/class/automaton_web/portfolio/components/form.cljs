(ns automaton-web.portfolio.components.form
  (:require
   [automaton-web.components.button :as web-button]
   [automaton-web.components.form :as sut]
   [automaton-web.components.input :as web-input]
   [automaton-web.portfolio.proxy :as web-proxy]
   [portfolio.reagent-18
    :as
    portfolio
    :refer-macros
    [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Form"})

(defn view-state [{:keys [state]}] [:p "state: " @state])

(defn minimal-form
  [{:keys [state on-change on-blur]}]
  [:div
   [:p "state: " @state]
   [web-input/text-field {:name "input"
                          :on-change on-change
                          :on-blur on-blur}]])

(defn initial-value-form
  [props]
  [:div
   (view-state props)
   [web-input/text-field (merge props {:name "init-text"})]])

(defn submit-form
  [{:keys [on-submit]
    :as props}]
  [:form {:on-submit on-submit
          :class ["flex flex-col gap-4"]}
   [web-input/text-field
    (merge props
           {:text "Name"
            :name "some-text"})]
   [web-button/button {:text "Submit!"
                       :type "submit"}]])

(defn bigger-form
  [{:keys [on-submit]
    :as props}]
  [:form {:on-submit on-submit
          :class ["flex flex-col gap-4"]}
   [web-input/text-field
    (merge props
           {:text "Name"
            :name "name"})]
   [web-input/text-field
    (merge props
           {:text "Surname"
            :name "last-name"})]
   [web-input/text-field
    (merge props
           {:text "Company"
            :name "company"})]
   [web-input/text-field
    (merge props
           {:text "How do you feel?"
            :name "feel"})]
   [web-input/checkbox
    (merge props
           {:title "Agree?"
            :name "agree"})]
   [web-button/button {:text "Submit!"
                       :type "submit"}]])

(defn validation-fn
  [{:keys [values]}]
  (cond-> {}
    (empty? (get values "name" "")) (assoc "name" "Name required!")
    (empty? (get values "last-name" "")) (assoc "last-name"
                                                "Surname required!")))

(defn validation-form
  [{:keys [on-submit reset]
    :as props}]
  [:form {:on-submit on-submit
          :class ["flex flex-col gap-4"]}
   [web-input/text-field
    (merge props
           {:text "Name"
            :name "name"
            :required? true})]
   [web-input/text-field
    (merge props
           {:text "Surname"
            :name "last-name"
            :required? true})]
   [web-input/text-field
    (merge props
           {:text "Company"
            :name "company"})]
   [web-input/text-field
    (merge props
           {:text "How do you feel?"
            :name "feel"})]
   [web-input/checkbox
    (merge props
           {:title "Agree?"
            :name "agree"})]
   [:div {:class ["flex justify-between"]}
    [web-button/button {:text "Submit!"
                        :type "submit"}]
    [:div {:class ["cursor-pointer"]
           :on-click #(reset)}
     "Clear!"]]])

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene form
          (web-proxy/wrap-component [sut/form {}
                                     minimal-form]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene form-initial-value
          (web-proxy/wrap-component [sut/form {:initial-values {"init-text"
                                                                "initated"}}
                                     initial-value-form]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene form-submittable
          (web-proxy/wrap-component
           [sut/form {:on-submit (fn [props] (js/alert (str "props:" props)))}
            submit-form]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene form-more-components
          (web-proxy/wrap-component
           [sut/form {:on-submit (fn [props] (js/alert (str "props:" props)))}
            bigger-form]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene form-with-validation
          (web-proxy/wrap-component
           [sut/form {:on-submit (fn [props] (js/alert (str "props:" props)))
                      :validation validation-fn}
            validation-form]))

(defn validation
  [{:keys [values]}]
  (let [email (get values "email" "")]
    (cond-> {}
      (not (re-matches #".+@.+\..+" email)) (assoc "email"
                                                   "This is not correct email!")
      (empty? email) (assoc "email" "Email needs to be filled")
      (or (not (get values "checkbox1")) (false? (get values "checkbox1")))
      (assoc "checkbox1" "You need to agree"))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene form-basic
          (web-proxy/wrap-component [sut/form-basic {}
                                     web-input/email-field]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene form-basic-full
          (web-proxy/wrap-component
           [sut/form-basic {:form-id "full-form basic"
                            :on-submit (fn [props]
                                         (js/alert (str
                                                    "I'm your full form basic!"
                                                    props)))
                            :validation validation
                            :text "Show props"}
            #(web-input/email-field (merge %
                                           {:id "email"
                                            :size :full
                                            :required? true}))
            web-input/first-name-field
            web-input/company-field
            #(web-input/checkboxes (merge %
                                          {:id "checkbox1"
                                           :name "checkbox1"
                                           :title "Important require"
                                           :required? true})
                                   (merge %
                                          {:id "checkbox2"
                                           :name "checkbox2"
                                           :title "Conditions and rules"})
                                   (merge %
                                          {:id "checkbox3"
                                           :name "checkbox3"
                                           :title "Additional"}))]))

(defscene form-login
          (web-proxy/wrap-component [sut/form-login {:form-id "login-form"
                                                     :on-submit-fn
                                                     (fn [e] (prn "e: " e))}]))

(defscene form-login-forgot-link
          (web-proxy/wrap-component [sut/form-login {:form-id "login-form"
                                                     :on-submit-fn
                                                     (fn [e] (prn "e: " e))
                                                     :forgot-link "#"}]))
