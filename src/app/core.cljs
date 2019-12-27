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
        true :not-found}])

(defn current-page []
  (:current-page @state))

;; Views

(defn index []
  [:main.mt-12
   (for [post (:posts @state)]
     [:div.my-4.py-4 {:key (first (:id (:metadata (last post))))}
      [:a.text-xl.font-semibold.border-b-2.border-gray-900.hover:border-transparent {:href (bidi/path-for
                  app-routes
                  :post
                  :post-id
                  (first (:id (:metadata (last post)))))}
       (first (:title (:metadata (last post))))]
      [:div
       [:time.text-sm (first (:date (:metadata (last post))))]]
      [:p (first (:subtitle (:metadata (last post))))]])])

(defn not-found []
  [:div.mt-12
   [:p "Not Found"]])

(defn- highlight-block [node]
  (if (nil? (.querySelector (r/dom-node node) "pre code"))
    nil
    (doseq [block (array-seq (.querySelectorAll (r/dom-node node) "pre code"))]
      (.highlightBlock hljs block))))

(defn post [post-id]
  (r/create-class
   {:component-did-mount highlight-block
    :reagent-render
    (fn []
      (if (nil? ((keyword post-id) (:posts @state)))
        (not-found)
        [:div.mt-4.pt-4
         [:article {:dangerouslySetInnerHTML {:__html (:html ((keyword post-id) (:posts @state)))}}]]))}))

;; Routing

(defn pages [path]
  ;; (js/alert (:current-page @state))
  (case (:handler (:current-page @state))
    :index [index]
    :post [post (:post-id (:route-params (:current-page @state)))]
    [not-found]))

(defn set-page! [match]
  (swap! state assoc :current-page match))

(defn app []
  [:div.container.mx-auto.max-w-2xl.m-4.mt-8.p-4.text-gray-900
   [:header
    [:h1.text-2xl.font-semibold.leading-snug.underline.hover:no-underline [:a
                                                             {:href (bidi/path-for app-routes :index)
                                                              :aria-label "Yosev, strange loop"}
                                                             "Yosevu.strange-loop"]]
    [:code.text-sm {:aria-label "Thread thoughts, read, evaluate, print"}"(-> thoughts read eval print)"]]
   (pages current-page)])

(def history
  (pushy/pushy set-page! (partial bidi/match-route app-routes)))

;; Start

(defn ^:export start! []
  (pushy/start! history)
  (r/render [app]
            (.getElementById js/document "app")))
