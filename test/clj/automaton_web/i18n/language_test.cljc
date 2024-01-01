(ns automaton-web.i18n.language-test
  (:require [automaton-core.adapters.regexp :as core-regexp]
            [automaton-web.i18n.language :as sut]
            [clojure.test :refer [deftest is testing]]))

(def selected-languages "For test, all supported languages are tested" (sut/make-automaton-web-languages))

(deftest cors-domain-routes-test
  (testing "routes are built"
    (is (= #{".*heph.com$" ".*heph.fr$"}
           (->> (sut/cors-domain-routes (sut/make-automaton-web-languages {:en {}
                                                                           :fr {}})
                                        "heph")
                (map core-regexp/stringify)
                set)))))

(deftest create-ui-languages-test
  (testing "Options creation"
    (let [options (sut/create-ui-languages selected-languages)]
      (is (= 2 (count options)))
      (is (= [{:name "fr"
               :id :fr
               :value "FR"}
              {:name "en"
               :value "EN"
               :id :en}]
             (sut/create-ui-languages selected-languages))))))

(deftest ui-str-to-id-test
  (testing "Select languages are matching"
    (is (= :en (sut/ui-str-to-id selected-languages "EN")))
    (is (= :fr
           (sut/ui-str-to-id selected-languages "fr")
           (sut/ui-str-to-id selected-languages "FR")
           (sut/ui-str-to-id selected-languages "Fr")))
    (is (nil? (sut/ui-str-to-id selected-languages "AS")))))
