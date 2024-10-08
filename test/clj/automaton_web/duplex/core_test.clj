(ns automaton-web.duplex.core-test
  (:require
   [automaton-web.duplex.core :as sut]
   [clojure.test              :refer [deftest is testing]]
   [mount.core                :as mount]))

(deftest server-test
  (testing "mocking realtime server"
    (let [_ (with-out-str (mount/start-with-states {#'automaton-web.duplex.core/duplex-server
                                                    {:start (partial sut/start-rt nil)}}))]
      (testing "Realtime server has an ajax-get-or-ws-handshake-fn"
        (is (fn? (sut/ajax-get-or-ws-handshake))))
      (testing "Realtime server has an ajax-post" (is (fn? (sut/ajax-post))))
      (testing "Realtime server has a ch-recv" (is (sut/ch-recv)))
      (testing "Realtime server has a connected-uids"
        (is (= clojure.lang.Atom (type (sut/connected-uids)))))
      (testing "Realtime server has a send-fn" (is (not (sut/send-fn 666 [:foo/bar]))))
      (with-out-str (mount/stop)))))
