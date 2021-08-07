(defproject decoy-link "0.1.0-SNAPSHOT"
  :description "Create redirects with custom link previews"
  :url "https://github.com/JohnnyJayJay/decoy-link"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-core "1.9.4"]
                 [ring/ring-devel "1.9.4"]
                 [bananaoomarang/ring-debug-logging "1.1.0"]
                 [selmer "1.12.44"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.12.5"]]
  :aot :all
  :ring {:handler decoy-link.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]
         :aot nil}})
