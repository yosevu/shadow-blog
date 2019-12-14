(ns app.core
  (:require [reagent.core :as r]))

(defn app
  []
  [:div.text-purple-900 "Hello, world!"])

(defn ^:dev/after-load start
  []
  (r/render [app]
            (.getElementById js/document "app")))

(defn ^:export init
  []
  (start))
