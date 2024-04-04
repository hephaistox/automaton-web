(ns automaton-web.portfolio.components.modal
  (:require
   [automaton-web.components.button :as web-button]
   [automaton-web.components.modal  :as sut]
   [automaton-web.portfolio.proxy   :as web-proxy]
   [portfolio.reagent-18            :as           portfolio
                                    :refer-macros [defscene configure-scenes]]))

(configure-scenes {:collection :components
                   :title "Modal"})

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene modal-big
          (web-proxy/wrap-component (sut/wrap-modal-call {:modal-id "bigModal"}
                                                         [web-button/button
                                                          {:text "Call modal"}])
                                    [sut/modal-big {:title [:div "hello"]
                                                    :body [:div "Body"]
                                                    :id "bigModal"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 modal-detailed
 (web-proxy/wrap-component
  (sut/wrap-modal-call {:modal-id "basic-modal"}
                       [web-button/button {:text "Show modal"}])
  [sut/details-modal
   {:img
    "https://images.unsplash.com/photo-1519345182560-3f2917c472ef?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80"
    :name "Arthur Dent"
    :description
    "Arthur dies in the fifth installment of the book series, Mostly Harmless, in a club called Beta (owned by Stavro Mueller) when the Earth and all its duplicates are seemingly destroyed by the Grebulons. Adams frequently expressed his disdain for this ending in retrospect, claiming that it was too depressing and came about as the result of him having a bad year."
    :title "Earthman and Englishman"
    :modal-id "basic-modal"}]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defscene
 modal-detailed-sections
 (web-proxy/wrap-component
  (sut/wrap-modal-call {:modal-id "sections-modal"}
                       [web-button/button {:text "Show modal"}])
  [sut/details-modal
   {:img
    "https://images.unsplash.com/photo-1519345182560-3f2917c472ef?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80"
    :name "Jar Jar Binks"
    :description
    "Meesa Jar Jar Binks, a lovable Gungan with a heart full of good intentions and a knack for clumsiness. Meesa bringin' laughter and charm to the Star Wars galaxy, always stumbling into adventures and playing an unexpected part in the galaxy's fate. Meesa may not be the most graceful, but meesa always ready to make a difference and spread joy wherever meesa goes."
    :title "Gungan military commander and politician"
    :modal-id "sections-modal"}
   {:id (str "jarjar-1")
    :title "The Clumsy Comedian"
    :description
    "In this section, we delve into Jar Jar Binks' endearing clumsiness and his ability to turn mishaps into moments of humor. From tripping over his own feet to unintentionally causing chaos, Jar Jar's knack for physical comedy brings a lighthearted touch to his character."}
   {:id (str "jarjar-2")
    :title "Unlikely Hero"
    :description
    "Here, we explore Jar Jar's unexpected journey from a simple Gungan to an accidental hero. Despite his initial naivety and lack of combat skills, Jar Jar finds himself thrust into critical situations and rises to the occasion, proving that bravery can come from the most unlikely sources."}
   {:id (str "jarjar-3")
    :title "A Friend to All"
    :description
    "In this section, we highlight Jar Jar's genuine kindness and his ability to forge connections with characters from different walks of life. Whether it's befriending Qui-Gon Jinn and Obi-Wan Kenobi or bridging the gap between the Gungans and the people of Naboo, Jar Jar's warm-hearted nature makes him a loyal and dedicated friend to those around him."}
   {:id (str "jarjar-4")
    :title "Navigating a Galaxy in Turmoil"
    :description
    "Here, we explore Jar Jar's role in the tumultuous events of the Star Wars galaxy. From his involvement in the Trade Federation conflict to his stint as a senator in the Galactic Senate, Jar Jar finds himself amidst political upheaval, striving to bring unity and understanding in a time of division."}
   {:id (str "jarjar-5")
    :title "Legacy and Controversy"
    :description
    "This section addresses the lasting impact and mixed reception of Jar Jar Binks. We discuss the character's enduring presence in popular culture and how he has sparked debates among fans. Despite the controversies, Jar Jar remains an iconic figure, reminding us that even the most polarizing characters can leave a lasting legacy."}]))
