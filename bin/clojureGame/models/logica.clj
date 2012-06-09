(ns clojureGame.models.logica
  (:require [noir.session :as session]))

(def empty-board [[\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]])

(def init-state {:board empty-board :player \X})
(def game-state (atom empty-board))

(defn new-game! []
  (session/put! :game-state @init-state))
  ;(reset! game-state empty-board))

(defn get-board []
  ;(:board @game-state))
  (:board (session/get :game-state)))

(defn get-player []
  ;(:player @game-state))
  (:player (session/get :game-state)))  
  
(defn get-board-cell 
  ([row column]
    (get-board-cell (get-board) row column))
  ([board row column]
    (get-in board [row column])))

(defn set-board-cell
  ([row column]
    (set-board-cell (get-board) row column))
  ([board row column]
    (update-in board [row column])))
  
(defn other-player []
    (if (= (get-player) \X) \O \X))

(defn free-row? 
  ([column] 
    (free-row? get-board 0 column))
  ([row column]
    (free-row? get-board row column))
  ([board row column]
    (if (= (get-in board [row column]) \-) row (free-row? (inc row) column))))

(defn drop-in-column [column player]
  (set-board-cell (free-row? column)))
