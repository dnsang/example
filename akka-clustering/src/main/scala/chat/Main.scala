package chat

import akka.actor.{ActorSystem, Address, Props}
import akka.cluster.Cluster

object Main {
  def main(args: Array[String]): Unit = {
    val systemName = "ChatApp"
    val system1 = ActorSystem(systemName)
    val joinAddress = Cluster(system1).selfAddress
    Cluster(system1).join(joinAddress)
    system1.actorOf(Props[MemberListener], "memberListener")
    system1.actorOf(Props[RandomUser], "Ben")
    system1.actorOf(Props[RandomUser], "Kathy")

    Thread.sleep(5000)
    val system2 = ActorSystem(systemName)
    Cluster(system2).join(joinAddress)
    system2.actorOf(Props[RandomUser], "Skye")

    Thread.sleep(10000)
    val system3 = ActorSystem(systemName)
    Cluster(system3).join(joinAddress)
    system3.actorOf(Props[RandomUser], "Miguel")
    system3.actorOf(Props[RandomUser], "Tyler")
  }
}

object system1 extends App {

  val systemName = "ChatApp"
  val system1 = ActorSystem(systemName)
  val joinAddress = Cluster(system1).selfAddress
  println("join address:" +joinAddress)
  Cluster(system1).join(joinAddress)
  system1.actorOf(Props[MemberListener], "memberListener")
  system1.actorOf(Props[RandomUser], "Ben")
  system1.actorOf(Props[RandomUser], "Kathy")
}
object system2 extends App {

  val systemName = "ChatApp"
  val system1 = ActorSystem(systemName)
  val joinAddress = new Address("akka.tcp",systemName,"192.168.31.234",53061)
  Cluster(system1).join(joinAddress)
  system1.actorOf(Props[MemberListener], "memberListener")
  system1.actorOf(Props[RandomUser], "s5")
}
