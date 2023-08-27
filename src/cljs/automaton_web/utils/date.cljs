(ns automaton-web.utils.date
  "Utility function for date management"
  (:require [cljs-time.core :as cljs-time]))

(defn this-year
  ([date]
   (cljs-time/year date))
  ([]
   (this-year (cljs-time/now))))
