(ns clojureGame.views.connectFour
  (:require [clojureGame.views.common :as common]
            [clojureGame.models.logica :as logica]
            [noir.content.getting-started])
  (:use noir.core))

(defpartial testTwee []
  [:div 
   [:p (logica/sum 1 2)]])

(defpartial cell-html [rownum colnum cell] 
  [:td 
   [:input {:name (str "b" rownum colnum) 
            :value (str cell)
            :type "button"}]])
  
(defpartial row-html [rownum row]
  [:tr (map-indexed (fn [colnum cell]
                      (cell-html rownum colnum cell))
                    row)])
  
(defpartial board-html [board]
  ([:post "/"]
           [:table 
            (map-indexed (fn [rownum row]
                           (row-html rownum row)) 
                         board)]))
  
(defpartial play-screen []
  [:div 
   [:p "Player 1, it is your turn!"]
   (board-html [[\- \- \-][\- \- \-][\- \- \-]])])

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to clojure test test"]
         (play-screen)))
