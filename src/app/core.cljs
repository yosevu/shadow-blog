(ns app.core
  (:require [app.resource :as rc]
            [bidi.bidi :as bidi]
            [clojure.string :refer [split]]
            [pushy.core :as pushy]
            [reagent.core :as r]
            [reagent.dom :as dom]
            ["highlight.js" :as hljs]))

;; State

(def state (r/atom {:posts (rc/get-posts "src/posts")}))

;; Routes

(def app-routes
  ["/" {"" :index
        ["" :post-id] :post
        ["tag/" :tag-id] :tag
        true :not-found}])

(defn current-page []
  (:current-page @state))

;; Views

(defn tag-template [tag]
  [:a {:class '[text-blue-600 text-sm tl ml-3 border-b border-transparent hover:border-blue-600]
       :key   tag
       :href  (bidi/path-for
                app-routes
                :tag
                :tag-id
                tag)}
   tag])

(defn tags [post]
  [:div {:class '[-mt-px]}
   (for [tag (split (first (:tags (:metadata post))) " ")]
     (tag-template tag))])

;; {:class '[]}

(defn index [posts]
  [:main {:class '[mt-12]}
   (for [post posts]
     [:div  {:class '[my-4 py-4]
             :key   (first (:id (:metadata (last post))))}
      [:a
       {:class '[text-lg font-medium border-b border-transparent hover:border-gray-900]
        :href  (bidi/path-for
                 app-routes
                 :post
                 :post-id
                 (first (:id (:metadata (last post)))))}
       (first (:title (:metadata (last post))))]
      [:div {:class '[flex items-center mt-1]}
       [:time
        {:class '[text-sm tracking-wide mt-px]}
        (first (:date (:metadata (last post))))]
       (tags (last post))]
      [:p
       {:class '[tracking-wide mt-2]} (first (:subtitle (:metadata (last post))))]])])

(defn not-found []
  [:div {:class '[mt-12]}
   [:p "Not Found"]])

(defn- highlight-block [node]
  (if (nil? (.querySelector (dom/dom-node node) "pre code"))
    nil
    (doseq [block (array-seq (.querySelectorAll (dom/dom-node node) "pre code"))]
      (.highlightBlock hljs block))))

(defn post [post-id]
  (r/create-class
    {:component-did-mount highlight-block
     :reagent-render
     (fn []
       (if (nil? ((keyword post-id) (:posts @state)))
         (not-found)
         [:div {:class '[mt-12]}
          [:h2 {:class '[text-2xl font-medium]}
           (first (:title (:metadata ((keyword post-id) (:posts @state)))))]
          [:div {:class '[flex mt-2 items-center]}
           [:time {:class '[text-sm tracking-wide mt-px]} (first (:date (:metadata ((keyword post-id) (:posts @state)))))]
           (tags ((keyword post-id) (:posts @state)))]
          [:article {:class                   '[markdown mt-6]
                     :dangerouslySetInnerHTML {:__html (:html ((keyword post-id) (:posts @state)))}}]]))}))

(defn filter-by-tag [posts tag-id]
  (filter #(re-find (re-pattern tag-id) (first (:tags (:metadata (val %))))) posts))

;; Routing

(defn pages [path]
  (case (:handler (:current-page @state))
    :index [index (:posts @state)]
    :post  [post (:post-id (:route-params (:current-page @state)))]
    :tag   [index (filter-by-tag (:posts @state) (:tag-id (:route-params (:current-page @state))))]
    [not-found]))

(defn set-page! [match]
  (swap! state assoc :current-page match))

(defn app []
  [:div {:class '[container mx-auto max-w-2xl m-4 p-4 mt-10 text-gray-900]}
   [:header
    [:h1 {:class '[text-gray-900 text-xl leading-snug tracking-wide]}
     [:a {:class      '[ border-b border-transparent hover:border-gray-900]
          :href       (bidi/path-for app-routes :index)
          :aria-label "Yosev, strange loop"}
      "Yosevu.strange-loop"]]
    [:code {:class      '[text-xs tracking-tight]
            :aria-label "Thread thoughts, read, evaluate, print"}
     "(-> thoughts read eval print)"]]
   (pages current-page)])

(def history
  (pushy/pushy set-page! (partial bidi/match-route app-routes)))

;; Start

(defn ^:export start! []
  (pushy/start! history)
  (dom/render [app]
              (.getElementById js/document "app")))
