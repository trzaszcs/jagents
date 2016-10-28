(ns jagents.routes.stats
  (:require [compojure.core :refer :all]
            [jagents.stats :as stats]
            [org.httpkit.server :refer :all]
            [clojure.data.json :as json]))


(defn- gen-key!
  []
  (str (java.util.UUID/randomUUID)))


(defn- json-list-to-str
  [list]
  (map
   #(json/write-str %)
   list))

(defn- populate-stats
  [stats-list channel]
  (doseq [value (json-list-to-str stats-list)] (send! channel value)))

(defn get-stats! [request]
  (let [client-id (keyword (gen-key!))]
    (println "new web-socket" client-id)
    (with-channel request channel
      (on-close channel (fn [status]
                          (println client-id " web-socket closed: " status)
                          (stats/unregister client-id)))
      (populate-stats (stats/all) channel)
      (stats/register client-id #(populate-stats % channel)))))

(defroutes stats-routes
  (GET "/stats-ws" [] get-stats!))
