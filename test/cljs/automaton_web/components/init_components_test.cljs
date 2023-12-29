(ns automaton-web.components.init-components-test
  (:require [automaton-web.components.init-components :as sut]
            [cljs.test :refer [deftest is testing] :include-macros true]
            [goog.object]))

(defn err->edn [e] (into {} (map (fn [k] [(keyword k) (js->clj (goog.object/get e k))])) (.getOwnPropertyNames js/Object e)))

(deftest modal-element-initialized
  (comment
    (testing "Modal element is returned"
      (is (= [:_focustrap :_ignoreBackdropClick :_backdrop :_scrollBar :_config :_classes :_isTransitioning :_dialog :_isShown]
             (keys (err->edn (sut/init-modal [:div]))))))))
