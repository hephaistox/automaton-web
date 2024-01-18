(ns automaton-web.components.errors
  "Contains all the components that are displayed when something unexpected happens and we need to inform a user.
   This namespace will also help us with consistency on error components inside the application.
   Like a not found page.")

(defn not-found
  [{:keys [title description _back-home-text _back-link] ;:or {_back-link
                                                         ;"/"}
   }]
  [:main {:class ["relative isolate min-h-full h-full"]}
   [:div
    {:class
     ["before:content-[''] before:absolute before:h-full before:w-full before:bg-black/[.5]"]}
    [:img {:src "/images/not_found.jpg"
           :alt ""
           :class
           ["absolute inset-0 -z-10 h-full w-full object-cover object-top "]}]]
   [:div
    {:class
     ["absolute left-1/2 top-1/2 text-center -translate-x-1/2 -translate-y-1/2"]}
    [:p {:class
         ["text-base font-semibold leading-8 text-white text-border-red"]}
     "404"]
    [:h1
     {:class
      ["mt-4 text-3xl font-bold tracking-tight text-white sm:text-5xl text-border-red-thin"]}
     title]
    [:p {:class ["mt-4 text-base text-white/70 sm:mt-6 text-border-black"]}
     description]
    [:div {:class ["mt-10 flex justify-center text-border-black"]}
     #_(foobar-navigation/back-navigation {:href back-link
                                           :text back-home-text
                                           :dark? true})]]])

(defn internal-error
  [{:keys [title description _back-home-text _back-link] ;:or {_back-link
                                                         ;"/"}
   }]
  [:main {:class ["relative isolate min-h-full h-full"]}
   [:div
    {:class
     ["before:content-[''] before:absolute before:h-full before:w-full before:bg-black/[.5]"]}
    [:img {:src "/images/not_found.jpg"
           :alt ""
           :class
           ["absolute inset-0 -z-10 h-full w-full object-cover object-top "]}]]
   [:div
    {:class
     ["absolute left-1/2 top-1/2 text-center -translate-x-1/2 -translate-y-1/2"]}
    [:p {:class
         ["text-base font-semibold leading-8 text-white text-border-red"]}
     "500"]
    [:h1
     {:class
      ["mt-4 text-3xl font-bold tracking-tight text-white sm:text-5xl text-border-red-thin"]}
     title]
    [:p {:class ["mt-4 text-base text-white/70 sm:mt-6 text-border-black"]}
     description]
    [:div {:class ["mt-10 flex justify-center text-border-black"]}
     #_(foobar-navigation/back-navigation {:href back-link
                                           :text back-home-text
                                           :dark? true})]]])
