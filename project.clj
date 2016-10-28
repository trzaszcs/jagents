(defproject jagents "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [hiccup "1.0.5"]
                 [ring "1.5.0"]
                 [ring-server "0.4.0"]
                 [http-kit "2.1.18"]
                 [org.clojure/data.json "0.2.6"]]
  :main jagents.server
  :profiles
  {:uberjar {:aot :all}
   :production {:ring {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev {:ring {:port 5000}  :dependencies [[ring/ring-mock "0.3.0"] [ring/ring-devel "1.5.0"]]}})
