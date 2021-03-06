# Distributed Deep Learning

This repo contains the code for End to End Distributed Deep Learning Process Pipeline.

The Process happens in 7 steps:

1. Real-Time Streaming Data or Batch Data is captured using [Debezium](https://github.com/debezium/debezium).
2. Captured Stream or Batch Data is pushed as Apache Kafka Topics using Kafka Connectors.
3. Apache Flink is used to perform ETL operations.
4. The Streaming/Batch Data Predictions are received from Models Deployed using TensorFlow Serving on Docker.
5. Frequent Data Caching is achieved with [RocksDB](https://github.com/facebook/rocksdb).
6. Once the required predictions are made, all the data is pushed into [Apache Druid](https://github.com/apache/druid) where further processing takes place.
7. The data present in Druid is now very powerful and can be used for making personalized predictions, cancellation probabilities, time-series forecasting etc.

<h4>Architecture Diagram containing the whole Distributed Deep Learning Pipeline:</h4>

<br />

![Architecture-DistributedDL](https://github.com/DeathReaper0965/distributed-deeplearning/blob/master/images/Architecture-DistributedDL.png?raw=true)

Made with ❤️ &nbsp;by Praneet Pabolu
