(ns automaton-web.i18n.dictionary-test
  (:require #?(:clj [clojure.test :refer [deftest is testing]]
               :cljs [cljs.test :refer [deftest is testing] :include-macros true])
            [automaton-web.i18n.dict.text :as sut]
            [automaton-core.i18n.missing-translation-report :as b-language]
            [automaton-web.i18n.language :as web-language]
            [clojure.set :as set]
            [clojure.string :as str]))

(deftest dictionary-test
  (testing (apply str
                  "Dictionary is matching all expected languages. List all languages to expect: "
                  (str/join " " web-language/get-web-languages-ids))
    (is (= [] (b-language/key-with-missing-languages sut/dict web-language/get-web-languages-ids #{:tongue/missing-key}))))
  (testing "All languages are known languages" (is (empty? (set/difference (set (keys sut/dict)) web-language/get-web-languages-ids)))))
