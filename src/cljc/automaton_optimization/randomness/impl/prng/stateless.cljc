(ns automaton-optimization.randomness.impl.prng.stateless
  "Implements PRNG without state.

  Use this protocol to wrap some stateless implementations of a `prng`.
  To use prng implementing protocol, wraps it again with `PrngStatefulImpl`."
  (:refer-clojure :exclude [next]))

(defprotocol PRNGStateless
  (jump [_]
   "Jump to a completly different place.")
  (rnd-range [_]
   "Returns the range in which rnd is returned.")
  (seed [_]
   "Returns the seed of the random number generator.")
  (peek-rnd [_]
   "Peek the next random value without changing the prng.")
  (next [_]
   "Returns a new value of prng so next `peek-rnd` will return a new value."))
