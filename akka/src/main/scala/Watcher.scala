import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}

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
        log.info("Sending message to Supervisor")
        val value: Option[ActorRef] = monitoredActors.get(t.actor)
        value.get ! new DeadWorker()
      }
    case msg: RegisterWorker =>
      context.watch(msg.worker)
      monitoredActors += msg.worker -> msg.supervisor
  }
}

object Watcher extends App {
  val system = ActorSystem("faultTolerance")
  val supervisor = system.actorOf(Props[One4OneSupervisorActor],
    name = "supervisor")
  var mesg: Int = 8
  supervisor ! mesg
  supervisor ! "Do Something"
  Thread.sleep(4000)
  supervisor ! mesg
  system.shutdown
}