(ns automaton-optimization.randomness.impl.prng.stateful
  "A stateful prng definition.

  Statefulness is important to ensure:
  * Each call to `rnd` is modifying the state of the prng.
  * it is thread safe to use."
  (:refer-clojure :exclude [rnd])
  (:require
   [automaton-optimization.maths :as opt-maths]
   [cljc-long.core]))

(defprotocol PRNG
  (duplicate [_]
   "Duplicates this prng to a new one, starting at the seed value.")
  (jump [_]
   "Jump to a completly different place.")
  (rnd-range [_]
   "Returns the range in which rnd is returned.")
  (seed [_]
   "Returns the seed of the random number generator.")
  (reset [_]
   "Returns a prng that starts again at the seed value.")
  (rnd [_]
   "Returns a random number and change the state of the prng so next call will return a new value."))

(defn as-int
  "Returns an integer generated with `prng` between `[min-int; max-int[`."
  [prng min-int max-int]
  (let [rnd-value (rnd prng)] (+ min-int (mod rnd-value (- max-int min-int)))))

(defn as-double
  "Returns a double generated with `prng` between `[min-double, max-double[`."
  [prng min-double max-double]
  (let [rnd-value (rnd prng)]
    (+ min-double
       (* (opt-maths/long->unit-double (cljc-long.core/long rnd-value))
          (- max-double min-double)))))

(defn draw-ints
  "Draw `n` random integers with `prng`, between `[min-int; max-int[`."
  [prng n min-int max-int]
  (repeatedly n #(as-int prng min-int max-int)))
