(ns automaton-web.persistence.cookies
  "Parse cookies data
  Could be used on both backend and frontend side to analyze the content of a cookie"
  (:require
   [automaton-core.log :as core-log]
   [clojure.string     :as str]))

(defn parse-cookie
  "Parse the content of the cookie
  Params:
  * `cookie-text` is the cookie text content to transform in data structure"
  [cookie-text]
  (let [cookie-data (when (and cookie-text (not= "" cookie-text))
                      (into {}
                            (map (fn [line]
                                   (vec (let [res (->> (str/split line #"=")
                                                       (map str/trim))]
                                          (case (count res)
                                            1 [res nil]
                                            2 res
                                            [(first res) (vec (rest res))]))))
                                 (str/split cookie-text #";"))))]
    (core-log/trace "Cookie parsed: " cookie-data)
    cookie-data))
