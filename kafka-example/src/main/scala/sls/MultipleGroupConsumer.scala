package sls

import java.util.concurrent.atomic.AtomicInteger

/**
  * An example for 1 producer, multiple group, in group could have multiple consumer
  * Created by SangDang on 7/8/16.
  */
object MultipleGroupConsumer extends App {
  val TOPIC = "kafka-multiple-topic"
  val NUM_ITEM = 100
  val producer = new KafkaSimpleProducer(TOPIC)
  var counter: AtomicInteger = new AtomicInteger(0)
  (1 to NUM_ITEM).par foreach (x => {
    println(counter.incrementAndGet() + " sending item " + x)
    producer.send(x.toString, x.toString)
  })
  while (counter.get() < NUM_ITEM) Thread.`yield`()
  println("Total Send:" + counter + " items")

  val NUM_GROUP = 3
  val NUM_THREAD_IN_GROUP = 3

  (1 to NUM_GROUP).par foreach (x => {
    (1 to NUM_THREAD_IN_GROUP).par foreach (y => {
      println("start group:" + x + " consumer: " + y)
      val consumer = new KafkaSimpleConsumer(x.toString, TOPIC)
      val numItemPerThread = NUM_ITEM / NUM_THREAD_IN_GROUP
      val numConsume=consumer.startConsume(numItemPerThread,false)
      println("end group:" + x + " consumer: " + y + " total items:" + numConsume)
    })

  })


}
