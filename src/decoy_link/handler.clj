(ns decoy-link.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.response :refer [Renderable render]]
            [selmer.parser :as selmer]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.response :refer [response]]
            [ring-debug-logging.core :as logger]))

(def file-root (or (System/getenv "DL_FILE_ROOT") "public"))
(def base-url (System/getenv "DL_BASE_URL"))

(selmer/cache-off!)
(selmer/set-resource-path! (or (System/getenv "DL_TEMPLATE_PATH") (str file-root "/templates")))

(def redirects (atom {}))

(defroutes app-routes
  (GET "/" [] (selmer/render-file "form.html" {}))
  (GET "/generate" {:keys [params]}
       (let [id (Long/toHexString (hash params))]
         (swap! redirects assoc id params)
         (selmer/render-file "generated.html" {:url (str base-url "/link/" id)})))
  (GET "/link/:id" [id]
       (when-some [data (@redirects id)]
         (selmer/render-file "redirect.html" data)))
  (route/files "/" {:root (str "resources/" file-root)})
  (route/not-found "Not Found"))

(def app
  (-> app-routes  wrap-keyword-params wrap-params logger/wrap-with-logger))
