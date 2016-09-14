(defproject kafka-sample "0.1.0"
  :description ""
  :url "http://onyxplatform.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ^:replace ["-server" "-Xmx6g"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.onyxplatform/onyx "0.9.10-beta5"]
                 [org.onyxplatform/onyx-kafka "0.9.10.0-20160914.183040-6"]
                 [ymilky/franzy-admin "0.0.1" :exclusions [org.slf4j/slf4j-log4j12]]
                 [cheshire "5.5.0"]
                 [environ "1.0.1"]
                 [com.cognitect/transit-clj "0.8.285"]]
  :plugins [[lein-update-dependency "0.1.2"]])
