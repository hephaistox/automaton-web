(ns automaton-web.components.input
  "Namespace for basic inputs"
  (:require
   [automaton-web.components.icons            :as web-icons]
   [automaton-web.i18n.fe.auto-web-translator :as auto-web-translator]))

(defn- input
  "Input component, passes appropriate values to html input tag and styles it depending on state or props"
  [{:keys [type
           placeholder
           name
           autocomplete
           id
           value
           checked
           on-blur
           on-change
           class
           disabled?
           invalid?]
    :or {type "text"}}]
  (let [disabled? (cond
                    (fn? disabled?) (disabled? name)
                    (boolean? disabled?) disabled?)]
    [:input
     (merge
      (when value {:value value})
      {:checked checked
       :type type
       :name name
       :id id
       :disabled disabled?
       :on-blur on-blur
       :on-change on-change
       :placeholder placeholder
       :autoComplete autocomplete
       :class
       (vec
        (concat
         ["block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm placeholder:text-gray-400 sm:text-sm sm:leading-6 ring-1 ring-inset"
          ;;Focus
          "focus:ring-2 focus:ring-inset focus:ring-additional "
          ;;Disabled
          "disabled:cursor-not-allowed disabled:bg-gray-50 disabled:text-gray-500 disabled:ring-gray-200"
          ;;Invalid
          (if invalid?
            "text-red-900 ring-red-300 placeholder:text-red-300"
            "ring-gray-300")]
         class))})]))

(defn text-field
  "Text input with different states covered.
   Component can be used both with form or standalone.
   States of the text field:
   required? -> boolean: for displaying it as non-optional
   invalid? -> boolean: for displaying incorrect data in component
   error-message -> string: for displaying message on invalid component
   size -> :full for displaying input on two grid cols
   values -> fn returning string: value of input when component is controlled."
  [{:keys
    [name text size touched error-message errors type values invalid? required?]
    :as params
    :or {type "text"}}]
  (let [invalid? (if (fn? invalid?)
                   (invalid? name)
                   (or invalid?
                       (when required?
                         (and touched (touched name) (get errors name)))))
        e-msg (or error-message (get errors name))]
    [:div {:class [(when (= size :full) "sm:col-span-2")]}
     [:div {:class ["relative mt-2.5"]}
      [:label
       {:for name
        :class
        ["absolute -top-2 left-2 inline-block bg-white px-1 text-xs font-medium text-gray-900"]}
       text
       (when required?
         [:span {:class ["text-red-600"]}
          " *"])]
      [:div
       [input
        (merge params
               (when values {:value (values name "")})
               {:type type
                :invalid? invalid?})]]
      (when invalid?
        [:div
         {:class
          ["pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3"]}
         [web-icons/icon {:class ["icon-red"]}]])]
     (when invalid?
       [:p {:class ["ml-2 mt-2 text-sm text-red-600"]}
        e-msg])]))

(defn checkbox
  "Checkbox input with different states covered.
   Component can be used both with form or standalone.
   States of the text field:
   required? -> boolean: for displaying it as non-optional
   invalid? -> boolean: for displaying incorrect data in component
   error-message -> string: for displaying message on invalid component
   size -> :full for displaying checkbox on two grid cols
   values -> fn returning boolean: checked of input when component is controlled."
  [{:keys [id
           title
           description
           name
           size
           touched
           errors
           values
           error-message
           required?
           invalid?]
    :as params}]
  (let [invalid? (or invalid?
                     (when required?
                       (and touched (touched name) (get errors name))))
        error-message (or error-message (get errors name))]
    [:div {:class ["relative flex items-start"
                   (when (= size :full) "sm:col-span-2")]}
     [:div {:class ["flex h-6 items-center"]}
      [input
       (merge
        (when values {:checked (values name false)})
        {:invalid? invalid?
         :type "checkbox"
         :class
         ["h-4 !w-4 !p-0 rounded border-gray-300 !text-additional focus:!ring-offset-0 focus:!shadow-none"]}
        (dissoc params :values))]]
     [:div {:class ["ml-3 text-sm leading-6"]}
      [:label {:for id
               :class ["font-medium text-gray-900"]}
       title
       (when required?
         [:span {:class ["text-red-600"]}
          " *"])]
      [:p {:id "comments-description"
           :class ["text-gray-500"]}
       description]
      (when invalid?
        [:p {:class ["ml-2 mt-2 text-sm text-red-600"]}
         error-message])]]))

(defn email-field
  "Optional parameters:
   :id 'email-field'
   :name 'email'"
  [{:keys [id name]
    :or {id "email-field"
         name "email"}
    :as params}]
  [text-field
   (merge params
          {:id id
           :name name
           :text (auto-web-translator/tr :email)
           :type "email"
           :placeholder (auto-web-translator/tr :example "mati@hephaistox.com")
           :autocomplete "email"})])

(defn password-field
  "Optional parameters:
   :id 'password-field'
   :name 'password'"
  [{:keys [id name]
    :or {id "password-field"
         name "password"}
    :as params}]
  [text-field
   (merge params
          {:id id
           :name name
           :text (auto-web-translator/tr :password)
           :type "password"
           :placeholder (auto-web-translator/tr :password)
           :autocomplete "password"})])

(defn first-name-field
  "Optional parameters:
   :id 'fname-field'
   :name 'fname'"
  [{:keys [id name]
    :or {id "fname-field"
         name "fname"}
    :as params}]
  [text-field
   (merge params
          {:id id
           :name name
           :text (auto-web-translator/tr :first-name)
           :placeholder (auto-web-translator/tr :example "Pierre")
           :autocomplete "given-name"})])

(defn last-name-field
  "Optional parameters:
   :id 'lname-field'
   :name 'lname'"
  [{:keys [id name]
    :or {id "lname-field"
         name "lname"}
    :as params}]
  [text-field
   (merge params
          {:id id
           :name name
           :placeholder (auto-web-translator/tr :example "Caumond")
           :text (auto-web-translator/tr :last-name)
           :autocomplete "family-name"})])

(defn company-field
  "Optional parameters:
   :id 'company-field'
   :name 'company'"
  [{:keys [id name]
    :or {id "company-field"
         name "company"}
    :as params}]
  [text-field
   (merge params
          {:id id
           :name name
           :text (auto-web-translator/tr :company)
           :placeholder (auto-web-translator/tr :example "Michelin")
           :autocomplete "organization"})])

(defn checkboxes
  [& checkboxes-params]
  [:fieldset {:class ["sm:col-span-2"]}
   [:div {:class ["divide-y divide-gray-200"]}
    (for [checkbox-params checkboxes-params] [checkbox checkbox-params])]])
