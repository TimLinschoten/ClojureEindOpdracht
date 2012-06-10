(ns clojureGame.models.logica
  (:require [noir.session :as session]))
(import (java.util.Math))

(def empty-board(atom [[\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]
                  [\- \- \- \- \- \-]]))

(defn new-game! []
  (session/clear!)
  (session/put! :game-state empty-board)
  (session/put! :player \X))

(defn get-board []
  @(session/get :game-state))

(defn set-board [board]
  (session/put! :game-state board))

(defn get-player []
  (session/get :player))

(defn other-player[]
  (if (= (session/get :player) \X)
    \O \X))

(defn next-player []
  (if (= (session/get :player) \X)
    (session/put! :player \O)
    (session/put! :player \X)))

(defn set-piece [rownr colnr]
  (set-board (atom (assoc-in @(session/get :game-state) [rownr colnr] (get-player)))))

(defn free-row? 
  ([column] 
    (free-row? (get-board) 5 column))
  ([board row column]
    (if (= (get-in board [row column]) \-) row (if (> row 0)(free-row? board (dec row) column)nil))))

;(def grid-size 6)
;(def max-grid 5)
;
;(defn horz-find-last-index [board row col transform]
;  (if (not= (get-in board [row col]) (get-in board [row (transform col)]))
;    col
;    (recur board row (transform col) transform)))
;
;(defn count-horz [board row col transform]
;  (. Math abs (- col (horz-find-last-index board row col transform))))
;
;(defn horizontal-line? 
;  ([row col]
;    (horizontal-line? (get-board) row col))
;  ([board row col]
;    (cond 
;      (= 0 col) (+ 1 (count-horz board row col inc))
;      (< max-grid col) (+ 1 (count-horz board row col inc) (count-horz board row col dec))
;      :else (+ 1 (count-horz board row col dec)))))
;    
;(defn vert-find-last-index [board row col transform]
;  (if (not= (get-in board [row col]) (get-in board [(transform row) col]))
;    row
;    (recur board (transform row) col transform)))
;
;(defn count-vert [board row col transform]
;  (. Math abs (- row (vert-find-last-index board row col transform))))
;
;(defn vertical-line? 
;  ([row col]
;    (vertical-line? (get-board) row col))
;  ([board row col]
;    (cond 
;      (= 0 row) (+ 1 (count-vert board row col inc))
;      (< row max-grid) (+ 1 (count-vert board row col inc) (count-vert board row col dec))
;      :else (+ 1 (count-vert board row col dec)))))
;
;(defn diagonal-line?
;  ([row col]
;    (diagonal-line? (get-board) row col))
;  ([board row col]
;    false))

(defn left-count
  ([row col]
      (left-count row (dec col) 0 (get-in(get-board) [row col])))
  ([row col count player]   
      (if (= (get-in (get-board) [row col]) player)
        (if (> col 0)
          (recur row (dec col) (inc count) player)
          (inc count))
        count
      )))

(defn right-count
  ([row col]
      (right-count row (inc col) 0 (get-in(get-board) [row col])))
  ([row col count player]   
      (if (= (get-in (get-board) [row col]) player)
        (if (< col 5)
          (recur row (inc col) (inc count) player)
          (inc count))
        count
      )))

(defn under-count
  ([row col]
      (under-count (inc row) col 0 (get-in(get-board) [row col])))
  ([row col count player]   
      (if (= (get-in (get-board) [row col]) player)
        (if (< row 5)
          (recur (inc row) col (inc count) player)
          (inc count))
        count
      )))

(defn rightup-count
  ([row col]
      (rightup-count (dec row) (inc col) 0 (get-in(get-board) [row col])))
  ([row col count player]   
      (if (= (get-in (get-board) [row col]) player)
        (if (and (> row 0)(< col 5))
          (recur (dec row) (inc col) (inc count) player)
          (inc count))
        count
      )))

(defn leftdown-count
  ([row col]
      (leftdown-count (inc row) (dec col) 0 (get-in(get-board) [row col])))
  ([row col count player]   
      (if (= (get-in (get-board) [row col]) player)
        (if (and (< row 5)(> col 0))
          (recur (inc row) (dec col) (inc count) player)
          (inc count))
        count
      )))

(defn leftup-count
  ([row col]
      (leftup-count (dec row) (dec col) 0 (get-in(get-board) [row col])))
  ([row col count player]   
      (if (= (get-in (get-board) [row col]) player)
        (if (and (> row 0)(> col 0))
          (recur (dec row) (dec col) (inc count) player)
          (inc count))
        count
      )))

(defn rightdown-count
  ([row col]
      (rightdown-count (inc row) (inc col) 0 (get-in(get-board) [row col])))
  ([row col count player]   
      (if (= (get-in (get-board) [row col]) player)
        (if (and (< row 5)(< col 5))
          (recur (inc row) (inc col) (inc count) player)
          (inc count))
        count
      )))

(defn horizontal-line? [row col] 
  (<= 3 (+ (left-count row col)(right-count row col))))

(defn vertical-line? [row col] 
  (<= 3 (under-count row col)))
  
(defn diagonal-line-rightup-leftdown? [row col]
  (<= 3 (+ (rightup-count row col)(leftdown-count row col))))

(defn diagonal-line-lefttup-rightdown? [row col]
   (<= 3 (+ (rightdown-count row col)(leftup-count row col))))

(defn winner? [row col]
  (cond
    (horizontal-line? row col) true
    (vertical-line? row col) true
    (diagonal-line-rightup-leftdown? row col) true
    (diagonal-line-lefttup-rightdown? row col) true
  :else false))

(defn drop-in-column [colnr]
  (let [rownr (free-row? colnr)]
    (if (not= rownr nil)
      (do 
        (set-piece rownr colnr)
        (next-player)
        (if (winner? rownr colnr)
          (other-player)
          nil)
  )nil)))

