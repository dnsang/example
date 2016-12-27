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
  kafkaConsumer.unsubscribe()

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

  def startConsume(num: Int, log: Boolean = false): Int = {
    try {
      val counter = new AtomicInteger(0);
      while (counter.get() < num) {
        val records: ConsumerRecords[String, String] = kafkaConsumer.poll(100)
        if (log) {
          println("Num records:" + records.count())
        }
        counter.addAndGet(records.count())
        kafkaConsumer.commitSync()
      }
      counter.get()
    }

    finally {
      kafkaConsumer.close()
    }

  }

}
