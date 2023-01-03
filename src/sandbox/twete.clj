(ns sandbox.twete
  (:require [clojure.string :as string])
  (:use [twitter.oauth]
        [twitter.callbacks]
        [twitter.callbacks.handlers]
        [twitter.api.restful])
  (:require [cheshire.core :refer :all])
  (:import [twitter.callbacks.protocols SyncSingleCallback]))

(def my-creds
  (let [creds (read-creds (str (System/getenv "HOME") "/.t.json"))]
    (make-oauth-creds
     (nth creds 0)
     (nth creds 1)
     (nth creds 2)
     (nth creds 3))))

(defn read-creds [file]
  (parse-string (slurp file)))

(defn get-following [name cursor client]
  (let [l (friends-list
            :client client
            :oauth-creds my-creds
            :params {
                     :screen-name name
                     :cursor      cursor
                     :count       200})]
    {:cursor ((l :body) :next_cursor)
     :users  ((l :body) :users)}))

(defn get-all-following [name client]
  (loop [lst [] next-page -1]
    (let [response (get-following name next-page client)]
      (do
        (Thread/sleep 1000)
        (if (= (response :cursor) 0)
          (concat lst (response :users))
          (recur (response :users) (response :cursor)))))))

(defn get-following-screen-names [name client]
  (map #(% :screen_name) (get-all-following name client)))

(defn loop-all-following [name client]
  (for [n (get-following-screen-names name client)]
    (do
      (Thread/sleep 5000)
      {n (get-following-screen-names n client)})))

(def t-handle "jack")

(defn main-loop []
  (do
    (with-open [client (http.async.client/create-client)]
      (println (get-all-following t-handle client)))
    (http.async.client/close (twitter.core/default-client))))

(defn -main "main" []
  main-loop)
