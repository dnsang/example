import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, ActorLogging, ActorSystem, OneForOneStrategy, Props}

/**
 * Created by zkidkid on 12/27/16.
 */
object One4OneStratergyExample extends App {
  val system = ActorSystem("actor-system")
  val rootActor = system.actorOf(Props[RootActor])
  //expect success process 2
  rootActor ! MSG_START(2)
  //expect failed with restart 2 time
  rootActor ! MSG_START(1)
}

case class MSG_START(value: Int)

class RootActor extends Actor with ActorLogging {


  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 3,loggingEnabled = false) {
    case ue: UnsupportedOperationException => {
      println("something wrong with worker-actor")
      Restart
    }
    case e => {
      println("Unexpected failure " + e.getMessage)
      Stop
    }
  }

  val workerActor = context.actorOf(Props[WorkerActor])

  override def receive: Receive = {
    case MSG_START(x) => {
      println("process msg start")
      workerActor ! MSG_PROCESS_INT(x)
    }
  }
}

case class MSG_PROCESS_INT(value: Int)

class WorkerActor extends Actor with ActorLogging {

  var lastValue: Option[Int] = None

  @scala.throws[Exception](classOf[Exception])
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(s"Restart Actor reason $reason msg $message")
    if(lastValue != None){
      context.parent ! MSG_START(lastValue.get)
    }

  }

  override def receive: Receive = {
    case MSG_PROCESS_INT(x) => {
      lastValue = Some(x)
      if (x % 2 == 0) {
        println("worker process " + x)
      }
      else
        throw new UnsupportedOperationException("only support even number")
    }
  }
}
