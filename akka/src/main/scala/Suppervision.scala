import akka.actor.Actor.Receive
import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, AllForOneStrategy, OneForOneStrategy, Props, SupervisorStrategy}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern._
import akka.pattern.AskSupport
import akka.util.Timeout

/**
  * Created by SangDang on 7/11/16.
  */
class Result

class WorkerActor extends Actor with ActorLogging {
  var state = 0

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    println("Starting WorkerActor " + this.hashCode())
  }




  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    super.postStop();
    println("Stoping WorkerActor " + this.hashCode())
  }

  override def receive: Receive = {
    case value: Int => {
      if (value <= 0) {
        state = -1
        throw new ArithmeticException("Number equal or less than zero")
      }
      else state = value
    }
    case result: Result => {
      sender() ! state
    }
    case ex: NullPointerException => throw new NullPointerException("Null Value Passed")
    case _ => throw new IllegalArgumentException("Wrong Argument")

  }
}


class One4OneSupervisorActor extends Actor with ActorLogging {
  val childActor = context.actorOf(Props[WorkerActor], "worker-actor")

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException =>{
      println("supervisorStrategy::Resume")
      Resume
    }
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }
  //  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {
  //    case _: ArithmeticException => Resume
  //    case _: NullPointerException => Restart
  //    case _: IllegalArgumentException => Stop
  //    case ex: Exception => {
  //      println(ex.getMessage)
  //      Escalate
  //    }
  //  }

  override def receive: Receive = {
    case r: Result => childActor.tell(r, sender)
    case msg: Object => childActor ! msg
  }
}
class All4OneSupervisorActor extends Actor with ActorLogging {
  val childActor1 = context.actorOf(Props[WorkerActor], "worker-actor1")
  val childActor2 = context.actorOf(Props[WorkerActor], "worker-actor2")

  override val supervisorStrategy = AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException =>{
      Resume
    }
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }
  //  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {
  //    case _: ArithmeticException => Resume
  //    case _: NullPointerException => Restart
  //    case _: IllegalArgumentException => Stop
  //    case ex: Exception => {
  //      println(ex.getMessage)
  //      Escalate
  //    }
  //  }

  override def receive: Receive = {
    case r: Result => childActor1.tell(r, sender)
    case msg: Object => childActor1 ! msg
  }
}


object HelloOne4OneSupervisionStrategy extends App {
  val system = ActorSystem("faultTolerance")
  val originalValue: Int = 8
  val supervisor = system.actorOf(Props[One4OneSupervisorActor], name = "supervisor")
//  println("send value 8")
//  supervisor ! originalValue
//  doAsk(supervisor, new Result)
//
//  println("send value -8 ArithmeticException will thrown")
//  supervisor ! originalValue * (-1)
//  doAsk(supervisor, new Result)
//
//  println("Re-send to check child-worker have resumed already")
//  supervisor ! originalValue
//  doAsk(supervisor, new Result)

//  println("Send null value, child-worker should restart")
//  supervisor ! new NullPointerException
//  doAsk(supervisor,new Result)

  println("Send invalid value, child-worker should stop")
  supervisor ! "something strange"
  doAsk(supervisor,new Result)

  def doAsk(actorRef: ActorRef, obj: Any): Unit = {
    implicit val timeout = Timeout(1 seconds)
    val result = actorRef ? obj
    result onFailure {
      case ex: Exception => println("Exception to ask result" + ex.getMessage)
    }
    result onSuccess {
      case any: Any => println("Result is:" + any)
    }
    Thread.sleep(2.seconds.toMillis)
  }
}
object HelloAll4OneSupervisionStrategy extends App {
  val system = ActorSystem("faultTolerance")
  val originalValue: Int = 8
  val supervisor = system.actorOf(Props[All4OneSupervisorActor], name = "supervisor")
  //  println("send value 8")
  //  supervisor ! originalValue
  //  doAsk(supervisor, new Result)
  //
  //  println("send value -8 ArithmeticException will thrown")
  //  supervisor ! originalValue * (-1)
  //  doAsk(supervisor, new Result)
  //
  //  println("Re-send to check child-worker have resumed already")
  //  supervisor ! originalValue
  //  doAsk(supervisor, new Result)

  //  println("Send null value, child-worker should restart")
  //  supervisor ! new NullPointerException
  //  doAsk(supervisor,new Result)

  println("Send invalid value, child-worker should stop")
  supervisor ! "something strange"
  doAsk(supervisor,new Result)

  def doAsk(actorRef: ActorRef, obj: Any): Unit = {
    implicit val timeout = Timeout(1 seconds)
    val result = actorRef ? obj
    result onFailure {
      case ex: Exception => println("Exception to ask result" + ex.getMessage)
    }
    result onSuccess {
      case any: Any => println("Result is:" + any)
    }
    Thread.sleep(2.seconds.toMillis)
  }
}