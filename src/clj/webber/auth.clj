(ns webber.auth
  (:require [buddy.sign.jwt :as jwt]
            [buddy.core.hash :as h]
            [buddy.core.codecs :refer [bytes->hex hex->bytes]]
            [buddy.core.bytes :as b]
            [clj-time.core :as t]
            [clojure.java.io :as io]
            [webber.users :as users]))

(defn encode-password
  [password]
  (bytes->hex (h/sha256 password)))

(defn auth-user
  [credentials]
  (let [user (users/find-by-username (:username credentials))]
    [(b/equals? (encode-password (:password credentials)) (:password user)) 
     user]))

(defn create-auth-token
  [credentials]
  (let [[ok? res] (auth-user credentials)]   
    (if ok?
      [true {:token (jwt/sign res "secret-key")}]
      [false res])))
