(ns automaton-web.components.structure)

(defn structure
  [{:keys [header footer class]} & components]
  [:div {:class (vec (concat class ["h-full flex flex-col relative"]))} header (into [:div {:class ["grow"]}] (for [comp components] comp))
   footer])
