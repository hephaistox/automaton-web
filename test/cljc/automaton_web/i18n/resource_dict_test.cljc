(ns automaton-web.i18n.resource-dict-test
  (:require #?(:clj [clojure.test :refer [deftest is testing]]
               :cljs [cljs.test :refer [deftest is testing] :include-macros true])
            [automaton-web.i18n.dict.resources :as sut]
            [automaton-web.i18n.language :as web-language]
            [automaton-core.i18n.missing-translation-report :as b-language]
            [clojure.set :as set]
            [clojure.string :as str]))

(deftest dictionary-test
  (testing (apply str
                  "Dictionary is matching all expected languages. List of languages, expect: "
                  (str/join " " web-language/get-web-languages-ids))
    (is (= [] (b-language/key-with-missing-languages sut/dict web-language/get-web-languages-ids #{:tongue/missing-key}))))
  (testing "All languages are known languages" (is (empty? (set/difference (set (keys sut/dict)) web-language/get-web-languages-ids)))))
