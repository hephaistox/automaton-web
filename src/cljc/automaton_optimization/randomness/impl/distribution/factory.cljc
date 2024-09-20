(ns automaton-optimization.randomness.impl.distribution.factory
  "Factory to create the distribution.")

(defn build
  "Creates the distribution `distribution-name` leveraging the `prng`.
  The distribution will be found in the `registry`.
  As most of distribution needs parameters, they will be found in the `law-parameters`."
  [registry distribution-name prng law-parameters]
  (when-let [law (get registry distribution-name)] (law prng law-parameters)))
