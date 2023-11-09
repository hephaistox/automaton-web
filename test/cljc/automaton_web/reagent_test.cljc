(ns automaton-web.reagent-test
  (:require [automaton-web.reagent :as sut]
            #?(:clj [clojure.test :refer [deftest is testing]]
               :cljs [cljs.test :refer [deftest is testing] :include-macros true])))

(deftest add-opt-test
  (testing "Existing map is updated"
    (is (= [:div
            {:foo :bar
             :bar :foo} "this" "is" "expected"]
           (sut/add-opt [:div {:foo :bar} "this" "is" "expected"] :bar :foo))))
  (testing "Existing map is updated, even with multiple values"
    (is (= [:div
            {:foo :bar
             :bar :foo
             :bar2 :foo2} "this" "is" "expected"]
           (sut/add-opt [:div {:foo :bar} "this" "is" "expected"] :bar :foo :bar2 :foo2))))
  (testing "It's possible to have no trailing data"
    (is (= [:div
            {:foo :bar
             :bar :foo}]
           (sut/add-opt [:div {:foo :bar}] :bar :foo))))
  (testing "Option map is inserted if it does not exist already" (is (= [:div {:bar :foo}] (sut/add-opt [:div] :bar :foo))))
  (testing "Option map is inserted with multiple values even if it does not already exist"
    (is (= [:div
            {:bar :foo
             :bar2 :foo2
             :bar3 :foo3}]
           (sut/add-opt [:div] :bar :foo :bar2 :foo2 :bar3 :foo3))))
  (testing "Option map is inserted if it does not exist already even if trailing data exists"
    (is (= [:div {:bar :foo} "Am" "I" "still" "here?"] (sut/add-opt [:div "Am" "I" "still" "here?"] :bar :foo)))))

(deftest reagent-option
  (testing "return an empty map if it is not present"
    (is (= {} (sut/reagent-option []) (sut/reagent-option [:option]) (sut/reagent-option [:option "ho" "ho"]))))
  (testing "return the option map if it exists"
    (is (= {:foo :bar} (sut/reagent-option [:option {:foo :bar}]) (sut/reagent-option [:option {:foo :bar} "ho" "ho"])))))

(deftest update-reagent-options-test
  (testing "Component with no options now have some" (is (= [:div {:foo :bar}] (sut/update-reagent-options {:foo :bar} [:div]))))
  (testing "Component with no options now have some, and preserve one following argument"
    (is (= [:div {:foo :bar} "foo"] (sut/update-reagent-options {:foo :bar} [:div "foo"]))))
  (testing "Component with no component now have some, and preserve two followings arguments"
    (is (= [:div {:foo :bar} "foo" "bar"] (sut/update-reagent-options {:foo :bar} [:div "foo" "bar"]))))
  (testing "Component is replaced" (is (= [:div {:foo :bar}] (sut/update-reagent-options {:foo :bar} [:div {:foo2 :bar2}]))))
  (testing "Options of a component are replaced, and preserve one following argument"
    (is (= [:div {:foo :bar} "foo"] (sut/update-reagent-options {:foo :bar} [:div {:foo2 :bar2} "foo"])))))
