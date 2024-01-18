(ns automaton-web.components.navigation
  "Navigation component,
  Should be instantiated with your routers data in the customer app")

(defn back-navigation
  "Print a back navigation button
  * The linked could be set with `href` or `on-click` and are higher priority"
  [{:keys [href text dark? on-click]}]
  [:a
   (merge (cond
            href {:href href}
            on-click {:on-click on-click})
          {:class ["font-semibold leading-7"
                   (when (some nil? [href on-click]) "cursor-pointer")
                   (if dark? "text-additional" "text-primary")]})
   [:span {:aria-hidden "true"}
    "← "]
   text])
