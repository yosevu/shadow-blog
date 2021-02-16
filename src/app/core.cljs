(ns app.core
  (:require ;[reagent.core :as r]
   [reagent.dom :as dom]))

(defn app []
  [:div {:class '[container mx-auto max-w-2xl m-4 p-4 mt-10 text-gray-900]}
   [:header
    [:h1 {:class '[text-gray-900 text-xl leading-snug tracking-wide]}
     [:a {:class      '[ border-b border-transparent hover:border-gray-900]
          :aria-label "Yosev, strange loop"}
      "Yosevu.strange-loop"]]
    [:code {:class      '[text-xs tracking-tight]
            :aria-label "Thread thoughts, read, evaluate, print"}
     "(-> thoughts read eval print)"]]])

;; Start

(defn ^:export start! []
  (dom/render [app]
              (.getElementById js/document "app")))
