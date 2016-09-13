(defproject kafka-sample "0.1.0"
  :description ""
  :url "http://onyxplatform.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ["-Xmx6g"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.onyxplatform/onyx "0.9.10-beta4"]
                 [org.onyxplatform/onyx-kafka "0.9.10.0-beta3"]
                 [environ "1.0.1"]
                 [com.cognitect/transit-clj "0.8.285"]]
  :plugins [[lein-update-dependency "0.1.2"]])
