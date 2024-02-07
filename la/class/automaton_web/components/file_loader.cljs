(ns automaton-web.components.file-loader
  "Allow the loading of a local file
   See [mozilla input definition](https://developer.mozilla.org/fr/docs/Web/HTML/Element/Input/file)"
  (:require
   [ajax.core :as ajax]
   [automaton-core.log :as core-log]
   [automaton-web.components.alert :as alert]
   [automaton-web.events-proxy :as web-events-proxy]
   [automaton-web.security.csrf-frontend :as csrf-frontend]
   [clojure.string :as str]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))



(defn component
  "Create an input html entity, wired with reframe and ajax to send the file to the uri.
  Parameters are:
  * tag: each instance of the component are identified with their tag
  * message: is the string to be displayed when no file is selected
  * uri: where to post the loaded file(s)
  * field-name is the name of the field where to post the file in the post http message"
  [{:keys [tag message uri urifield]
    :or {message "Select a file"}
    :as input}]
  (if (and tag uri urifield)
    (let [files (web-events-proxy/subscribe [::files tag])
          upload-message (web-events-proxy/subscribe [::upload-message tag])
          network-error (web-events-proxy/subscribe [::network-error tag])
          loading? (web-events-proxy/subscribe [::loading? tag])]
      [:div {:class ["flex flex-wrap -mx-4 file-loader"]}
       [:div {:class ["lg:w-5/12" "w-full px-4 mb-12"]}
        [:div {:class ["flex flex-row"]}
         (let [nb-files (if @files (.-count @files) 0)]
           [:label {:for "image-upload"}
            (if (= 0 nb-files)
              message
              (str "File"
                   (when (> 1 nb-files) "s")
                   ": ("
                   (str/join ", " (for [file @files] (.-name file)))
                   ")"))])
         (when @loading?
           [:div
            {:class
             ["flex justify-center items-center inline-block ml-2 float-right"]}
            [:div
             {:class
              ["spinner-border animate-spin inline w-8 h-8 border-4 rounded-full"]
              :role "status"}]
            [:span {:class ["visually-hidden"]}
             "Loading ..."]])]
        [:input
         (merge
          input
          {:id "image-upload"}
          {:type "file"
           :class
           ["w-full border-[1.5px] border-form-stroke rounded-lg font-medium text-body-color placeholder-body-color outline-none focus:border-primary active:border-primary
transition disabled:bg-[#F5F7FD] disabled:cursor-default cursor-pointer
file:bg-[#F5F7FD] file:border-0 file:border-solid file:border-r file:border-collapse file:border-form-stroke
file:py-3 file:px-5 file:mr-5 file:text-body-color file:cursor-pointer file:hover:bg-primary file:hover:bg-opacity-10"]
           :on-change #(web-events-proxy/dispatch [::files-selected
                                                   tag
                                                   (-> %
                                                       .-target
                                                       .-files)
                                                   uri
                                                   urifield])})]
        (when @network-error
          [alert/component {:message @network-error
                            :title "Network error"}])
        (when @upload-message
          [alert/component {:message @upload-message
                            :title "Upload error"}])]])
    [:div "Error in the component parameters"]))

(web-events-proxy/reg-event-fx
 ::files-selected
 (fn-traced [{:keys [db]} [_ tag files uri uri-field]]
            {:db (assoc-in db
                  [:file-upload tag]
                  {:uri uri
                   :field-name uri-field
                   :files files})
             :fx [[:dispatch [::trigger-upload tag files]]]}))

(web-events-proxy/reg-event-db
 ::file-upload-success
 (fn-traced [db [_ tag]] (assoc-in db [:file-upload tag :success] true)))

(web-events-proxy/reg-event-fx
 ::trigger-upload
 (fn-traced [{:keys [db]} [_ tag files]]
            (let [uri (get-in db [:file-upload tag :uri])
                  form-data (js/FormData.)
                  file (aget files 0)
                  field-name (get-in db [:file-upload tag :field-name])]
              (.append form-data field-name file)
              {:db (assoc-in db [:file-upload tag :loading?] true)
               :http-xhrio {:method :post
                            :body form-data
                            :format (ajax/json-request-format)
                            :headers {:x-csrf-token csrf-frontend/?csrf-token}
                            :uri uri
                            :timeout 8000
                            :response-format (ajax/json-response-format
                                              {:keywords? true})
                            :on-success [::loading-success tag]
                            :on-failure [::error-message tag]}})))

(web-events-proxy/reg-event-fx
 ::loading-success
 (fn-traced [{:keys [db]}
             [_
              tag
              {:keys [status _uuid _size _filename]
               :as upload-response}]]
            (core-log/trace "upload response  is  " upload-response)
            {:db (assoc-in db [:file-upload tag :loading?] false)
             :fx [[:dispatch [::reset-selector tag]]
                  (if (= "done" status)
                    [:dispatch [::new-file-uploaded tag upload-response]]
                    [:dispatch [::file-upload-error tag]])]}))

(web-events-proxy/reg-event-fx
 ::error-message
 (fn-traced
  [{:keys [db]} [_ tag result]]
  {:db (if (= 0 (:status result))
         (assoc-in db [:file-upload tag :network-error] (:status-text result))
         (assoc-in db [:file-upload tag :upload-error] (:status-text result)))
   :fx [[:dispatch [::stop-loading tag]]]}))

(web-events-proxy/reg-event-db
 ::reset-selector
 (fn-traced [db [_ tag]]
            (let [image-upload (. js/document (getElementById "image-upload"))]
              (set! (.-value image-upload) "")
              (assoc-in db [:file-upload tag :files] nil))))

(web-events-proxy/reg-event-db
 ::file-upload-error
 (fn-traced [db [_ tag]] (assoc-in db [:file-upload tag :error?] true)))

(web-events-proxy/reg-sub ::files
                          (fn [db [_ tag]]
                            (get-in db [:file-upload tag :files])))

(web-events-proxy/reg-sub ::upload-message
                          (fn [db [_ tag]]
                            (get-in db [:file-upload tag :upload-error])))

(web-events-proxy/reg-sub ::network-error
                          (fn [db [_ tag]]
                            (get-in db [:file-upload tag :network-error])))

(web-events-proxy/reg-sub ::loading?
                          (fn [db [_ tag]]
                            (get-in db [:file-upload tag :loading?])))
