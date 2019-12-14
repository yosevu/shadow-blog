(ns app.core
  (:require [reagent.core :as r]
            [shadow.resource :as rc]
            [clojure.reader :as reader]))

(def posts
  "Read posts data"
  (reader/read-string (rc/inline "../posts.edn")))

(defn app
  []
  [:div
   (for [post posts]
     [:div
      [:a.text-blue-900 {:href "http://localhost:3000"} "/" (first (:slug (:metadata post)))]
      [:div {:dangerouslySetInnerHTML {:__html (:html post)}}]])])

(defn ^:dev/after-load start
  []
  (r/render [app]
            (.getElementById js/document "app")))

(defn ^:export init
  []
  (start))
