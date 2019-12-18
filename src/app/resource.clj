(ns app.resource
  (:require [clojure.java.io :as io]
            [markdown.core :refer [md-to-html-string-with-meta]]))

(defn slurp-dir [path]
  (map slurp (rest (file-seq (io/file path)))))

(defn into-map [posts-map post]
  (assoc posts-map (keyword (first (:id (:metadata post)))) post))

(defmacro get-posts [path]
  (reduce into-map {} (map md-to-html-string-with-meta (slurp-dir path))))
