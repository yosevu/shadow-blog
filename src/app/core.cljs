(ns app.core
  (:require [reagent.core :as r]
            [app.resource :as resource]))

(defn app
  []
  (let [posts (resource/get-posts "src/posts")]
       ;; (js/alert posts)
       [:div
         (for [post posts]
           [:div
            [:div (:metadata post)]
            [:div (:html post)]])]))


(defn ^:export start
  []
  (r/render
   [app]
   (.getElementById js/document "app")))
