(ns automaton-optimization.randomness.impl.sample.vector
  "A sample data stored in memory - a vector."
  (:require
   [automaton-optimization.randomness.number-set :as opt-number-set]
   [automaton-optimization.randomness.sample     :as opt-sample-data]))

(defrecord SampleVector [v]
  opt-sample-data/SampleData
    (average [_] (opt-number-set/average v))
    (n [_] (count v))
    (median [_] (opt-number-set/median v))
    (variance [_] (opt-number-set/variance v)))

(defn make "Returns sample `v`." [v] (->SampleVector v))
