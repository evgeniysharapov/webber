(ns webber.core
  (:require [reagent.core :as reagent]))

(defn app
  []
  [:div
   [:h1 "Hello, world!"]])

(reagent/render [app] (js/document.querySelector "#app"))
