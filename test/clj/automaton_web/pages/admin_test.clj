(ns automaton-web.pages.admin-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [automaton-web.pages.admin :as sut]))

(def graph
  "Graph for test purposes"
  (list {:name "#'automaton-core.configuration/conf-state"
         :order 1
         :status #{:started}
         :deps #{}}
        {:name "#'optor.user/web-server"
         :order 2
         :status #{:started}
         :deps #{}}
        {:name "#'caumond-sasu.core/web-server"
         :order 3
         :status #{:stopped}
         :deps #{}}
        {:name "#'caumond-sasu.user/web-server"
         :order 4
         :status #{:stopped}
         :deps #{}}
        {:name "#'optor.core/web-server"
         :order 5
         :status #{:stopped}
         :deps #{}}))

(deftest component-graph-deps
  (testing "Component graph"
    (is
     (= [{:name "#'automaton-core.configuration/conf-state"
          :status #{:started}
          :status-color :green
          :deps #{}}
         {:name "#'optor.user/web-server"
          :status #{:started}
          :status-color :green
          :deps #{}}
         {:name "#'caumond-sasu.core/web-server"
          :status #{:stopped}
          :status-color :red
          :deps #{}}
         {:name "#'caumond-sasu.user/web-server"
          :status #{:stopped}
          :status-color :red
          :deps #{}}
         {:name "#'optor.core/web-server"
          :status #{:stopped}
          :status-color :red
          :deps #{}}]
        (sut/component-graph-deps graph)))))
