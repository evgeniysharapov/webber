(ns webber.datasource
  (:require [monger.core :as mongo]
            [monger.collection :as collection]
            monger.json)
  (:import [com.mongodb MongoOptions ServerAddress]))

(defonce db
  (let [^MongoOptions opts (mongo/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa  (mongo/server-address "dockerhost" 27017)
        conn               (mongo/connect sa opts)
        db (mongo/get-db conn "testapp")]
    db))

(defn ds []
  db)
