(ns jagents.ws-client
  (:require [jagents.stats :as stats]
            [clojure.data.json :as json]))


(defn- json-list-to-str
  [list]
  (map
   #(json/write-str %)
   list))

(defn- populate-stats
  [stats-list send-clb!]
  (doseq [value (json-list-to-str stats-list)] (send-clb! value)))

(defn new!
  [client-id send-clb!]
  (println "[WSC] new client ")
  (stats/register client-id #(populate-stats % send-clb!))
  (populate-stats (stats/all) send-clb!))

(defn on-message!
  [client-id msg send-clb!]
  (println "[WSC] got message from web-client " client-id msg)
  (send-clb! "{}"))

(defn on-close!
  [client-id status]
  (println client-id "[WSC] web-socket closed: " status)
  (stats/unregister client-id))
