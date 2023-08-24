(ns automaton-web.security.csrf-backend
  (:require
   [ring.middleware.anti-forgery :as anti-forgery]))

(defn anti-forgery-html-token []
  [:div {:name  "__anti-forgery-token"
         :id    "__anti-forgery-token"
         :anti-forgery-token (force anti-forgery/*anti-forgery-token*)
         :class ["hidden"]}])
