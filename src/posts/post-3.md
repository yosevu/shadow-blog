title: Welcome to Post 3
subtitle: This post has a code snippet.
date: 2019-12-03
id: post-3
tags: tag1 tag2

# Post 3

## Clojure

```clojure
;; a typical entry point of a Clojure program
(defn -main                   ; function name
  [& args]                    ; parameter vector (`-main` is a variadic function)
  (println "Hello, World!"))  ; function body
```

## JavaScript

```javascript
function factorial(n) {
    if (n === 0)
        return 1; // 0! = 1

    return n * factorial(n - 1);
}

factorial(3); // returns 6
```
