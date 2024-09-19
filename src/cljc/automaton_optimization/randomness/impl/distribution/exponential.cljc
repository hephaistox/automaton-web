(ns automaton-optimization.randomness.impl.distribution.exponential
  "Exponential distribution based on [inversion method](https://en.wikipedia.org/wiki/Inverse_transform_sampling).

  See the [wiki article](https://en.wikipedia.org/wiki/Exponential_distribution)."
  (:require
   [automaton-optimization.maths                         :as opt-maths]
   [automaton-optimization.randomness.distribution       :as opt-proba-distribution]
   [automaton-optimization.randomness.impl.prng.stateful :as opt-prng-stateful]))

(defrecord Exponential [prng rate]
  opt-proba-distribution/Distribution
    (draw [_] (/ (- (opt-maths/log (opt-prng-stateful/as-double prng 0.0 1.0))) rate))
    (median [_] (/ (- (opt-maths/log 0.5)) rate))
    (cumulative [_ p] (- 1.0 (opt-maths/exp (- (* rate p)))))
    (minimum [_] 0)
    (maximum [_] opt-maths/infinity)
    (quantile [_ x] (/ (- (opt-maths/log (- 1.0 x))) rate)))

(defn make
  "Generates an exponential distribution based on `prng` with rate `rate`."
  [prng rate]
  (->Exponential prng rate))
