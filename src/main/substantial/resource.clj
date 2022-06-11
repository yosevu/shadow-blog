(ns substantial.resource
  (:require [clojure.java.io :as io]
            [markdown.core :refer [md-to-html-string-with-meta]]))

(defn slurp-dir [path]
  (map slurp (rest (file-seq (io/file path)))))

(defn md->html [files]
  (map md-to-html-string-with-meta files))

(defn sort-by-date [posts]
  (reverse (sort-by #(get-in (:metadata %) [:date]) posts)))

(defn into-map [posts-map post]
  (assoc posts-map (keyword (first (:id (:metadata post)))) post))

(defmacro get-posts [path]
  (reduce into-map {} (sort-by-date (md->html (slurp-dir path)))))
