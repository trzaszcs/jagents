(ns jagents.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [jagents.routes.home :refer [home-routes]]
            [jagents.routes.stats :refer [stats-routes]]
            [jagents.stats :as stats]))

(defn init []
  (println "jagents is starting")
  (stats/listen 9999))

(defn destroy []
  (println "jagents is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes stats-routes app-routes)
      (handler/site)
      (wrap-base-url)))
