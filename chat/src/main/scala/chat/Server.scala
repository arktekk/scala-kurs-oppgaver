package chat

import akka.actor._
import com.typesafe.config.ConfigFactory

object Server {
  def main(args:Array[String]): Unit ={
    val config = ConfigFactory.load("server-application")
    val system = ActorSystem("server-system", config)
    system.actorOf(Props[Server], "server")
  }
}

class Server extends Actor {
  import collection.mutable

  val subscribers = mutable.Map[String, ActorRef]()
  def reverse = subscribers.map(_.swap)

  def broadcast(msg:String) = for(subscriber <- subscribers.values) subscriber ! StatusMessage(msg)

  def receive = {
    case Send(msg) =>
      for{
        nick <- reverse.get(sender())
        subscriber <- subscribers.values if subscriber != sender()
      } subscriber ! Message(nick, msg)

    case Subscribe(nick) =>
      if(subscribers.isDefinedAt(nick)){
        sender() ! NickTaken(nick)
      } else {

        val msg = reverse.get(sender()).map{ existing =>
          subscribers -= existing
          s"'$existing' is now known as '$nick'"
        }.getOrElse(s"everyone, please welcome '$nick'")

        broadcast(msg)

        subscribers += (nick -> sender())

        sender() ! StatusMessage(s"welcome '$nick'")

        // watch på en actor fører til at vi mottar Terminated melding når den dør
        context.watch(sender())

      }

    case Terminated(ref) =>
      for(nick <- reverse.get(ref)) {
        subscribers -= nick
        broadcast(s"'$nick' left the server")
      }

  }
}