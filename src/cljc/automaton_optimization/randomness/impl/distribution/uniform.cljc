(ns automaton-optimization.randomness.impl.distribution.uniform
  "An uniform distribution, returning a double between `a` and `b`."
  (:require
   [automaton-optimization.maths                         :as opt-maths]
   [automaton-optimization.randomness.distribution       :as opt-proba-distribution]
   [automaton-optimization.randomness.impl.prng.stateful :as opt-prng-stateful]))

(defrecord Uniform [prng a b width]
  opt-proba-distribution/Distribution
    (draw [_] (opt-prng-stateful/as-double prng a b))
    (median [_] (/ (+ a b) 2.0))
    (cumulative [_ p]
      (cond
        (<= p a) 0.0
        (>= p b) 1.0
        :else (/ (- p a) width)))
    (minimum [_] (reduce opt-maths/min [a b]))
    (maximum [_] (reduce opt-maths/max [a b]))
    (quantile [_ p]
      (cond
        (zero? p) a
        (= p 1.0) b
        :else (+ a (* p width)))))

(defn make [prng a b] (when (every? number? [a b]) (->Uniform prng a b (double (- b a)))))
