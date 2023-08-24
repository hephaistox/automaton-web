(ns automaton-web.components.grid-list)

(defn grid-box
  [{:keys [size
           class]} & components]
  (into
   [:div
    {:class (vec (concat ["grid lg:max-w-none gap-x-8 gap-y-16 text-center mx-auto lg:mx-0"
                          (case size
                            :sm "grid-cols-1 md:grid-cols-2"
                            :md "grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 mx-auto"
                            "grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-6 mx-auto")]
                         class))}]

   (for [component components]
     component)))
