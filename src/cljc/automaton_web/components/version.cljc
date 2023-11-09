(ns automaton-web.components.version
  (:require [automaton-web.components.icons :as bci]))

(defn component
  "Show versions"
  [{:keys [version dark?]}]
  [:div {:class ["inline-block absolute right-0"]}
   [bci/icon
    {:size 2
     :path-kw :svg/bug
     :dark? dark?}] [:span {:class ["text-xs" (if dark? "text-theme-light" "text-theme-dark")]} version]])
