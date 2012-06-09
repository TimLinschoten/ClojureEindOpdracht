(ns clojureGame.views.welcome
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
  (createButton "1" "1" "button")
  (createButton "2" "2" "button")
  (createButton "3" "3" "button")
  (createButton "4" "4" "button")
  (createButton "5" "5" "button")
  (createButton "6" "6" "button"))

(defpartial showImage[imageshow]
  (html (image imageshow "not found  "))
  )
  
;(defpartial showScreen [board]
;  (for [x (range 6)]
;    [:p
;      (for[y (range 6)]     
;      (createField "not" (get-in board [x y]) "submit"))]))

(defpartial showScreen [board]
  (for [x (range 6)]
    [:p
      (for[y (range 6)] 
        (if (= (get-in board [x y]) \-)
          (showImage (.getFile (clojure.java.io/resource "img/player1.png")))
        (if (= (get-in board [x y]) \X)
          (showImage "c:/player1.png")
        (if (= (get-in board [x y]) \O)
          (showImage "c:/player2.png")))))]))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to clojure connect four"]
           [:p "Player " "(logica/get-player)"]
           (showButton)
           (showScreen [[\- \- \X \- \- \-]
                        [\- \- \- \- \- \-]
                        [\- \- \- \- \- \-]
                        [\- \- \- \O \- \-]
                        [\X \- \- \- \- \-]
                        [\- \- \- \- \- \-]])))



