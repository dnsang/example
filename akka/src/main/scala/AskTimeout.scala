
import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by SangDang on 7/9/16.
  */
object TimeoutActor {
  val refCounter: AtomicInteger = new AtomicInteger(0)
}

class TimeoutActor extends Actor {

  override def receive: Receive = {
    case "hello" => println("hello world" + TimeoutActor.refCounter.incrementAndGet())
    case "ask" => sender ! "ok"
    case "ask2" => Thread.sleep(SECONDS.toMillis(2))
    case _ => print("???")
  }
}


object Main2 extends App {

  val system = ActorSystem.create("HelloAkka")
  val helloActor = system.actorOf(Props[TimeoutActor])
  helloActor ! "hello"

  implicit val timeout: Timeout = 1 seconds
  val future = helloActor ? "ask2"
  future onComplete {
    case Failure(t) => {
      println(t.getMessage)
      println("System Shutdown")
      system.shutdown()
    }
    case Success(v) => println(v.asInstanceOf[String])
    case _ => println("unknowned")
  }

}