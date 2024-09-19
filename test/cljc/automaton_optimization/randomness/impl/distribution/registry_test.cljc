(ns automaton-optimization.randomness.impl.distribution.registry-test
  (:require
   [automaton-core.adapters.schema                               :as core-schema]
   #?@(:clj [[clojure.test :refer [deftest is]]]
       :cljs [[cljs.test :refer [deftest is] :include-macros true]])
   [automaton-optimization.randomness.impl.distribution.registry :as sut]))

(deftest registry-test
  (is (nil? (core-schema/validate-humanize (sut/schema))))
  (is (nil? (core-schema/validate-data-humanize (sut/schema) (sut/registry)))))

(comment
  ;;To generate the list of possible parameters for the documentation.
  (->> (sut/registry)
       keys
       (map #(str "* `" % "`\n"))
       (apply println))
  ;
)
