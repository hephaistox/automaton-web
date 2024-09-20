(ns automaton-optimization.randomness.impl.distribution.uniform-integer
  "An uniform distribution of integers."
  (:require
   [automaton-optimization.maths                         :as opt-maths]
   [automaton-optimization.randomness.distribution       :as opt-proba-distribution]
   [automaton-optimization.randomness.impl.prng.stateful :as opt-prng-stateful]))

(defrecord UniformInteger [prng a b]
  opt-proba-distribution/Distribution
    (draw [_] (opt-prng-stateful/as-int prng a b))
    (median [_] (/ (+ a b) 2))
    (cumulative [_ p]
      (cond
        (<= p a) 0.0
        (>= p b) 1.0
        :else (/ (- p a) (- b a))))
    (minimum [_] (reduce opt-maths/min [a b]))
    (maximum [_] (reduce opt-maths/max [a b]))
    (quantile [_ p]
      (cond
        (neg? p) a
        (= p 1.0) b
        :else (+ a (* p (- b a))))))

(defn make [prng a b] (->UniformInteger prng a b))
