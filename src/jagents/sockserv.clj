(ns jagents.sockserv
  (:require [clojure.java.io :as io]
            [clojure.data :as data])
  (:import [java.net ServerSocket]))

(def server (atom nil))
(def clients (atom []))

(defn- address
  [socket]
  (.toString (.getHostName (.getRemoteSocketAddress socket))))

(defn- consume
  [socket new-msg-callback]
  (try
    (with-open [stream (new java.io.BufferedReader (new java.io.InputStreamReader (.getInputStream socket)))]
      (with-local-vars [msg ""
                        client-id (rand-int 2000)
                        client-ip (address socket)]
        (while @msg
          (var-set msg (.readLine stream))
          (when @msg
            (println "received msg from" @client-id)
            (new-msg-callback @client-id @client-ip  @msg))
          )))
    (finally (.close socket)))
)

(defn- start-client
  [socket new-msg-callback]
  (swap!
   clients
   #(conj % (future (consume socket new-msg-callback)))))

(defn- socket-listen
  [port new-msg-callback]
  (with-open [server-sock (ServerSocket. port)]
    (while true 
      (let [sock (.accept server-sock)]
        (start-client sock new-msg-callback)))
  (println "server socket closed")))

(defn start
  [port new-msg-callback]
  (println "listening on port ..." port)
  (reset! server (future (socket-listen port new-msg-callback))))

(defn stop
  []
  (.close @@server)
  (reset! server nil)
  (println "server closed"))
