(ns automaton-web.i18n.be.translator-test
  (:require [automaton-web.i18n.be.translator :as sut]
            [automaton-web.i18n.be.translator.tempura :as be-tempura-translator]
            [clojure.test :refer [deftest is testing]]))

(def web-translator (be-tempura-translator/make-tempura-be-translator [:sk]))

(def fd "Faulty delay, meant to check it is not evaluted" (delay (throw (ex-info "Should not happen" {}))))

(def nd "Wait for it: Null delay, return delay, but wait for a delay" (delay nil))

(deftest language-choice-strategy*-test
  (testing "language in path parameters is higher priority" (is (= :en (sut/language-choice-strategy* :en fd fd fd fd))))
  (testing "language in cookies is higher priority" (is (= :en (sut/language-choice-strategy* nil (delay :en) fd fd fd))))
  (testing "language in tld is higher priority" (is (= :en (sut/language-choice-strategy* nil nd (delay :en) fd fd))))
  (testing "main-lang is low priority" (is (= :en (sut/language-choice-strategy* nil nd nd (delay :en) fd))))
  (testing "main-lang is low priority" (is (= :en (sut/language-choice-strategy* nil nd nd nd (delay :en))))))

(deftest language-choice-strategy-def-test
  (testing "If parameter is given, this is the priority"
    (is (= "fr" (sut/lang-str-choice-strategy-def web-translator {:params {:lang "fr"}}))))
  (testing "If no information is found at all, return default language" (is (= :sk (sut/lang-str-choice-strategy-def web-translator {}))))
  (testing "Test cookies accepted language is used"
    (is (= "fr" (sut/lang-str-choice-strategy-def web-translator {:headers {"accept-language" "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7"}}))))
  (testing "Cookies language are skipped if unknown"
    (is (= "pl" (sut/lang-str-choice-strategy-def web-translator {:headers {"accept-language" "pl-PL,fr;q=0.9,en-US;q=0.8,en;q=0.7"}}))))
  (testing "Tld language is used" (is (= "en" (sut/lang-str-choice-strategy-def web-translator {:headers {"host" "hephaistox.en"}})))))
