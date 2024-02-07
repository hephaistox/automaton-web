(ns automaton-web.configuration.protocol)

(defprotocol ConfWeb
  (config-web-reference [this]
   "Return the reference to communicate config between backend/frontend"))
