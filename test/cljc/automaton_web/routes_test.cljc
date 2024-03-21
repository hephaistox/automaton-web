(ns automaton-web.routes-test
  (:require
   [automaton-web.routes :as sut]
   #?(:clj [clojure.test :refer [deftest is testing]]
      :cljs [cljs.test :refer [deftest is testing] :include-macros true])))

(deftest parse-route-elt-test
  (testing "Strings are preserved" (is (= "foo" (sut/parse-routes :be "foo"))))
  (testing
    "If be or fe data isn't a map, so copy it directly (usefull to pass handlers directly)"
    (is (= 'clojure.print
           (sut/parse-routes :be
                             {:name :foo
                              :be 'clojure.print}))))
  (testing "If be or fe are a map, copy it with `:name` attribute appended"
    (is (= {:name ::root
            :get clojure.core/print}
           (sut/parse-routes :be
                             {:name ::root
                              :be {:get :html-page/index}
                              :fe {:panel-id :panels/home}}
                             {:html-page/index clojure.core/print})))
    (is (= {:name ::root
            :panel-id :panels/home}
           (sut/parse-routes :fe
                             {:name ::root
                              :be {:page-id :html-page/index}
                              :fe {:panel-id :panels/home}}))))
  (testing "Vectors values are treated"
    (is (= ["foo" "bar"] (sut/parse-routes :be ["foo" "bar"]))))
  (testing "Registry replace keywords"
    (is (= ["" clojure.core/last]
           (sut/parse-routes :be ["" {:be :foo}] {:foo clojure.core/last}))))
  (testing "Routes registry index-metadata is included when creating index"
    (is (= {:some "data"
            :here "important"}
           ((:get (sut/parse-routes :be {:name ::root
                                         :be {:get :html-page/custom-index
                                              :index-metadata {:some "data"
                                                               :here "important"}}
                                         :fe {:panel-id :panels/home}}
                                    {:html-page/custom-index (fn [x _] x)}))
            {})))))
