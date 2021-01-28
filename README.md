# shadow-static

A minimal, single-page blog starter built with shadow-cljs, Reagent, and Tailwind CSS.

```
.
├── README.md
├── netlify.toml
├── package.json
├── postcss.config.js
├── public
│   └── index.html
├── shadow-cljs.edn
├── src
│   ├── app
│   │   ├── core.cljs
│   │   ├── resource.clj
│   │   └── resource.cljs
│   ├── css
│   │   ├── custom.css
│   │   ├── main.css
│   │   ├── nord.css
│   │   └── tailwind.css
│   └── posts
│       ├── build-a-single-page-blog-with-clojurescript.md
│       ├── markdown-samples.md
│       ├── post-1.md
│       ├── post-2.md
│       └── set-up-tailwind-css-in-a-shadow-cljs-project.md
├── tailwind.config.js
├── yarn-error.log
└── yarn.lock
```

## Develop

Before running for the first time run:
`yarn install`

### CLI

`yarn start`

### [CIDER](https://cider.mx/) (Emacs)

- `cider-jack-in-cljs`

cider-connect-sibling-cljs option

-  Select the **shadow** 
-  Select **:app**

Visit 'http://localhost:3000' in a browser? (y or n)

- Enter `y`

## Release

`yarn release`

See [Generating Production Code](https://shadow-cljs.github.io/docs/UsersGuide.html#release) for more information.
