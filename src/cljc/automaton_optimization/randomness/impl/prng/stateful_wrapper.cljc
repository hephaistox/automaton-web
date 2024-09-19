(ns automaton-optimization.randomness.impl.prng.stateful-wrapper
  "Creates a stateful prng based on a stateless one."
  (:require
   [automaton-optimization.randomness.impl.prng.stateful  :as opt-prng-stateful]
   [automaton-optimization.randomness.impl.prng.stateless :as opt-prng-stateless]))

(declare make)

(defrecord PrngStatefulImpl [stateless-prng original-prng]
  opt-prng-stateful/PRNG
    (duplicate [_] (make original-prng))
    (jump [_] (swap! stateless-prng opt-prng-stateless/jump))
    (rnd-range [_] (opt-prng-stateless/rnd-range @stateless-prng))
    (seed [_] (opt-prng-stateless/seed @stateless-prng))
    (reset [_] (reset! stateless-prng original-prng))
    (rnd [_]
      (let [val (opt-prng-stateless/peek-rnd @stateless-prng)]
        (swap! stateless-prng opt-prng-stateless/next)
        val)))

(defn make [stateless-prng] (->PrngStatefulImpl (atom stateless-prng) stateless-prng))
