(ns automaton-web.i18n.be.translator.tempura-test
  (:require
   [automaton-web.i18n.be.translator         :as be-translator]
   [automaton-web.i18n.be.translator.tempura :as sut]
   [automaton-web.i18n.dict.text             :as web-dict-text]
   [clojure.test                             :refer [deftest is testing]]))

(defn handler-stub
  [request]
  {:answer? true
   :request request})

(def default-language [:en])

(def temp-translator
  (sut/make-tempura-be-translator default-language
                                  web-dict-text/dict
                                  {:en {:foo "foo-en"}
                                   :fr {:foo "foo-fr"}}))

(deftest wrap-ring-request-test
  (let [request {:tempura/tr :id
                 :tempura/accept-langs_ :accept}
        handler (be-translator/wrap-ring-request temp-translator handler-stub)]
    (testing "Keys are set"
      (is (= #{:accept-langs :tr :locales}
             (-> (handler request)
                 :request
                 keys
                 set))))
    (testing "Added tr key is working"
      (is (= #{:accept-langs :tr :locales}
             (-> (handler request)
                 :request
                 keys
                 set))))))

(deftest default-languages-test
  (testing "Default language is found"
    (is (= default-language
           (be-translator/default-languages temp-translator)))))
(deftest get-middleware-test
  (testing "Middleware has the expected keys"
    (is (= #{:tr :accept-langs :locales}
           (let [wrapper (be-translator/wrap-translator temp-translator)]
             (-> ((wrapper handler-stub) {})
                 :request
                 keys
                 set)))))
  (testing "Test translation based on request"
    (is (=
         "foo-en"
         (be-translator/translate-based-on-request temp-translator {} :foo [])))
    (is (= "foo-fr"
           (be-translator/translate-based-on-request
            temp-translator
            {:headers {"host" "http://www.testify.com?lang=en"}
             :locales [:fr]}
            :foo
            [])))))
