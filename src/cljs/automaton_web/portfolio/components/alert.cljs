(ns automaton-web.portfolio.components.alert
  (:require
   [automaton-web.components.alert :as sut]
   [automaton-web.portfolio.proxy  :as web-proxy]
   [portfolio.reagent-18           :as           portfolio
                                   :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Alerts"})

(defscene error-alert
          (web-proxy/wrap-component [sut/component {:title "Title"
                                                    :type :error
                                                    :message "Message"}]))

(defscene warning-alert
          (web-proxy/wrap-component [sut/component {:title "Title"
                                                    :type :warning
                                                    :message "Message"}]))

(defscene success-alert
          (web-proxy/wrap-component [sut/component {:title "Title"
                                                    :type :assert
                                                    :message "Message"}]))

(defscene title-only
          (web-proxy/wrap-component [sut/component {:title "Title"}]))

(defscene message-only
          (web-proxy/wrap-component [sut/component {:message "Message"}]))
