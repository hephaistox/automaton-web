(ns automaton-optimization.randomness.prng
  "Pseudo random number generator.
  This protocol hides the complexity of the different random number generator. As many different implementations exists and none superseeds all others.

  See [notion page](https://www.notion.so/hephaistox/Pseudo-Random-Number-Generator-1d76651744174b04848c294841e916fc?pvs=4) for the choice of generators."
  (:refer-clojure :exclude [next]))
