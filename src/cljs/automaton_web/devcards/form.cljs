(ns automaton-web.devcards.form
  (:require
   [devcards.core :as dc :refer [defcard]]

   [automaton-web.components.form :as sut]
   [automaton-web.components.input :as bci]
   [automaton-web.devcards.utils :as bdu]
   [automaton-web.components.button :as bcb]))

(defn view-state [{:keys [state]}]
  [:p "(for devcards) state: " @state])

(defn minimal-form
  [{:keys [state on-change on-blur]}]
  [:div
   [:p "(for devcards) state: " @state]
   [bci/text-field {:name "input"
                    :on-change on-change
                    :on-blur on-blur}]])

(defn initial-value-form [props]
  [:div
   (view-state props)
   [bci/text-field (merge props
                          {:name "init-text"})]])

(defn submit-form [{:keys [on-submit] :as props}]
  [:form
   {:on-submit on-submit
    :class ["flex flex-col gap-4"]}
   [bci/text-field (merge props
                          {:text "Name"
                           :name "some-text"})]
   [bcb/button {:text "Submit!"
                :type "submit"}]])

(defn bigger-form [{:keys [on-submit] :as props}]
  [:form
   {:on-submit on-submit
    :class ["flex flex-col gap-4"]}
   [bci/text-field (merge props
                          {:text "Name"
                           :name "name"})]
   [bci/text-field (merge props
                          {:text "Surname"
                           :name "last-name"})]
   [bci/text-field (merge props
                          {:text "Company"
                           :name "company"})]
   [bci/text-field (merge props
                          {:text "How do you feel?"
                           :name "feel"})]
   [bci/checkbox (merge props
                        {:title "Agree?"
                         :name "agree"})]
   [bcb/button {:text "Submit!"
                :type "submit"}]])

(defn validation-fn [{:keys [values]}]
  (cond->
   {}
    (empty? (get values "name" ""))
    (assoc "name" "Name required!")

    (empty? (get values "last-name" ""))
    (assoc "last-name" "Surname required!")))

(defn validation-form [{:keys [on-submit reset] :as props}]
  [:form
   {:on-submit on-submit
    :class ["flex flex-col gap-4"]}
   [bci/text-field (merge props
                          {:text "Name"
                           :name "name"
                           :required? true})]
   [bci/text-field (merge props
                          {:text "Surname"
                           :name "last-name"
                           :required? true})]
   [bci/text-field (merge props
                          {:text "Company"
                           :name "company"})]
   [bci/text-field (merge props
                          {:text "How do you feel?"
                           :name "feel"})]
   [bci/checkbox (merge props
                        {:title "Agree?"
                         :name "agree"})]
   [:div
    {:class ["flex justify-between"]}
    [bcb/button {:text "Submit!"
                 :type "submit"}]
    [:div
     {:class ["cursor-pointer"]
      :on-click #(reset)}
     "Clear!"]]])

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard form
  (bdu/wrap-component [:div
                       [:h3 "Minimal form"]
                       [sut/form {}
                        minimal-form]
                       [:h3 "Form with initial value"]
                       [sut/form {:initial-values {"init-text" "initated"}}
                        initial-value-form]
                       [:h3 "Submitable form"]
                       [sut/form {:on-submit (fn [props]
                                               (js/alert (str "props:" props)))}
                        submit-form]
                       [:h3 "Bigger form"]
                       [sut/form {:on-submit (fn [props]
                                               (js/alert (str "props:" props)))}
                        bigger-form]
                       [:h3 "Form validation"]
                       [sut/form {:on-submit (fn [props]
                                               (js/alert (str "props:" props)))
                                  :validation validation-fn}
                        validation-form]]))

(defn validation [{:keys [values]}]
  (cond->
   {}
    (not (re-matches #".+@.+\..+" (get values "email" "")))
    (assoc "email" "This is not correct email!")
    (empty? (get values "email"))
    (assoc "email" "Email needs to be filled")
    (or (not (get values "checkbox1"))
        (false? (get values "checkbox1")))
    (assoc "checkbox1" "You need to agree")))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defcard form-basic
  (bdu/wrap-component
   [:div
    [:h3 "Minimal form basic"]
    [sut/form-basic
     {}
     #(bci/email-field %)]
    [:h3 "Full form basic"]
    [sut/form-basic
     {:form-id "full-form basic"
      :on-submit (fn [props]
                   (js/alert (str "I'm your full form basic!"  props)))
      :validation validation
      :text "Show props"}
     #(bci/email-field (merge %
                              {:id "email"
                               :size :full
                               :required? true}))
     #(bci/first-name-field %)
     #(bci/company-field %)
     #(bci/checkboxes (merge %
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
                              :title "Additional"}))]]))
