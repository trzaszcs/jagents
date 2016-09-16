(ns jagents.routes.stats
  (:require [compojure.core :refer :all]
            [jagents.stats :as stats]
            [org.httpkit.server :refer :all]))


(defn- populate-stats
  [stats-list channel]
  (doseq [value stats-list] (send! channel value)))

(defn get-stats! [request]
  (with-channel request channel
    (on-close channel (fn [status] (println "channel closed: " status)))
    (populate-stats (stats/all) channel)
    (stats/on-change #(populate-stats % channel))))

(defroutes stats-routes
  (GET "/stats-ws" [] get-stats!))
