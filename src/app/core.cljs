(ns app.core
  (:require [app.resource :as rc]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [reagent.core :as r]))

;; State

(def state (r/atom { :posts (rc/get-posts "src/posts") }))

;; Routes

(def app-routes
  ["/" {"" :index
        ["" :post-id] :post
        ["" :page-id] :page}])

(defn current-page []
  (:current-page @state))

;; Views

(defn index []
  [:div
   [:h2 "index"]
   (for [post (:posts @state)]
     [:div {:key (:slug (:metadata post))}
      [:a {:href (bidi/path-for
                  app-routes
                  :post
                  :post-id
                  (first (:slug (:metadata post))))}
       (first (:slug (:metadata post)))]])])

;; (defn index []
;;   [:div [:h2 "/"]
;;    ;; [:div [:a {:href (bidi/path-for app-routes :about)} "/about"]]
;;    [:div [:a {:href (bidi/path-for app-routes :post :post-id :post-1)} "/:post-id" ]]])

;; (defn about []
;;   [:div [:h2 "/about"]
;;    [:div [:a {:href (bidi/path-for app-routes :index)} "/"]]])

(defn post [post-id]
  [:div
   [:div
    [:a {:href (bidi/path-for app-routes :index)} "index"]
    [:h2 post-id]
    [:article "content"]]])

;; Routing

(defn pages [path]
  (case (:handler (:current-page @state))
    :index [index]
    ;; :about [about]
    :post [post (:post-id (:route-params (:current-page @state)))]
    [[:div "default page"]]))

(defn set-page! [match]
  (swap! state assoc :current-page match))

(def history
  (pushy/pushy set-page! (partial bidi/match-route app-routes)))

(defn app []
  [:div (pages current-page)])

;; Start

(defn ^:export start! []
  (pushy/start! history)
  (r/render [app]
   (.getElementById js/document "app")))
