(ns jagents.server
  (:use [org.httpkit.server :only [run-server]])
  (:require [compojure.core :refer [defroutes routes]]
            [jagents.handler :refer [app]]
            [ring.middleware.reload :as reload]
            [jagents.stats :as stats]
            [compojure.handler :refer [site]])
  (:gen-class))


(defonce server (atom nil))

(defn start
  [dev-mode]
  (stats/listen 9999)
  (let [handler (if dev-mode
                  (reload/wrap-reload (site #'app))
                  (site app))]
    (reset! server (run-server handler {:port 8080}))))

(defn stop
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))


(defn -main [& args]
  (start false))
