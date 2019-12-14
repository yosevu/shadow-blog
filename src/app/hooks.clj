(ns app.hooks
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [markdown.core :refer :all]))

(defn get-posts
  "Get posts from directory."
  []
  (let [dir (io/file "src/posts")]
    (map slurp (rest (file-seq dir)))))

(defn posts
  "Convert markdown posts to html."
  []
  (map md-to-html-string-with-meta (get-posts)))

(defn compile-posts
  {:shadow.build/stage :flush}
  [state]
  (spit "src/posts.edn" (vec (posts)))
  state)
