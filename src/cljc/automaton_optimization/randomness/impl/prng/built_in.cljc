(ns automaton-optimization.randomness.impl.prng.built-in
  "PRNG implementation built-in in your platform."
  (:refer-clojure :exclude [next])
  (:require
   [automaton-core.utils.call-limit                      :as core-call-limit]
   [automaton-optimization.maths                         :as opt-maths]
   [automaton-optimization.randomness.impl.prng.stateful :as opt-prng-stateful]))

(defrecord Builtin [seed]
  opt-prng-stateful/PRNG
    (duplicate [_] (throw (ex-info "Not implemented." {})))
    (jump [_] (repeatedly 100 rand))
    (rnd-range [_] [opt-maths/negative-infinity-long opt-maths/infinity-long])
    (reset [_]
      (throw
       (ex-info
        "Not implemented. Leverage another prng or implement https://github.com/trystan/random-seed"
        {})))
    (rnd [_] (rand))
    (seed [_] seed))

(defn make
  ([seed] (core-call-limit/allow-one-only-call #(->Builtin seed) ::are-global))
  ([] (core-call-limit/allow-one-only-call #(->Builtin (rand-int 1000000)) ::are-global)))
