import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorSystem, PoisonPill, Props}

/**
  * Created by SangDang on 7/9/16.
  */
class HelloAkka extends Actor{
  override def receive: Receive = {
    case "hello" => println("hello world")
    case _  =>print("???")
  }
}


object Main extends App{
  val system = ActorSystem.create("HelloAkka")
  val helloActor = system.actorOf(Props[TimeoutActor])
  helloActor ! "hello"
  helloActor ! PoisonPill
  helloActor ! "hi"
  helloActor ! Restart
  helloActor ! "hey"
  system.shutdown()
}