(ns clojureGame.models.logica
  (:require [noir.session :as session]))

(def empty-board(atom [[\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]]))

(defn new-game! []
  (session/clear!)
  (session/put! :game-state empty-board)
  (session/put! :player \X)
  (session/put! :winner \-))

(defn get-board []
  @(session/get :game-state))

(defn set-board [board]
  (session/put! :game-state board))

(defn get-player []
  (session/get :player))

(defn next-player []
  (if (= (session/get :player) \X)
    (session/put! :player \O)
    (session/put! :player \X)))

(defn set-piece [rownr colnr]
  (set-board (atom (assoc-in @(session/get :game-state) [rownr colnr] (get-player)))))

(defn is-winner []
  (if (= (session/get :winner) \-)
    nil
    (if (= (session/get :winner) \X)
      \X
      (if (= (session/get :winner) \O)
        \O
  ))))

(defn free-row? 
  ([column] 
    (free-row? (get-board) 5 column))
  ([board row column]
    (if (= (get-in board [row column]) \-) row (if (> row 0)(free-row? board (dec row) column)nil))))

(defn drop-in-column [colnr]
  (let [rownr (free-row? colnr)]
    (if (=(= rownr nil) false)
      (do 
        (set-piece rownr colnr)
        (next-player))
  )))

