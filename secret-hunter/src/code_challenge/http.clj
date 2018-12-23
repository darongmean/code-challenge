(ns code-challenge.http
  (:require
    [clj-http.client :as http-client]
    [clj-http.conn-mgr :as conn-mgr]
    [clojure.core.async :as async]
    [taoensso.timbre :as timbre]))


(defn mk-rest-client
  ([base-url]
   (let [session (-> (http-client/get (str base-url "/get-session" {:as :auto}))
                     (:body)
                     (:session))]
     (mk-rest-client
       base-url
       session
       (conn-mgr/make-reuseable-async-conn-manager {:timeout 5 :threads 32 :default-per-route 128}))))
  ([base-url session connection-manager]
   (fn [ch id]
     (http-client/get
       (str base-url "/" id)
       {:as                 :auto
        :async?             true
        :connection-manager connection-manager
        :retry-handler      (fn [ex try-count _]
                              (timbre/warn {:uri       (str base-url "/" id)
                                            :try-count try-count
                                            :ex        ex})
                              (if (> try-count 5) false true))
        :headers            {"session" session}}
       #(do
          (timbre/debug {:uri      (str base-url "/" id)
                         :response %})
          (async/>!! ch (:body %)))
       #(do
          (timbre/error {:uri (str base-url "/" id)
                         :ex  %}))))))

