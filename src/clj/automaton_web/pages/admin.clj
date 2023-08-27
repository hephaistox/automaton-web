(ns automaton-web.pages.admin
  "Admin page"
  (:require
   [clojure.edn :as edn]

   [mount.tools.graph :as graph]))

(defn optor-view
  "Build project view"
  [project-name]
  (let [shadow-cljs (edn/read-string
                     (slurp (str project-name "/shadow-cljs.edn")))]
    (merge {:cljs-nrepl-port (get-in shadow-cljs [:nrepl :port])}
           (into {}
                 (map (fn [[p urls]]
                        (vector (first urls) p))
                      (:dev-http shadow-cljs))))))

(defn menu-items
  "Create menu items"
  []
  [{:message "Clever logs"
    :category "Deploy"
    :uri "https://console.clever-cloud.com/organisations/orga_cb5862a9-e6bd-4085-9591-26d3110acad1/applications/app_b2f3c7a7-b999-448b-8de5-6390cc5aa2cf"}
   {:message "Docker hub"
    :category "Deploy"
    :uri "https://hub.docker.com/repositories"}
   {:message "Sas actions"
    :category "Deploy"
    :uri "https://github.com/hephaistox/monorepo/actions"}

   {:message "Website"
    :category "Development"
    :uri "/"}
   {:message "Shadow dashboard"
    :category "Development"
    :uri "http://localhost:9630/build/app"}
   {:message "Swagger"
    :category "Development"
    :uri "/api/doc/swagger-ui/index.html#/"}
   {:message "Cljs browser test"
    :category "Development"
    :uri (str "http://localhost:9630")}
   {:message "Devcards"
    :category "Development"
    :uri "/admin/devcards"}
   {:message "404 page"
    :category "Development"
    :uri "this-page-doesnt-exists"}
   {:message "Blank"
    :category "Development"
    :uri "/admin/blank"}
   {:message "Throw an exception"
    :category "Development"
    :uri "admin/throw-exception"}

   {:message "Repos"
    :category "Git"
    :uri  "https://github.com/hephaistox?tab=repositories"}
   {:message "Git automaton"
    :category "Git"
    :uri "https://github.com/hephaistox/automaton"}
   {:message "hephaistox"
    :category "Git"
    :uri "https://github.com/hephaistox/monorepo"}
   {:message "Optor"
    :category "Git"
    :uri "https://github.com/hephaistox/optor"}
   {:message "Website"
    :category "Production"
    :uri "http://app-b2f3c7a7-b999-448b-8de5-6390cc5aa2cf.cleverapps.io/"}
   {:message "Swagger"
    :category "Production"
    :uri "https://app-b2f3c7a7-b999-448b-8de5-6390cc5aa2cf.cleverapps.io/api/doc/swagger-ui/index.html#/"}

   {:message "Tailwind"
    :category "Tooling documentation"
    :uri "https://tailwindcss.com/docs/"}
   {:message "Clojure docs"
    :category "Tooling documentation"
    :uri "https://clojuredocs.org/"}
   {:message "Mermaid"
    :category "Tooling documentation"
    :uri "https://mermaid-js.github.io/mermaid/#/n00b-syntaxReference"}
   {:message "Markdown"
    :category "Tooling documentation"
    :uri "https://www.markdownguide.org/cheat-sheet/"}
   {:message "Babashka"
    :category "Tooling documentation"
    :uri "https://babashka.org/"}
   {:message "Reframe"
    :category "Tooling documentation"
    :uri "https://day8.github.io/re-frame/"}

   {:message "Slack"
    :category "Organization"
    :uri "https://optor.slack.com/"}
   {:message "Project tasks"
    :category "Organization"
    :uri "https://teams.microsoft.com/_#/tab::9509b9cd-e48a-4bbc-a8b7-707585842812/Engineering?threadId=19:3955270c687041d58506b9ca85dc0c0e@thread.tacv2&ctx=channel"}
   {:message "Shared files (365)"
    :category "Organization"
    :uri "https://mateuszmazurczaksoftware.sharepoint.com/sites/MateuszMazurczakbusinessandsoftware2/SitePages/Home.aspx"}])

(defn sorted-items
  "Create categories of menu item"
  [m]
  (sort
   (group-by :category m)))

(defn component-graph-deps
  "Create component graph data from the mount components"
  [graph]
  (map
   (fn [m]
     (-> m
         (dissoc :order)
         (assoc :status-color (if (contains? (:status m)
                                             :started)
                                :green
                                :red))))
   (sort-by :order
            graph)))

(defn map2hiccup
  "Convert a map to hiccup"
  [m]
  [:table
   {:class ["m-2 border-black border-solid border-2 m-2"]}
   (for [[k v] m]
     [:tr
      [:td
       {:class ["p-2"]}
       (name k)]
      [:td
       {:class ["p-2"]}
       (str v)]])])

(defn index
  [{:keys [envVars] :as request}]
  (let [items-by-category (sorted-items (menu-items))
        graph (component-graph-deps (graph/states-with-deps))]
    [:div
     {:id "admin-index"
      :class ["text-center w-full"]}
     [:div
      {:class ["flex flex-col"]}
      [:h2 "Links"]
      (for [[category items] items-by-category]
        [:div
         [:h3
          {:class ["text-left"]} category]
         [:div
          {:class ["flex flex-wrap"]}
          (for [{:keys [uri message]} items]
            [:a
             {:class ["cursor-pointer select-none"]
              :href uri
              :target "blank"}
             [:div
              {:class ["flex flex-row border-black border-solid border-2 m-2"]}
              [:div
               {:class ["m-2 p-2"]}
               message]]])]])]

     [:div
      {:class ["flex flex-wrap"]}
      [:div
       [:h2 "Component graph deps"]
       [:div
        {:class ["flex flex-col border-black border-solid border-2 m-2"]}
        (for [{:keys [_status status-color name]} graph]
          [:div
           {:class ["p-2"
                    (case status-color
                      :green "bg-green-300"
                      "bg-red-300")]}
           name])]]

      [:div
       [:h2 "Environment variables"]
       [:p (map2hiccup envVars)]

       [:h2 "Auth id:"]
       [:p (or (:session/key request)
               "N/A")]

       [:h2 "Versionning"]
       [:p "Clojure version: " (clojure-version)]]]]))
