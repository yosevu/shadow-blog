title: Build a Single-page Blog with ClojureScript
subtitle: How to Build a Single-page Blog with ClojureScript and shadow-cljs.
date: 2019-12-28
id: how-to-build-a-single-page-blog-with-clojurescript
tags: clojurescript shadow-cljs

I wanted to learn ClojureScript by building something that I would personally
use. My personal blog seemed like a failsafe choice, so here we are.

### Exploring the Clojure Ecosystem

In which:

- I chose not to use existing Clojure static site generators
- I discovered shadow-cljs 🖤
- I discovered Tailwind CSS 🖤

### Choosing the Stack

In which I chose the tools I wanted to work with.

- shadow-cljs
- Reagent
- Tailwind CSS

### Markdown to Macros

In which I learned:

- Key differences between Clojure and
ClojureScript
- How to leverage Clojure macros in CojureScript
- How to transform markdown files to HTML


```clojure
(ns app.resource
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
```

### Style and Build Configuration

In which:

- I integrated Tailwind CSS into my workflow
- I customized development and production builds

### Client-side Routing

In which I learned more about Clojure, ClojureScript, and Reagent.

### Syntax Highlighting

In which I gained a deeper understanding of Reagent.

### Deployment

In which I learned how to deploy a ClojureScript SPA.
