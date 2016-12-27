import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, OneForOneStrategy, Props, Terminated}

import scala.collection.immutable.HashMap

/**
  * Created by SangDang on 7/11/16.
  */

case class DeadWorker()

case class RegisterWorker(val worker: ActorRef,
                          val supervisor: ActorRef)

class MonitorActor extends Actor with ActorLogging {
  var monitoredActors = new HashMap[ActorRef, ActorRef]

  def receive: Receive = {
    case t: Terminated =>
      if (monitoredActors.contains(t.actor)) {
        log.info("Received Worker Actor Termination Message -> "
          + t.actor.path)
        log.info("Sending dead message to Supervisor")
        val value: Option[ActorRef] = monitoredActors.get(t.actor)
        value.get ! new DeadWorker()
      }
    case msg: RegisterWorker =>
      context.watch(msg.worker)
      monitoredActors += msg.worker -> msg.supervisor
  }
}

class SupervisorActor extends Actor with ActorLogging {

  import scala.concurrent.duration._

  var childActor = context.actorOf(Props[WorkerActor], "worker-actor")
  val monitorActor = context.actorOf(Props[MonitorActor], "monitor-actor")
  monitorActor ! new RegisterWorker(childActor, self)

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  override def receive: Receive = {
    case r: Result => childActor.tell(r, sender)
    case dw: DeadWorker => {
      log.info("Dead Worker !!! Do restart")
      childActor = context.actorOf(Props[WorkerActor],"worker-actor")

    }
    case msg: Object => childActor ! msg
  }

}

object Watcher extends App {
  val system = ActorSystem("faultTolerance")
  val supervisor = system.actorOf(Props[SupervisorActor],
    name = "supervisor")

  var mesg: Int = 8
  supervisor ! mesg
  supervisor ! "Do Something"
  Thread.sleep(4000)

  supervisor ! mesg
  val result=supervisor ! new Result
  println(result)
  system.shutdown
}