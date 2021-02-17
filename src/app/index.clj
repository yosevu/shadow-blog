(ns app.index
  (:require [hiccup.core :as hiccup]
            [clojure.string :as string]))

(def noscript? false)

(def html-data
  {:description "Generic description"
   :keywords    ["programming" "clojure" "clojurescript" "web development" "javascript"]
   :author      "Johnny Appleseed"
   :title       "An Awesome Static Site"})

(defn index-html
  [html-data]
  (string/join ["<!DOCTYPE html>"  (hiccup/html
                                     [:html {:lang "en"}
                                      [:head {}
                                       [:meta {:charset "UTF-8"}]
                                       [:meta {:name "description", :content (:description html-data)}]
                                       [:meta {:name "keywords", :content (string/join "," (:keywords html-data))}]
                                       [:meta {:name "author", :content (:author html-data)}]
                                       [:meta {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
                                       [:title {} (:title html-data)]
                                       [:link {:rel "stylesheet", :href "/css/main.css"}]]
                                      [:body {}
                                       (if noscript?
                                         [:noscript {} "You need to enable JavaScript to run this app."]
                                         nil)
                                       [:div {:id "app"}]
                                       [:script {:src "/js/main.js", :type "text/javascript"}]]])]))

(spit "./public/index.html" (index-html html-data))
