(ns kafka-sample.core
  (:require [onyx.test-helper]
            [onyx.plugin.kafka]
            [onyx.api])
  (:gen-class))

(def segments (atom 0))

(defn counter-task
  [segment]
  (let [current (swap! segments inc)]
    (println current)
    segment))

(def id (java.util.UUID/randomUUID))

(def env-config
  {:zookeeper/server? false
   :zookeeper/address "127.0.0.1:2181"
   :onyx/tenancy-id id})

(def peer-config
  {:zookeeper/address "127.0.0.1:2181"
   :onyx/tenancy-id id
   :onyx.peer/job-scheduler :onyx.job-scheduler/balanced
   :onyx.messaging/impl :aeron
   :onyx.messaging/peer-port 40200
   :onyx.messaging/bind-addr "localhost"})

(def workflow
  [[:read-messages :identity]
   [:identity :out]])

(def n-peers (count (set (mapcat identity workflow))))

(def catalog
  [{:onyx/name :read-messages
    :onyx/plugin :onyx.plugin.kafka/read-messages
    :onyx/type :input
    :onyx/medium :kafka
    :onyx/min-peers 1
    :onyx/max-peers 1
    :onyx/batch-size 100
    :kafka/topic "test-data"
    :kafka/group-id "onyx-consumer3"
    :kafka/zookeeper "127.0.0.1:2181"
    :kafka/offset-reset :smallest
    :kafka/force-reset? true
    :kafka/deserializer-fn :onyx.tasks.kafka/deserialize-message-edn
    :kafka/wrap-with-metadata? false
    :onyx/doc "Reads messages from a Kafka topic"}
    {:onyx/name :identity
     :onyx/fn :clojure.core/identity
     :onyx/batch-size 100
     :onyx/type :function}
   {:onyx/name :out
    :onyx/plugin :onyx.test-helper/dummy-output
    :onyx/type :output
    :onyx/medium :null
    :onyx/min-peers 1
    :onyx/batch-size 100
    :onyx/doc "Writes segments to dev null"}])

(def lifecycles
  [{:lifecycle/task :read-messages
    :lifecycle/calls :onyx.plugin.kafka/read-messages-calls}])

(defn -main
  [& args]
  (let [env         (onyx.api/start-env env-config)
        peer-group  (onyx.api/start-peer-group peer-config)
        v-peers     (onyx.api/start-peers (* 2 n-peers) peer-group)
        job-id      (:job-id
                     (onyx.api/submit-job
                      peer-config
                      {:catalog catalog
                       :workflow workflow
                       :lifecycles lifecycles
                       :task-scheduler :onyx.task-scheduler/balanced}))]
    (println job-id)
    (println "Stopping")
    (onyx.api/await-job-completion peer-config job-id)
    (doseq [v-peer v-peers]
      (onyx.api/shutdown-peer v-peer))
    (onyx.api/shutdown-peer-group peer-group)
    (onyx.api/shutdown-env env)))

