(ns jagents.routes.stats
  (:require [compojure.core :refer :all]
            [org.httpkit.server :refer :all]
            [jagents.ws-client :as wsc]))


(defn- gen-key!
  []
  (str (java.util.UUID/randomUUID)))


(defn get-stats! [request]
  (let [client-id (keyword (gen-key!))]
    (with-channel request channel
      (let [send-clb! (fn [msg] (send! channel msg))]
        (wsc/new! client-id send-clb!)
        (on-close channel (fn [status](wsc/on-close! client-id status)))
        (on-receive channel (fn [msg] (wsc/on-message! client-id msg send-clb!))))
)))

(defroutes stats-routes
  (GET "/stats-ws" [] get-stats!))
