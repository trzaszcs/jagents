(ns jagents.routes.stats
  (:require [compojure.core :refer :all]
            [jagents.stats :refer :all]
            [org.httpkit.server :refer :all]))

(defn get-stats!
  [request]
  {:status 200
   :body (str @stats-data)})


(defn get-stats-ws! [request]
  (with-channel request channel
    (on-close channel (fn [status] (println "channel closed: " status)))
    (on-receive channel (fn [data] ;; echo it back
                          (send! channel data)))))



(defroutes stats-routes
  (GET "/stats" [] get-stats!)
  (ANY "/stats-ws" [] get-stats-ws!))
