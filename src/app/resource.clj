(ns app.resource
  (:require [clojure.java.io :as io]
            [markdown.core :refer :all ]))

(defn slurp-dir
  "Get posts from directory."
  [path]
    (map slurp (rest (file-seq (io/file path)))))

(defn map-posts [post-map post]
  (assoc post-map (keyword (first (:id (:metadata post)))) post))

(defmacro get-posts
  [path]
  ;; (prn (md-to-html-string-with-meta (slurp (io/file path))))
  ;; (md-to-html-string-with-meta (slurp (io/file path)))) ; {:metadata nil, :html "<h1>Post 1</h1><p>This my first post.</p><pre><code class=\"clojure\">&#40;println &quot;Hello, World!&quot;&#41;\n</code></pre>"}
  ;; (prn (count (map md-to-html-string-with-meta (get-posts path))))
  ;; (prn (map md-to-html-string-with-meta (get-posts path)))
  ;; (prn (map md-to-html-string-with-meta (slurp-dir path)))
  ;; (vec (map md-to-html-string-with-meta (slurp-dir path))))
  (prn (reduce map-posts {} (map md-to-html-string-with-meta (slurp-dir path))))
  (reduce map-posts {} (map md-to-html-string-with-meta (slurp-dir path))))
  ;; (vec (map md-to-html-string-with-meta (slurp-dir path))))
