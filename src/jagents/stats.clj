(ns jagents.stats
  (:require [clojure.java.io :as io])
  (:import [java.net ServerSocket]))

(defn- receive
  "Read a line of textual data from the given socket"
  [socket]
  (.readLine (io/reader socket)))

(def stats-data (atom []))

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
