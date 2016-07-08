package sls

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.concurrent.ExecutionException


/**
  * Created by SangDang on 6/27/16.
  */
class KafkaSimpleProducer(topic:String) {
  private val kafkaProperties: Properties = new Properties()
  kafkaProperties.put("bootstrap.servers", "localhost:9092")
  kafkaProperties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
  kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  private val producer = new KafkaProducer[String,String](kafkaProperties)

  def send(key:String,msg:String):Unit={
    try{
      val result =producer.send(new ProducerRecord(topic,key,msg))
      result.get()
    } catch{
      case ex @ (_:InterruptedException | _: ExecutionException) => {
        ex.printStackTrace()
      }
      case _ => {

      }
    }
  }
}

