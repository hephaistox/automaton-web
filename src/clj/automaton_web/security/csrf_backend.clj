(ns automaton-web.security.csrf-backend
  (:require
   [ring.middleware.anti-forgery :as ring-anti-forgery]))

(defn anti-forgery-html-token
  []
  [:div {:name "__anti-forgery-token"
         :id "__anti-forgery-token"
         :anti-forgery-token (force ring-anti-forgery/*anti-forgery-token*)
         :class ["hidden"]}])
