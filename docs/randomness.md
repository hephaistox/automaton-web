# Randomness
A Clojure library to manage the Randomness concept for optimization problems.

Note: All the examples can be found in the `test` sub-directory under `automaton-optimization.demo.prng` namespace.

## Quick start
You can draw simply one number from a given distribution: 
```clojure
(require '[automaton-optimization.randomness :as opt-randomness])
(opt-randomness/draw :uniform
                     {:a 12
                      :b 15})
```
The `draw` function will generate a number according to the `:uniform` distribution and the parameters.
Refer to the Reference section for more details on possible distribution and their required parameters.

If you need more than one drawing, you will prefer to create the distribution once, so the `prng` properties will be kept and memory and cpu performance will be improved.
```clojure
(require '[automaton-optimization.randomness :as opt-randomness])
(def ud
  (opt-randomness/build :uniform
                       {:a 12
                        :b 15}))
(opt-randomness/draw ud) ;;12.164821013036297
(opt-randomness/draw ud) ;;13.81793789260508
(opt-randomness/draw ud) ;;12.947625088830756
;;Note that values will be different for you as this form depends on execution time.
```

There can directly generate a prng and call it directly.
```clojure
(require '[automaton-optimization.randomness :as opt-randomness])
(-> (opt-randomness/xoroshiro128)
    opt-randomness/rnd)
```

And leverage it in the distribution.
```clojure
(require '[automaton-optimization.randomness :as opt-randomness])
(let [x (-> (opt-randomness/distribution-registry)
            (opt-randomness/build (opt-randomness/xoroshiro128)
                                  :uniform
                                  {:a 12
                                   :b 15}))]
  [(opt-randomness/draw x)
   (opt-randomness/draw x)
   (opt-randomness/draw x)])
```

However some numbers have been generated, you can store them in `samples`.

# Reference
This section details the features of the randomness feature. The lib offers three main features:
* PRNG - The generation of pseudo-random numbers, 
* Distribution - Some probability distributions that are leveraging PRNG to generate various random variables that can be leveraged to generate one or more values.
* Samples - The `sample` of random variables - that can be fed by the distribution but also created in other manners - on which statistics analysis could be carried out.

## PRNG (i.e. Pseudo-Random Number Generators)
Pseudo-random number generators simulate the generation of random phenomenons. The `automaton-optimization` library is shipped with some already implemented prng :
* `xoroshiro128` which is a proxy for the excellent `xoroshito` library.
   ```clojure
(require '[automaton-optimization.randomness.impl.prng.xoroshiro128 :as opt-prng-xoro]
         '[automaton-optimization.randomness.prng :as opt-prng])
(-> (opt-prng-xoro/make)
    opt-prng/rnd)
   ```
* `built-in` which leverages the `prng` shipped with the platform executing your Clojure code (java, javascript, ...).
A test is not added for that one as it should not be called more than once, as only one global instance exists. An exception is thrown if you however try to call it twice.

The prng are all shipped with two arities, one with one seed as a parameter, the second with no parameters that generates it automatically based on current time.

## Advanced features

### Creates a new PRNG

Note that you can easily use other prng as soon as you implement the `automaton-optimization.randomness.prng` protocol.

Only functions defined in the `PRNG` protocol are required, and all other functions of the namespace and `automaton-optimization` will be able to use this prng.

## Distribution 

To draw a random number according to a distribution, you need to decide:
* The underlying pseudo-random generator - for instance, the `xoro` one.
* What is the distribution of that randomness, search for one distribution in the `Leverage existing PRNG` section. 
* The parameters of this distribution - note this depends on the distribution.

### Leverage existing Distributions
Distributions are:
 * `:weibull`
 * `:beta`
 * `:bernoulli`
 * `:dirichlet-multinomial`
 * `:chi-squared`
 * `:normal`
 * `:log-normal`
 * `:cauchy`
 * `:uniform`
 * `:pareto`
 * `:dirichlet`
 * `:exponential`
 * `:categorical`
 * `:gamma`
 * `:binomial`
 * `:poisson`
 * `:beta-binomial`
 * `:f`
 * `:multinomial`
 * `:t`

Note that you can extend/superseed this registry by updating the `distribution-registry` map, and then use the 3 arity of the `build` function to create distribution with this registry.

### Sample
