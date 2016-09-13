
### Goal

The goal of this project is figure out the setting for kafka plugin for onyx which give the maximum throughput. Optimising Kafka is out of scope for this work.

## Setup

- Download the latest version from [here](https://kafka.apache.org/downloads.html).

- Unzip the archive and add `delete.topic.enable = true` to `config/server.properties`.

- Start Zookeeper - `bin/zookeeper-server-start.sh config/zookeeper.properties`

- Start Kakfa - `bin/kafka-server-start.sh config/server.properties`

- Clone [simplekafka](https://github.com/ducky427/simplekafka)

- Go the location where simplekafka is clonned and run `lein run -m simplekafka.produce` to publish 50,000 messages to kafka.

- In the same location, run `lein run -m simplekafka.consume` to get a baseline for consuming those 50,000 messages. On my laptop it is about 72 seconds.

- Clone this repo and where it is cloned run `lein run -m kafka-sample.core` to get timing for using kafka with onyx.