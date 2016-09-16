(ns jagents.stats
  (:require [clojure.java.io :as io]
            [clojure.data :as data])
  (:import [java.net ServerSocket]))

(defn- receive
  "Read a line of textual data from the given socket"
  [socket]
  (.readLine (io/reader socket)))

(def stats-data (atom []))


(defn all
  []
  @stats-data)

(defn on-change
  [clb]
  (add-watch
   stats-data
   :my-key
   (fn [k r old new]
     (clb (remove nil? (get (data/diff old new) 1))))))


(defn- add-message
  [msg]
  (swap!
   stats-data
   (fn [stats]
     (conj stats msg))))

(defn- socket-listen
  [port]
    (with-open [server-sock (ServerSocket. port)
                sock (.accept server-sock)]
      (while true
        (let [msg (receive sock)]
          (println "received" msg)
          (add-message msg)))))

(defn listen
  [port]
  (println "listening on port ..." port)
  (future (socket-listen port)))
