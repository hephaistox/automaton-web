(ns automaton-web.persistence.cookies
  (:require
   [goog.net.Cookies :as gnc]))

(defn ^:export set-cookie [{:keys [k v time]
                            :or {time -1}}]
  (.set (gnc/getInstance) k v time))
