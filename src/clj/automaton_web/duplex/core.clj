(ns automaton-web.duplex.core
  "Add a real time communication feature with the client"
  (:require [automaton-web.configuration :as web-conf]
            [automaton-core.log :as core-log]
            [automaton-web.duplex.message-handler :as message-handler]
            [mount.core :refer [defstate] :as mount]
            [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit :refer [get-sch-adapter]]))

(defn start-rt
  [debug?]
  (let [{:keys [ch-recv]
         :as sente-map}
        (sente/make-channel-socket! (get-sch-adapter) {:user-id-fn (constantly 123456)})
        server (sente/start-server-chsk-router! ch-recv message-handler/event-msg-handler)]
    (when (= :development debug?) (sente/set-min-log-level! :warn))
    (assoc sente-map :server server)))

(defstate
 duplex-server
 "Contains a map with the following keys:
* ajax-get-or-ws-handshake-fn
* ajax-post-fn
* ch-recv - Receive channel
* connected-uids - Watchable, read-only atom
* send-fn - ChannelSocket's send API fn
* server is the sente server, useful to stop it especially

For an example see https://github.com/ptaoussanis/sente/tree/master/example-project/src/example"
 :start (try (core-log/info "Starting duplex server component")
             (let [res (start-rt (web-conf/read-param [:env]))]
               (core-log/trace "Duplex server component is started")
               res)
             (catch Exception e (core-log/error "Unexpected error during duplex start" e)))
 :stop (when @duplex-server (when-let [server (:server @duplex-server)] (server))))

(defn ajax-get-or-ws-handshake
  "Is the handshake handler"
  []
  (if-let [afn (:ajax-get-or-ws-handshake-fn @duplex-server)]
    afn
    (throw (ex-info "Duplex server is not started" {}))))

(defn ajax-post
  "Post a message in ajax mode"
  []
  (if-let [afn (:ajax-post-fn @duplex-server)]
    afn
    (throw (ex-info "Duplex server is not started" {}))))

(defn ch-recv
  "Receive a message handler"
  []
  (if-let [afn (:ch-recv @duplex-server)]
    afn
    (throw (ex-info "Duplex server is not started" {}))))

(defn connected-uids
  "List of all connected clients"
  []
  (if-let [afn (:connected-uids @duplex-server)]
    afn
    (throw (ex-info "Duplex server is not started" {}))))

(defn send-fn
  "Send a message handler"
  [& args]
  (if-let [afn (:send-fn @duplex-server)]
    (apply afn args)
    (throw (ex-info "Duplex server is not started" {}))))

