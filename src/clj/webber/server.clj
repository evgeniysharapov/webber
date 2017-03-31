(ns webber.server
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :refer [resources not-found]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger wrap-with-body-logger]]
            [ring.middleware.json :refer :all]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :refer [resource-response response]]

            [webber.users :as users]
            [webber.auth :as auth])
  (:use ring.middleware.reload))

(defroutes static-routes
  (GET "/" _
    (-> "public/index.html"
        io/resource
        io/input-stream
        response
        (assoc :headers {"Content-Type" "text/html; charset=utf-8"}))))

(def web
  (->
   static-routes
   (wrap-defaults site-defaults)
   wrap-with-logger
   wrap-gzip))

(defroutes users-api
  (GET "/" [] (users/list))
  (POST "/" [body] (users/save body))
  (GET "/:id" [id] (users/find-by-id id))
  (PUT "/:id" [id body] (users/update body))
  (DELETE "/:id" [id] (users/delete id)))

(defroutes auth-api
  (POST "/" [body] (let [[ok? res] (auth/create-auth-token body)]
                      )))

(defroutes api-routes
  (context "/users" [] users-api))

(def api
  (->
   api-routes
   (wrap-restful-format)
   (wrap-defaults api-defaults )
   wrap-with-logger
   wrap-json-response
   (wrap-json-body {:keywords? true})))

(defroutes app
  (context "/api" [] api)
  web
  (resources "/")
  (not-found "Not Found"))

(defn -main [& [port]]
  (let [port 5555]
    (run-jetty app {:port port :join? false})))
