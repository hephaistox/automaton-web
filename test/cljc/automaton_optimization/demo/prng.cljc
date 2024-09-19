(ns automaton-optimization.demo.prng
  "Demo of `prng` use for the documentation."
  {:clj-kondo/config '{:linters {:duplicate-require {:level :off}}}}
  (:require
   [automaton-optimization.randomness :as opt-randomness]))

;; Quick start 1
(require '[automaton-optimization.randomness :as opt-randomness])
(opt-randomness/draw :uniform
                     {:a 12
                      :b 15})

;; Quick start 2
(require '[automaton-optimization.randomness :as opt-randomness])
(def ud
  (opt-randomness/build :uniform
                        {:a 12
                         :b 15}))
(opt-randomness/draw ud)
(opt-randomness/draw ud)
(opt-randomness/draw ud)

;; Quick Start 3
(require '[automaton-optimization.randomness :as opt-randomness])
(-> (opt-randomness/xoroshiro128)
    opt-randomness/rnd)

;; Quick start 4
(require '[automaton-optimization.randomness :as opt-randomness])
(let [x (-> (opt-randomness/distribution-registry)
            (opt-randomness/build (opt-randomness/xoroshiro128)
                                  :uniform
                                  {:a 12
                                   :b 15}))]
  [(opt-randomness/draw x) (opt-randomness/draw x) (opt-randomness/draw x)])
