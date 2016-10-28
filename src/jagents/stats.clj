(ns jagents.stats
  (:require [clojure.data :as data]
            [jagents.sockserv :as sockserv]
            [clojure.data.json :as json]))

(def stats-data (atom []))

(defn all
  []
  @stats-data)

(defn register
  [client-id clb]
  (add-watch
   stats-data
   client-id
   (fn [k r old new]
     (clb (remove nil? (get (data/diff old new) 1))))))

(defn unregister
  [client-id]
  (remove-watch
   stats-data
   client-id))

(defn- add-message
  [msg]
  (swap!
   stats-data
   (fn [stats]
     (conj stats msg))))

(defn- enhance
  [message ip-addr]
  (let [msg-as-map (json/read-str message)]
    (assoc msg-as-map :source-ip ip-addr)))

(defn on-message
  [id ip msg]
  (add-message (enhance msg ip)))

(defn- socket-listen
  [port]
  (sockserv/start port on-message))

(defn listen
  [port]
  (println "listening on port ..." port)
  (socket-listen port))
