(ns clojureGame.views.connectFour
  (:require [clojureGame.views.common :as common]
            [clojureGame.models.logica :as logica]
            [noir.content.getting-started])
  (:use noir.core
        hiccup.core
        hiccup.page
        hiccup.form
        hiccup.element))

(defpartial createField[name value type]
  [:td(if (= value \X)
        (do [:input {:name name
                       :value value
                       :type type}])
        (if (= value \O)
           (do [:input {:name name
                          :value value
                          :type type}])
           (do [:input {:name name
                        :value value
                        :type type}])))
   ])

(defpartial createButton[name value type]
  [:td [:button {:name name
            :value value
            :type type}]])

(defpartial showButton []
  [:table[:tr
  (createButton (str "b" 0) "1" "submit")
  (createButton (str "b" 1) "2" "submit")
  (createButton (str "b" 2) "3" "submit")
  (createButton (str "b" 3) "4" "submit")
  (createButton (str "b" 4) "5" "submit")
  (createButton (str "b" 5) "6" "submit")]])

  
(defpartial showScreen [board]
  [:table
  (for [x (range 6)]
    [:tr
      (for[y (range 6)]     
      (createField "not" (get-in board [x y]) "button"))])])

(defpartial playScreen []
  [:dive
   [:p "Welcome to clojure connect four"]
    [:p "Turn player: " (logica/get-player)]
          (form-to [:post "/"](showButton))
     (showScreen (logica/get-board))]
  )

(defpartial winnerScreen [winner]
  [:div
   [:p "player" winner "has won"]
   (showScreen (logica/get-board))]
  )

(defpage "/" []
  (logica/new-game!)
  (common/layout
    (playScreen)))

(defpage [:post "/"] {:as button-pressed}
  (let [button-id (name (first (keys button-pressed)))
        colnr (Integer/parseInt (str (nth button-id 1)))]
    (logica/drop-in-column colnr)
    (let [winner (logica/is-winner)]
      (if (= winner nil)
        (common/layout (playScreen))
        (common/layout (winnerScreen winner))))))