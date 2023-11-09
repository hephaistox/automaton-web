(ns automaton-web.duplex.core
  (:require [taoensso.sente :as sente]
            [automaton-core.log :as log]
            [automaton-web.duplex.route :as duplex-route]
            [automaton-web.security.csrf-frontend :as csrf-frontend]))

(if csrf-frontend/?csrf-token
  (log/info "Init of realtime component, token is:" csrf-frontend/?csrf-token)
  (log/error (ex-info "Fail during realtime component, CSRF token not found"
                      {:cause "?csrf-token does not exist"
                       :data csrf-frontend/?csrf-token})))

(try (let [{:keys [chsk ch-recv send-fn state]} (sente/make-channel-socket-client! duplex-route/duplex-uri
                                                                                   csrf-frontend/?csrf-token
                                                                                   {:type :auto ; e/o #{:auto
                                                                                                ; :ajax :ws}
                                                                                    :packer :edn})]
       (def chsk chsk)
       (def ch-chsk ch-recv)    ; ChannelSocket's receive channel
       (def chsk-send! send-fn) ; ChannelSocket's send API fn
       (def chsk-state state)   ; Watchable, read-only atom
     )
     (catch js/Object e (log/error (ex-info "Unexpected error during realtime component init" {:error e}))))

(defn get-chsk [] chsk)
