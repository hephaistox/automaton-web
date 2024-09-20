<!--toc:start-->
- [Time-based features.](#time-based-features)
  - [Quick start for additive measures](#quick-start-for-additive-measures)
- [Quick start for the `latest` time-based values.](#quick-start-for-the-latest-time-based-values)
- [Other `tb-var` features](#other-tb-var-features)
- [Aggregators](#aggregators)
- [`tb-var-aggregated`](#tb-var-aggregated)
- [Advanced](#advanced)
<!--toc:end-->

# Time-based features.
When a process creates some data over time, the time-based feature stores and manipulates measures of that data over time.

A measure is associating a `value` over time, at a certain `bucket`. All the measures of the same concept are stored together in a time-based variable `tb-var`.

There are two available behaviors for `time-based` features:

* `latest` that is, like an inventory level, concerned with the latest value.
* `additive` that is, like a machine production, whose underlying concept is additive, so measures at the same buckets will be added.

Even if those two features are similar, they are presented below separately, so we can illustrate the detailed results of each of them.

## Quick start for additive measures
You can create an additive variable, the so-called `tb-var-additive`, all measures leading to the same bucket are added.

By default, a non-set variable is defaulted to `0`.
```clojure
(ns opt-tb-demo)
(require '[automaton-optimization.time-based :as opt-tb])
(-> (opt-tb/tb-var-additive-deltas)
    (opt-tb/get-measure 10))
;;0
```

When a custom default value is set it is the value returned instead.
```clojure
(-> (opt-tb/tb-var-additive-deltas -666)
    (opt-tb/get-measure 10))
;;-666
```

If measure `10` is done at bucket `3`, this value is returned by `get-measure` at bucket `10`.
```clojure
(-> (opt-tb/tb-var-additive-deltas)
    (opt-tb/measure 10 3)
    (opt-tb/get-measure 10))
;;3
```

If more than one measure is done, `get-measure` at bucket `10` returns the sum of all measured values at 10.
```clojure
(-> (opt-tb/tb-var-additive-deltas)
    (opt-tb/measure 10 3)
    (opt-tb/measure 10 2)
    (opt-tb/measure 11 4)
    (opt-tb/get-measure 10))
;;5
```

The function `get-measures` allows an easy and efficient return of values for a collection of buckets.
Note that buckets with no measure are set to the default value.
```clojure
(-> (opt-tb/tb-var-additive-deltas)
    (opt-tb/measure 10 3)
    (opt-tb/measure 10 2)
    (opt-tb/measure 11 4)
    (opt-tb/get-measures (range 8 14)))
;[0 0 5 4 0 0]

Note that order is preserved
```clojure
(-> (opt-tb/tb-var-additive-deltas)
    (opt-tb/measure 10 3)
    (opt-tb/measure 10 2)
    (opt-tb/measure 11 4)
    (opt-tb/get-measures (reverse (range 8 14))))
; [0 0 4 5 0 0]

```
# Quick start for the `latest` time-based values.
The `tb-var-latest` stores the measured values by the `bucket`. The returned value will be the latest value set for a `bucket` in the past.
Note this example, similar to the first example of `additive` returns different values, as the second measure on the same date replaces the previous one.

If no measure is done at bucket `10` or before, then the default value is returned.
Note that this returned value could be whatever, as no operation is done on it.
```clojure
(-> (opt-tb/tb-var-latest-deltas :foobar)
    (opt-tb/measure 12 :a)
    (opt-tb/get-measure 10))
```

If a measure is done at bucket `10, then the default value is returned before `10` and `3` is returned after.
```clojure
(as->
    (opt-tb/tb-var-latest-deltas :foobar)
    tb-var
  (opt-tb/measure tb-var 10 3)
  (mapv #(opt-tb/get-measure tb-var %)
        [9 10 11 20]))
;; [:foobar 3 3 3]
```

If more than one value is set on the same `bucket`, then only the last measured (in order of the `measure` function call) is returned.
```clojure
(-> (opt-tb/tb-var-latest-deltas :foobar)
    (opt-tb/measure 10 3)
    (opt-tb/measure 10 2)
    (opt-tb/get-measure 10))
;; 2
```

# Other `tb-var` features
Whatever the `tb-var` is built, there are other features than `measure` and `get-measure`.

The `get-measures` help to quickly translate and note the results are different from additive as expected.

```clojure
(-> (opt-tb/tb-var-latest-deltas :foobar)
    (opt-tb/measure 10 3)
    (opt-tb/measure 10 2)
    (opt-tb/measure 11 4)
    (opt-tb/get-measures (range 8 14)))
;;[:foobar :foobar 2 4 4 4]
```

The default value of a `tb-var` is returned with the `default` function.

```clojure
(-> (opt-tb/tb-var-additive-deltas 3)
    (opt-tb/measure 10 3)
    (opt-tb/measure 10 2)
    (opt-tb/measure 11 4)
    opt-tb/default)
;3
```

# Aggregators
Aggregators can group `bucket`s in `bucket-aggregate`.

An aggregator can simply group `bucket`s. `aggregator` function creates it, then you can use it to translate the `bucket` into an `aggregate``.

```clojure
(-> [#::opt-tb{:start-bucket 0
               :step 10}]
    opt-tb/aggregator
    (opt-tb/to-bucket-aggregate 40))
; 4
```

An `aggregates` validity input data could be checked, it returns `nil` if it is valid or the error map if not.
```clojure
(opt-tb/validate-aggregates [#::opt-tb{:start-bucket 0
                                       :step 10}])
; nil

(opt-tb/validate-aggregates [#::opt-tb{:step 10}])
; [#:automaton-optimization.time-based{:start-bucket ["missing required key"]}]
```

If you apply `to-bucket-aggregate` to buckets wrapping the `bucket` definition. You'll remark that outside the defined range, the `bucket-aggregate` returns `nil`.
```clojure
(def agg
  (-> [#::opt-tb{:start-bucket 10
                 :end-bucket 20
                 :step 5}]
          opt-tb/aggregator))

(mapv #(opt-tb/to-bucket-aggregate
        agg %)
      (range 8 22))
; [nil nil 0 0 0 0 0 1 1 1 1 1 nil nil]
```

If you need the valid `bucket-aggregate` matching the `bucket` you pass as parameters, you can use:
```clojure
(-> [#::opt-tb{:start-bucket 10
               :end-bucket 20
               :step 5}]
    opt-tb/aggregator
    (opt-tb/to-bucket-aggregates (range 8 22)))
; [0 1]
```

# `tb-var-aggregated`
To store data in `tb-var` in aggregates, you need to define an `aggregator`.
The measures and queries are done at the `bucket` level, but the values are aggregated according to the `aggregator`.
Note that, it behaves like a classical `tb-var`, for instance, it returns `nil` outside the definition.
Note also that the `tb-var-aggregated` is relying on a `tb-var`, all properties of that `tb-var` are kept.

```clojure
(def tb-var-agg1
  (-> [#::opt-tb{:start-bucket 10
                 :end-bucket 20
                 :step 5}]
      opt-tb/aggregator
      (opt-tb/tb-var-aggregated (opt-tb/tb-var-additive-deltas))
      (opt-tb/measure 10 3)
      (opt-tb/measure 14 4)))

(mapv #(opt-tb/get-measure tb-var-agg1 %)
      [9 10 11 14 15 16])
; [nil 7 7 7 0 0]
```

# Advanced
It is not part of the API, but you can see how the underlying data are set, to better get the two underlying data structures:

```clojure
(-> (opt-tb/additive-contiguous)
    (opt-tb/measure 10 3)
    (opt-tb/measure 10 2)
    (opt-tb/measure 11 4)
    (get-in [:storage :contiguous]))
;;[nil nil nil nil nil nil nil nil nil nil 5 4]

(-> (opt-tb/tb-var-latest-deltas)
    (opt-tb/measure 10 3)
    (opt-tb/measure 10 2)
    (opt-tb/measure 11 4)
    (get-in [:storage :deltas]))
;;{10 5, 11 4}
```

Each feature is shipped with the two storage implementations:

```clojure
(require '[automaton-optimization.time-based :as opt-tb])
(opt-tb/latest-contiguous)
(opt-tb/latest-deltas)
(opt-tb/additive-contiguous)
(opt-tb/tb-var-latest-deltas)
```
