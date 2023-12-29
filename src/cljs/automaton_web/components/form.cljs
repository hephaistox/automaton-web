(ns automaton-web.components.form
  (:require [automaton-core.utils.uuid-gen :as uuid-gen]
            [automaton-web.components.button :as web-button]
            [automaton-web.components.input :as web-input]
            [automaton-web.i18n.fe.auto-web-translator :as auto-web-translator]
            [automaton-web.react-proxy :as web-react]
            [clojure.data :as clj-data]))

(defn initialize-state
  [{:keys [state initial-values initial-touched]}]
  (let [values (or (merge initial-values initial-touched) {})
        initialized-state {:initial-values initial-values
                           :initial-touched initial-touched
                           :values values
                           :touched (into #{} (keys initial-touched))}]
    (if-let [user-provided-state state]
      (do (swap! user-provided-state (fn [db] (merge initialized-state db))) user-provided-state)
      (web-react/ratom initialized-state))))

(defn element-value
  [evt]
  (let [type (-> evt
                 .-target
                 .-type)]
    (case type
      "checkbox" (-> evt
                     .-target
                     .-checked)
      (-> evt
          .-target
          .-value))))

(defn element-name
  [value]
  (-> value
      .-target
      (.getAttribute "name")))

(defn set-values
  [new-values state]
  (swap! state #(-> %
                    (update :values merge new-values)
                    (update :touched (fn [x y] (apply conj x y)) (keys new-values)))))

(defn touched [state k] (or (:attempted-submissions @state) (get (:touched @state) k)))

(defn set-touched [names state] (swap! state update :touched (fn [x y] (apply conj x y)) names))

(defn set-untouched [names state] (swap! state update :touched (fn [x y] (apply disj x y)) names))

(defn disable [state & [ks]] (swap! state update :disabled? #(apply conj ((fnil into #{}) %) ks)))

(defn enable [state & [ks]] (swap! state update :disabled? #(apply disj % ks)))

(defn disabled? [state k] (get (:disabled? @state) k))

(defn handle-validation [state validation] (let [resolved (validation state)] (when-not (every? empty? resolved) resolved)))

(defn on-change
  [evt state]
  (let [input-key (element-name evt) input-value (element-value evt)] (swap! state update :values assoc input-key input-value)))

(defn on-blur [evt state] (let [input-key (element-name evt)] (swap! state update :touched conj input-key)))

(defn set-on-change
  [{:keys [value]} state]
  (let [path (cons :values @state)
        curr-value (get-in @state path)
        val (if (fn? value) (value curr-value) value)]
    (swap! state assoc-in path val)))

(defn set-on-blur [{:keys [value]} state] (swap! state update :touched (if value conj disj)))

(defn dirty [values initial-values] (first (clj-data/diff values (or initial-values {}))))

(defn on-submit
  [evt {:keys [state on-submit validation reset]}]
  (.preventDefault evt)
  (swap! state update :attempted-submissions inc)
  (when (nil? validation)
    (swap! state update :successful-submissions inc)
    (on-submit {:state state
                :values (:values @state)
                :dirty (dirty (:values @state) (merge (:initial-values @state) (:touched-values @state)))
                :reset reset})))

(defn form
  [props _]
  (let [state (initialize-state props)
        form-id (or (:form-id props) (str (uuid-gen/unguessable)))
        handlers {:touched (fn [k] (touched state k))
                  :set-touched (fn [& ks] (set-touched ks state))
                  :set-untouched (fn [& ks] (set-untouched ks state))
                  :set-values #(set-values % state)
                  :disable (fn [& ks] (disable state ks))
                  :enable (fn [& ks] (enable state ks))
                  :disabled? #(disabled? state %)
                  :set-on-change #(set-on-change % state)
                  :set-on-blur #(set-on-blur % state)
                  :on-change #(on-change % state)
                  :on-blur #(on-blur % state)
                  :reset (fn [& [m]]
                           (reset! state (merge {:values {}
                                                 :touched #{}}
                                                m)))}]
    (web-react/create-class
     {:component-did-mount #(when-let [on-mount (:component-did-mount props)] (on-mount handlers))
      :reagent-render (fn [props component]
                        (let [validation (when-let [val-fn (:validation props)] (handle-validation @state val-fn))]
                          [component
                           {:props (:props props)
                            :state state
                            :form-id form-id
                            :values (:values @state)
                            :dirty (dirty (:values @state) (merge (:initial-values @state) (:touched-values @state)))
                            :errors validation
                            :touched (:touched handlers)
                            :set-touched (:set-touched handlers)
                            :set-untouched (:set-untouched handlers)
                            :attempted-submissions (or (:attempted-submissions @state) 0)
                            :successful-submissions (or (:successful-submissions @state) 0)
                            :set-values (:set-values handlers)
                            :disable (:disable handlers)
                            :enable (:enable handlers)
                            :disabled? (:disabled? handlers)
                            :set-on-change (:set-on-change handlers)
                            :set-on-blur (:set-on-blur handlers)
                            :on-change (:on-change handlers)
                            :on-blur (:on-blur handlers)
                            :reset (:reset handlers)
                            :on-submit (fn [evt]
                                         (on-submit evt
                                                    (merge props
                                                           {:state state
                                                            :form-id form-id
                                                            :validation validation
                                                            :reset (:reset handlers)})))}]))})))

(defn append-form [form-id el] (.appendChild (.getElementById js/document form-id) el))

(defn submit-form
  [form-id]
  (-> js/document
      (.getElementById form-id)
      (.submit)))

(defn form-basic
  "Quicker option to create the form component.
  Accepts props map for form defaults and elements to be displayed in the form.

  in the map argument set
  :text key for the button text, otherwise defaults to english."
  [{:keys [form-id on-submit validation action method component-did-mount text]
    :or {form-id (str (uuid-gen/unguessable))
         text (auto-web-translator/tr :submit)}} & elements]
  (let [submit-fn (or on-submit #(submit-form form-id))]
    [form
     {:validation validation
      :form-id form-id
      :on-submit submit-fn
      :component-did-mount component-did-mount}
     (fn [{:keys [form-id errors on-submit attempted-submissions]
           :as props}]
       [:form
        (merge {:id form-id
                :on-submit on-submit}
               (when action
                 {:action action
                  :method method
                  :target "_blank"}))
        [:div {:class ["grid grid-cols-1 gap-x-8 gap-y-6 sm:grid-cols-2"]} (for [el elements] ^{:key (str el)} [el props])
         [web-button/button
          {:disabled (and (seq errors) (> attempted-submissions 0))
           :class ["sm:col-span-2"]
           :text text
           :type "submit"}]]])]))

(defn- login-validation
  [values email-name password-name]
  (let [email (get values email-name "")
        password (get values password-name "")]
    (cond-> {}
      (not (re-matches #".+@.+\..+" email)) (assoc email-name (auto-web-translator/tr :email-structure-invalid))
      (empty? email) (assoc email-name (auto-web-translator/tr :email-required))
      (empty? password) (assoc password-name (auto-web-translator/tr :password-required))
      (< (count password) 12) (assoc password-name (auto-web-translator/tr :password-must-be-more-than-12))
      (nil? (re-find #"[A-Z]" password)) (assoc password-name (auto-web-translator/tr :password-must-contain-uppercase))
      (nil? (re-find #"[a-z]" password)) (assoc password-name (auto-web-translator/tr :password-must-contain-lowercase))
      (nil? (re-find #"[0-9]" password)) (assoc password-name (auto-web-translator/tr :password-must-contain-number))
      (nil? (re-find #"[^\w\*]" password)) (assoc password-name (auto-web-translator/tr :password-must-contain-special-character)))))

(defn forgot-password
  [{:keys [link]}]
  [:div {:class ["text-sm leading-6 text-right"]}
   [:a
    {:href link
     :class ["font-semibold text-indigo-600 hover:text-indigo-500"]} "Forgot password?"]])

(defn form-login
  [{:keys [form-id on-submit-fn forgot-link]}]
  (let [email-name "login-email"
        password-name "login-password"]
    [form-basic
     {:form-id form-id
      :method "post"
      :on-submit on-submit-fn
      :validation #(login-validation % email-name password-name)
      :text (auto-web-translator/tr :sign-in)}
     #(web-input/email-field (merge %
                                    {:id email-name
                                     :name email-name
                                     :size :full
                                     :required? true}))
     #(web-input/password-field (merge %
                                       {:id password-name
                                        :name password-name
                                        :size :full
                                        :required? true}))
     #(web-input/checkbox (merge %
                                 {:title (auto-web-translator/tr :remember-me)
                                  :name "remember"})) #(when forgot-link (forgot-password (merge % {:link forgot-link})))]))
