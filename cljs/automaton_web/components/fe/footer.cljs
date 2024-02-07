(ns automaton-web.components.fe.footer
  (:require
   [automaton-web.components.footer :as web-footer]
   [automaton-web.components.link :as web-link]))

(defn simple-footer
  "Minimal version of footer component.
   All the keys are optional.
   * `title` - text next to release
   * `social-networks` is a map
      * where key is an primitive type id
      * and value is an component that will be displayed as an icon
   * `footer-data` is a map:
      * `panel` id of the panel
      * `title` title text to write in the footer
   * `release` - a string with version to display
   * `dark?` - color mode"
  [{:keys [footer-data]
    :as props}]
  (let [footer-lists (mapv #(web-link/conditional-link-opts (dissoc %
                                                             :footer-data))
                           footer-data)]
    (web-footer/simple-footer (assoc props :footer-lists footer-lists))))

