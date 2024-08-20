#_{:heph-ignore {:forbidden-words ["automaton-build"]}}
(ns automaton-web.pages.admin
  "Admin page"
  (:require
   [automaton-core.adapters.edn-utils     :as edn-utils]
   [automaton-core.adapters.env-variables :as env-vars]
   [automaton-core.adapters.version       :as version]
   [automaton-core.app.build-config       :as build-config]
   [automaton-core.http.request           :as request]
   [automaton-web.components.icons        :as web-icons]
   [automaton-web.components.table        :as web-table]
   [automaton-web.configuration           :as web-conf]
   [mount.tools.graph                     :as mount-graph]))

(defn menu-items
  "Create menu items"
  []
  [{:message "Docker hub"
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
   {:message "Cljs browser test"
    :category "Development"
    :uri (str "http://localhost:8081")}
   {:message "Portfolio"
    :category "Development"
    :uri "http://localhost:8080/"}
   {:message "404 page"
    :category "Development"
    :uri "this-page-doesnt-exists"}
   {:message "Throw an exception"
    :category "Development"
    :uri "admin/throw-exception"}
   {:message "Repos"
    :category "Git"
    :uri "https://github.com/orgs/hephaistox/repositories"}
   {:message "Git automaton web"
    :category "Git"
    :uri "https://github.com/hephaistox/automaton-web"}
   {:message "Git automaton core"
    :category "Git"
    :uri "https://github.com/hephaistox/automaton-core"}
   {:message "Git automaton build"
    :category "Git"
    :uri "https://github.com/hephaistox/automaton-build"}
   {:message "hephaistox"
    :category "Git"
    :uri "https://github.com/hephaistox/monorepo"}
   {:message "Tailwind"
    :category "Tooling documentation"
    :uri "https://tailwindcss.com/docs/"}
   {:message "Fontawesome Icons"
    :category "Tooling documentation"
    :uri "https://fontawesome.com/icons"}
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
   {:message "Reagent"
    :category "Tooling documentation"
    :uri "https://reagent-project.github.io/"}
   {:message "Teams communication"
    :category "Organization"
    :uri "https://teams.microsoft.com/"}
   {:message "Project tasks"
    :category "Organization"
    :uri
    "https://dev.azure.com/mateusz0216/Hephaistox%20Agile/_boards/board/t/Hephaistox%20Agile%20Team/Stories"}
   {:message "Shared files with team"
    :category "Organization"
    :uri
    "https://hephaistox.sharepoint.com/sites/hephaistox/Shared%20Documents/Forms/AllItems.aspx"}
   {:message "Shared files with customers"
    :category "Organization"
    :uri
    "https://hephaistox.sharepoint.com/sites/customers-materials/Documents%20partages/Forms/AllItems.aspx?id=%2Fsites%2Fcustomers%2Dmaterials%2FDocuments%20partages%2FDownloadable%20materials&p=true&ga=1"}])

(defn sorted-items
  "Create categories of menu item"
  [m]
  (sort (group-by :category m)))

(defn component-graph-deps
  "Create component graph data from the mount components"
  [graph]
  (map (fn [m]
         (-> m
             (dissoc :order)
             (assoc :status-color
                    (if (contains? (:status m) :started) :green :red))))
       (sort-by :order graph)))

(defn main-app-build-config-view
  [build-config-edn]
  (let [app-name (:app-name build-config-edn)]
    [:div
     [:div (str app-name " full build config:")]
     (web-table/map->table build-config-edn)]))

(defn display-links
  [items-by-category]
  [:div {:class ["flex flex-col"]}
   [:h2 "Links"]
   (for [[category items] items-by-category]
     (when category
       [:div
        [:h3 {:class ["text-left"]}
         category]
        [:div {:class ["flex flex-wrap"]}
         (for [{:keys [uri message]} items]
           [:a {:class ["cursor-pointer select-none"]
                :href uri
                :target "blank"}
            [:div {:class
                   ["flex flex-row border-black border-solid border-2 m-2"]}
             [:div {:class ["m-2 p-2"]}
              message]]])]]))])

(defn all-apps-links
  [cust-apps]
  (let [admin-maps
        (reduce
         (fn [acc app]
           (let [app-name (:app-name app)
                 app-repo-link "to be done"
                 git-repo {:message "Git repo"
                           :category app-name
                           :uri app-repo-link}
                 doc (when (:doc? app)
                       {:message "TBC Docs"
                        :category app-name
                        :uri ""})
                 envs (reduce (fn [acc [_ v]]
                                (conj acc
                                      (when (:web-link v)
                                        {:message (str "web-" (:remote-name v))
                                         :category app-name
                                         :uri (:web-link v)})
                                      (when (:log-link v)
                                        {:message (str "log-" (:remote-name v))
                                         :category app-name
                                         :uri (:log-link v)})))
                              []
                              (:run-env app))]
             (vec (flatten (vec (apply merge (conj acc git-repo doc) envs))))))
         []
         cust-apps)
        sorted-links (sorted-items admin-maps)]
    (display-links sorted-links)))

(defn- ignore-error
  [fn-try ret-val]
  (try (or (fn-try) ret-val) (catch Exception _ ret-val)))

(defn envs-indication
  [cust-apps]
  (let
    [gh-token (env-vars/get-env "GH_TOKEN")
     rows
     (reduce
      (fn [acc app]
        (let [app-name (get-in app [:app-name])
              local-env (get (version/slurp-version (str app-name "/"))
                             version/release)
              gh-version (ignore-error
                          #(request/http-get
                            (str "https://api.github.com/repos/hephaistox/"
                                 app-name
                                 "/contents/version.edn")
                            {:headers {"Authorization" (str "Bearer " gh-token)
                                       "Accept"
                                       "application/vnd.github.raw+json"}})
                          (atom "Error"))
              gh-version-waited (ignore-error #(:release (read-string
                                                          (:body @gh-version)))
                                              "Error")
              prod-env
              (ignore-error
               #(request/http-get
                 (str (get-in app [:build-config :run-env :prod-env :web-link])
                      "/api/version"))
               (atom "Error"))
              test-env
              (ignore-error
               #(request/http-get
                 (str (get-in app [:build-config :run-env :test-env :web-link])
                      "/api/version"))
               (atom "Error"))]
          (conj
           acc
           [app-name
            local-env
            gh-version-waited
            (:body @prod-env)
            (:body @test-env)
            (if (= local-env gh-version prod-env test-env)
              (web-icons/icon
               {:path-kw :svg/check-circle
                :scale 0.03
                :color "green"
                :class
                ["w-[20px]
                                                    h-[20px]"]
                :hover? false})
              (web-icons/icon
               {:path-kw :svg/x-circle
                :scale 0.03
                :color "red"
                :class
                ["w-[20px]
                                                    h-[20px]"]
                :hover? false}))])))
      []
      cust-apps)]
    (web-table/table
     {:headers ["App name" "Local env" "Github" "Prod env" "Test env" "Status"]
      :rows rows})))

(defn current-app-stats
  [graph]
  [:div {:class ["flex flex-wrap"]}
   [:div
    [:h2 "Component graph deps"]
    [:div {:class ["flex flex-col border-black border-solid border-2 m-2"]}
     (for [{:keys [_status status-color name]} graph]
       [:div {:class ["p-2"
                      (case status-color
                        :green "bg-green-300"
                        "bg-red-300")]}
        name])]]])

(defn admin-page
  "Admin page content hiccup and information.
   Used to display admin page view."
  []
  (let [items-by-category (sorted-items (menu-items))
        graph (component-graph-deps (mount-graph/states-with-deps))
        app-name (web-conf/read-param [:app-name])
        build-configs-paths (build-config/search-for-build-configs "")
        build-configs (map edn-utils/read-edn build-configs-paths)
        current-app-config
        (first (filter (fn [app] (= (:app-name app) app-name)) build-configs))
        cust-apps (filter (fn [app] (:cust-app? app)) build-configs)]
    [:div {:id "admin-index"
           :class ["text-center w-full"]}
     (envs-indication cust-apps)
     (display-links items-by-category)
     (all-apps-links cust-apps)
     (current-app-stats graph)
     (main-app-build-config-view current-app-config)]))
