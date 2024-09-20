(ns automaton-optimization.randomness.sample
  "Contains a list of numerical data

  It could be whatever implements the operators: +, *, /, -, 0"
  (:refer-clojure :exclude [count]))

(defprotocol SampleData
  (average [_]
   "Average of the sample")
  (n [_]
   "Count the number of elements")
  (median [_]
   "Median of the sample")
  (variance [_]
   "Variance of the sample"))
