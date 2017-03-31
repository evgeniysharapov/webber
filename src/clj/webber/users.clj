(ns webber.users
  (:require [monger.core :as mongo]
            [monger.collection :as collection]
            monger.json
            [webber.datasource :refer [ds]]))

(defn list []
  (collection/find-maps (ds) "users"))

(defn save [user]
  (collection/save (ds) "users" user))

(defn update [id user]
  (collection/save (ds) "users" id user))

(defn delete [id]
  (collection/remove-by-id (ds) "users" id))

(defn find-by-id [id]
  (collection/find-map-by-id (ds) "users" id))

(defn find-by-username [username]
  (collection/find-one-as-map (ds) "users" {:username username}))


