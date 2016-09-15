(ns jagents.routes.home
  (:require [compojure.core :refer :all]
            [jagents.views.layout :as layout]))

(defn home []
  (layout/common [:h1 "Hello World 123 !"]))

(defroutes home-routes
  (GET "/" [] (home)))
