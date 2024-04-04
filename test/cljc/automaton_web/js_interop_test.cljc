(ns automaton-web.js-interop-test
  (:require
   [automaton-web.js-interop :as sut]
   [clojure.test             :refer [deftest is testing]]))

(deftest mapToJSmap
  (testing "Empty map" (is (= "{}" (sut/mapToJSmap {}))))
  (testing "String map"
    (is (= "{\"foo\": \"bar\"}" (sut/mapToJSmap {:foo "bar"}))))
  (testing "keyword with minus"
    (is (= "{\"is_foo\": \"bar\"}" (sut/mapToJSmap {:is-foo "bar"}))))
  (testing "Integer value" (is (= "{\"foo\": 1}" (sut/mapToJSmap {:foo 1}))))
  (testing "keyword value"
    (is (= "{\"foo_from\": \"bar_to\"}" (sut/mapToJSmap {:foo-from :bar-to}))))
  (testing "Multiple values"
    (is (= "{\"foo_from\": \"bar_to\",\n\"foo\": 1,\n\"bar\": \"foo\"}"
           (sut/mapToJSmap {:foo-from :bar-to
                            :foo 1
                            :bar "foo"}))))
  (testing "Nested maps"
    (is
     (=
      "{\"foo\": {\"bar\": \"hello\"},\n\"a\": \"b\",\n\"three\": {\"two\": {\"one\": \"Even more nested!\"},\n\"one\": \"i'm nested\"}}"
      (sut/mapToJSmap {:foo {:bar "hello"}
                       :a "b"
                       :three {:two {:one "Even more nested!"}
                               :one "i'm nested"}})))))
