package chat

import scala.concurrent.duration._
import akka.actor._

import scala.util.{Failure, Success}

object Client {

  val Server  = "/connect (.+)".r
  val Exit    = "/exit"
  val Unknown = "/(.*)".r

  def main(args:Array[String]): Unit ={
    val system = ActorSystem("client-system")
    val client = system.actorOf(Props[Client])

    var loop = true
    while(loop){
      io.StdIn.readLine("! ").trim() match {
        case Server(server) => client ! Connect(server)
        case Exit           => loop = false
        case Unknown(cmd)   => println("unknown command " + cmd)
        case ""             => // ignore empty msg
        case msg            => client ! Send(msg)
      }
    }
    system.shutdown()
  }

  case class Connect(server:String)
}

class Client extends Actor {
  import collection.mutable
  import context.dispatcher

  var nick = System.getProperty("user.name")
  val servers = mutable.Set[ActorRef]()

  def receive = {
    // local
    case Client.Connect(server) =>
      val url = s"akka.tcp://server-system@$server:2552/user/server"
      val remote = context.actorSelection(url).resolveOne(5.seconds)
      remote.onComplete{
        case Success(r) =>
          servers += r
          r ! Subscribe(nick)
        case Failure(t) => t.printStackTrace()
      }

    case Send(msg) => for(server <- servers) server ! Send(msg)

    // remote
    case Message(from, msg) => println(from + " :: " + msg)
  }
}

