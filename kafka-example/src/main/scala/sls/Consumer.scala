package sls

/**
  * Created by SangDang on 6/27/16.
  */
object Consumer extends App{
  val group = System.getProperty("group", "default-group")
  val topic = System.getProperty("topic", "default-topic")
  print("Start Consumer On Group:" + group + " topic: " + topic)
  val consumer = new KafkaSimpleConsumer(group, topic)
  consumer.startConsume()
  print("End Consume")
}
