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
  [:input {:name name
            :value value
            :type type}])

(defpartial createButton[name value type]
  [:td [:input {:name name
            :value value
            :type type}]])

(defpartial showButton []
  (createButton (str "b" 1) "1" "submit")
  (createButton (str "b" 2) "2" "submit")
  (createButton (str "b" 3) "3" "submit")
  (createButton (str "b" 4) "4" "submit")
  (createButton (str "b" 5) "5" "submit")
  (createButton (str "b" 6) "6" "submit"))

  
(defpartial showScreen [board] 
  (for [x (range 6)]
    [:br
      (for[y (range 6)]     
      (createField "not" (get-in board [x y]) "button"))]))

(defpartial playScreen []
   [:p "Welcome to clojure connect four"]
    [:p "Turn player: " (logica/get-player)]
          (form-to [:post "/"](showButton))
     (showScreen (logica/get-board))
  )

(defpartial winnnerScreen [winner]
   [:p "player" winner "has won"]
  )

(defpage "/" []
  (logica/new-game!)
  (common/layout
    (playScreen)))

(defpage [:post "/"] {:as button-pressed}
  (let [button-id (name (first (keys button-pressed)))
        colnr (Integer/parseInt (str (nth button-id 1)))]
    (logica/drop-in-column colnr (logica/get-player))
    (common/layout  
    (playScreen))))

;(defpartial showImage[imageshow]
;  [:input(html (image imageshow "not found  "))]
;  )

;(defpartial showScreen [board]
;  (for [x (range 6)]
;    [:p
;      (for[y (range 6)] 
;        (if (= (get-in board [x y]) \-)
;          (showImage (.getFile (clojure.java.io/resource "img/player1.png")))
;        (if (= (get-in board [x y]) \X)
;          (showImage "c:/player1.png")
;        (if (= (get-in board [x y]) \O)
;          (showImage "c:/player2.png")))))]))



