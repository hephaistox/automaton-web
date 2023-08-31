(ns automaton-web.i18n.dictionary-test
  (:require [automaton-web.i18n.dictionary :as sut]
            [automaton-web.i18n.language :as b-language]
            #?(:clj [clojure.test :refer [deftest is testing]]
               :cljs [cljs.test :refer [deftest is testing] :include-macros true])))

(deftest automaton-dictionary
  (testing "Dictionary is matching all expecting languages, list all languages, expect en fr"
    (is (= []
           (b-language/key-with-missing-languages sut/automaton-dictionary
                                                  #{:en :fr}
                                                  #{:tongue/missing-key})))))
