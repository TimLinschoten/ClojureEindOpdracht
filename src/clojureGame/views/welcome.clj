(ns clojureGame.views.welcome
  (:require [clojureGame.views.common :as common]
            [clojureGame.models.logica :as logica]
            [noir.content.getting-started])
  (:use noir.core))

(defpartial createField[name value type]
  [:td [:input {:name name
            :value value
            :type type}]])

(defpartial showScreen [board]
  (createField "1" "1" "button")
  (createField "2" "2" "button")
  (createField "3" "3" "button")
  (createField "4" "4" "button")
  (createField "5" "5" "button")
  (createField "6" "6" "button")
  [:p ] board)

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to clojure connect four"]
           [:p "Player " "(logica/getPlayer)"]
         (showScreen [[\- \- \- \- \- \-]
                      [\- \- \- \- \- \-]
                      [\- \- \- \- \- \-]
                      [\- \- \- \- \- \-]
                      [\- \- \- \- \- \-]
                      [\- \- \- \- \- \-]])))