(ns jagents.handler
  (:use [org.httpkit.server :only [run-server]])
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [jagents.routes.stats :refer [stats-routes]]
            [jagents.stats :as stats]
            [ring.middleware.reload :as reload]
            [compojure.handler :refer [site]]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes stats-routes app-routes)
      (handler/site)
      (wrap-base-url)))

(defn in-dev?
  [args]
  true)

(defn -main [& args] ;; entry point, lein run will pick up and start from here
  (stats/listen 9999)
  (let [handler (if (in-dev? args)
                  (reload/wrap-reload (site #'app)) ;; only reload when dev
                  (site app))]
    (run-server handler {:port 8080})))
