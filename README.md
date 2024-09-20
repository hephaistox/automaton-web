# Automaton optimization

## Definition:

### Model

A model is a simplification of the system that is learning something useful from it, the model formalizes the system through its states and discrete events.

### System
The system is the part of real life that the discrete event simulation is working on.

## Objective: 
Common concepts for optimizations, whatever the technology is: Discrete event simulation, scheduling, linear programming etc.


## Features

The features of those libraries are:
* Random variable [cf. def](https://en.wikipedia.org/wiki/Random_variable)
  * Pseudo-random number generator
  * [Main distributions](https://en.wikipedia.org/wiki/List_of_probability_distributions):
     * [normal distribution](https://en.wikipedia.org/wiki/Normal_distribution), 
     * [discrete uniform distribution](https://en.wikipedia.org/wiki/Discrete_uniform_distribution)
     * [Poisson binomial distribution](https://en.wikipedia.org/wiki/Poisson_binomial_distribution)
     * [noncentral chi-squared distribution](https://en.wikipedia.org/wiki/Noncentral_chi-squared_distribution)
* Time management
  * Interface for time in optimization
  * Implementation as Clojure date - https://github.com/dm3/clojure.java-time
  * Implementation as integer, one date is a bucket number
  * Implementation as variable bucket management (different bucket size)
* Scenario management
  * Scenario portfolio - 
  * Scenario persistence -
* Optimization runner
  * Randomized setup - when some parameters of the optimization are random variable
  * Multiple seed execution
* Performance indicators (KPI)
  * Being able to compare solutions
* Objective comparison 
  * Simple mono-objective comparison
  * Multi-objective comparison (Pareto front)
* Time phased variable 
  * Generic interface - get data at a given time whatever the implementation
  * Constant piecewise [cf. def](https://mathworld.wolfram.com/PiecewiseConstantFunction.html)
     * between two values: the constant piecewise is returning the value of the previous event where a constant has been set
     * summing two variables: consists in two constant piecewise that are summed
     * typical usage:
       * stock level: a stock level is set at one point in time, and is constant and still valid for all later on moments, until next stock level information 
  * Dirac - the value is valid at a specific point
     * between two values: 0 is returned 
     * summing two variables: is the union of the two sets of date/value data.
     * typical usage:
       * production: a production is set at one point in time, every next event does not exist
  * Fuzzy interval - the value is somewhere in an interval 
     * between two values: the value is somewhere in between
     * summing two variables: each interval intersection has a part of the value (proportional to its size), and the sum of the two intervals are stored
     * typical usage:
       * Demand: when a demand is set in a month, we don't really know what day it is, all zoom in should spread the value in the interval so the final total is the same

License information can be found in [LICENSE file](LICENSE.md)
Copyright © 2020-2024 Anthony Caumond, Mateusz Mazurczak
