(ns clojure.views.welcome
  (:require [clojure.views.common :as common]
            [clojure.models.logica :as logica]
            [noir.content.getting-started])
  (:use noir.core))

(defpartial testEen []
  [:div 
   [:p "Test 1"]])

(defpartial testTwee []
  [:div 
   [:p (logica/sum 1 2)]])

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to clojure"]
         (testEen)
         (testTwee)))
