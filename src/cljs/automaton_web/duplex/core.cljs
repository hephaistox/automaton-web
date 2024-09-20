(ns automaton-web.duplex.core
  (:require
   [automaton-core.log                   :as core-log]
   [automaton-web.duplex.route           :as duplex-route]
   [automaton-web.security.csrf-frontend :as csrf-frontend]
   [taoensso.sente                       :as sente]))

(if csrf-frontend/?csrf-token
  (core-log/info "Init of realtime component, token is:" csrf-frontend/?csrf-token)
  (core-log/error (ex-info "Fail during realtime component, CSRF token not found"
                           {:cause "?csrf-token does not exist"
                            :data csrf-frontend/?csrf-token})))

(try (let [{:keys [chsk ch-recv send-fn state]} (sente/make-channel-socket-client!
                                                 duplex-route/duplex-uri
                                                 csrf-frontend/?csrf-token
                                                 {:type :auto ; e/o #{:auto
                                                              ; :ajax :ws}
                                                  :packer :edn})]
       (def chsk chsk)
       (def ch-chsk ch-recv)    ; ChannelSocket's receive channel
       (def chsk-send! send-fn) ; ChannelSocket's send API fn
       (def chsk-state state)   ; Watchable, read-only atom
     )
     (catch js/Object e
       (core-log/error (ex-info "Unexpected error during realtime component init" {:error e}))))

(defn get-chsk [] chsk)
