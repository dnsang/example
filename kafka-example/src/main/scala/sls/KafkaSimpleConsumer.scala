package sls

import java.util
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer
import java.util.{Collections, Properties, function}

import collection.JavaConverters._
import org.apache.kafka.clients.consumer._
import org.apache.kafka.common.TopicPartition

/**
  * Created by SangDang on 6/27/16.
  */
class KafkaSimpleConsumer(groupId: String, topic: String) {
  private val kafkaProperties: Properties = new Properties()
  kafkaProperties.put("bootstrap.servers", "localhost:9092")
  kafkaProperties.put("group.id", groupId)
  kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  kafkaProperties.put("max.poll.records", "1")
  private val kafkaConsumer = new KafkaConsumer[String, String](kafkaProperties)
  kafkaConsumer.subscribe(util.Arrays.asList(topic))

  def startConsume(): Unit = {
    try {
      while (true) {

        val records: ConsumerRecords[String, String] = kafkaConsumer.poll(100)
        records.asScala.foreach(record => {
          println(record.key() + ":" + record.value() + ":" + record.offset())

        })
        kafkaConsumer.commitSync()
      }
    } finally {
      kafkaConsumer.close()
    }

  }

  def startConsume(num: Int): Unit = {
    try {
      val counter = new AtomicInteger(0);
      while (counter.get() < num) {
        val records: ConsumerRecords[String, String] = kafkaConsumer.poll(100)
        counter.addAndGet(records.count())
        kafkaConsumer.commitSync()
      }
      println("Consumer group:" + groupId + " on topic:" + topic + " finish consume: " + counter.get() + " items")


    }

    finally {
      kafkaConsumer.close()
    }

  }

}
