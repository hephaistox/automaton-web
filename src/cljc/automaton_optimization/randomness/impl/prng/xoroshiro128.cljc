(ns automaton-optimization.randomness.impl.prng.xoroshiro128
  "PRNG stateless implementation based on `xoroshoshiro128` algorithm.

  See [xoroshiro128 repo](https://github.com/thedavidmeister/xoroshiro128] for more details."
  (:refer-clojure :exclude [next])
  (:require
   [automaton-optimization.maths                                 :as opt-maths]
   [automaton-optimization.randomness.impl.prng.stateful-wrapper :as opt-stateful-wrapper]
   [automaton-optimization.randomness.impl.prng.stateless        :as opt-prng-stateless]
   [xoroshiro128.core                                            :as xoro]))

(defrecord Xoroshiro128 [xoro-object]
  opt-prng-stateless/PRNGStateless
    (jump [this] (update this :xoro-object xoro/jump))
    (rnd-range [_] [opt-maths/negative-infinity-long opt-maths/infinity-long])
    (seed [_] (xoro/seed xoro-object))
    (peek-rnd [_] (xoro/value xoro-object))
    (next [this] (update this :xoro-object xoro/next)))

(defn make-stateless
  ([seed] (->Xoroshiro128 (xoro/xoroshiro128+ seed)))
  ([]
   (->Xoroshiro128 (xoro/xoroshiro128+ #?(:cljs (.now js/Date)
                                          :clj (. System (currentTimeMillis)))))))

(defn make
  ([seed] (opt-stateful-wrapper/make (make-stateless seed)))
  ([] (opt-stateful-wrapper/make (make-stateless))))
