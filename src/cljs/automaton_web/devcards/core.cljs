(ns automaton-web.devcards.core
  "Devcards present components so they're seen / tested"
  (:require [automaton-web.devcards.section]
            [automaton-web.devcards.errors]
            [automaton-web.devcards.card]
            [automaton-web.devcards.alert]
            [automaton-web.devcards.mailchimp]
            [automaton-web.devcards.button]
            [automaton-web.devcards.grid-list]
            [automaton-web.devcards.footer]
            [automaton-web.devcards.navigation]
            [automaton-web.devcards.form]
            [automaton-web.devcards.header]
            [automaton-web.devcards.icons]
            [automaton-web.devcards.input]
            [automaton-web.devcards.logo]
            [automaton-web.devcards.menu]
            [automaton-web.devcards.menu-item]
            [automaton-web.devcards.modal]
            [automaton-web.devcards.select]
            [automaton-web.devcards.tooltip]
            [automaton-web.devcards.version]
            [automaton-web.devcards.structure]
            [automaton-web.devcards.table]
            [cljsjs.highlight]
            [cljsjs.marked]
            [cljsjs.react]
            [cljsjs.react.dom] ;; devcards needs cljsjs.react and
                               ;; cljsjs.react.dom to be imported
            [devcards.core :as dc]
            [mount.core :as mount]))

(defn ^:export init "Entry point for starting devcards" [] (mount/start) (dc/start-devcard-ui!))
