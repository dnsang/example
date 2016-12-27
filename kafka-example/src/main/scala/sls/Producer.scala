package sls

/**
  * Created by SangDang on 6/27/16.
  */
object Producer extends App {
  print("Start Producer")
  val topic = System.getProperty("topic", "default-topic")
  val producer = new KafkaSimpleProducer(topic)
  for (i <- 0 to 100) {
    producer.send(String.valueOf(i), String.valueOf(i))
  }
  print("End Producer")
}
