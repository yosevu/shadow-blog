(ns app.core
  (:require [app.resource :as rc]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [reagent.core :as r]
            ["highlight.js" :as hljs]))

;; State

(def state (r/atom {:posts (rc/get-posts "src/posts")}))

;; Routes

(def app-routes
  ["/" {"" :index
        ["" :post-id] :post
        ["" :page-id] :page
        true :not-found}])

(defn current-page []
  (:current-page @state))

;; Views

(defn index []
  [:div.mt-8
   (for [post (:posts @state)]
     [:div.my-4.py-4 {:key (first (:id (:metadata (last post))))}
      [:a.border-b.border-gray-900.hover:border-transparent {:href (bidi/path-for
                  app-routes
                  :post
                  :post-id
                  (first (:id (:metadata (last post)))))}
       (first (:title (:metadata (last post))))]])])
   ;; [:p
    ;; [:a {:href (bidi/path-for app-routes :page :page-id "another-page")} "another page"]]])

(defn post [post-id]
  [:div.mt-4.pt-4
   ;; (.log js/console (.highlight hljs "clj" (:html ((keyword post-id) (:posts @state))) true))
   [:article {:dangerouslySetInnerHTML {:__html (:html ((keyword post-id) (:posts @state)))}}]])

;; (defn page [page-id]
;;   [:div
;;    [:p "another page"]
;;    [:a {:href (bidi/path-for app-routes :index)} "index"]])

(defn not-found []
  [:div
   [:p "Not Found"]])

;; Routing

(defn pages [path]
  ;; (js/alert (:current-page @state))
  (case (:handler (:current-page @state))
    :index [index]
    ;; :page [page :another-page] FIXME
    :post [post (:post-id (:route-params (:current-page @state)))]
    [not-found]))

(defn set-page! [match]
  (swap! state assoc :current-page match))

(defn app []
  [:div.container.mx-auto.max-w-2xl.m-4.p-4.text-gray-900
   [:div
    [:h1.text-2xl.leading-snug.underline.hover:no-underline [:a {:href (bidi/path-for app-routes :index)} "Yosevu's strange loop"]]
    [:code.text-sm "(-> thoughts read eval print)"]]
    (pages current-page)])

(def history
  (pushy/pushy set-page! (partial bidi/match-route app-routes)))

;; Start

(defn ^:export start! []
  (pushy/start! history)
  (r/render [app]
            (.getElementById js/document "app")))
