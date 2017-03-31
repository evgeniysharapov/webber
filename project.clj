(defproject webber "0.1.0-SNAPSHOT"
  :description "Webber (Andrew Lloyd) is another incarnation of Composer tool"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.89" :scope "provided"]
                 [ring "1.5.0"]
                 [ring/ring-defaults "0.2.3"]
                 [bk/ring-gzip "0.2.1"]
                 [ring.middleware.logger "0.5.0"]
                 [ring-middleware-format "0.7.2"]
                 [ring/ring-json "0.4.0"]
                 [compojure "1.5.2"]
                 [buddy/buddy-auth "1.4.1"]
                 [reagent "0.6.0"]
                 [com.novemberain/monger "3.1.0"]]

  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  
  :main webber.server

  :plugins [[lein-cljsbuild "1.1.5"]]

  :cljsbuild {:builds
              [{:id "app"
                :source-paths ["src/cljs" "src/cljc"]
                :figwheel true
                :compiler {:main webber.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/webber.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :pretty-print true}}]}
  )
