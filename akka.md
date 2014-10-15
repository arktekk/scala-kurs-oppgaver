# [akka](http://akka.io/)

---

> Akka is a toolkit and runtime for building highly concurrent, distributed, and fault tolerant event-driven applications on the JVM.

---

* actors
* remoting
* supervision

---

> Actors er concurrent prosesser som kommuniserer via meldinger

---

```scala
import akka.actors._

case class Message(msg:String)

val system = ActorSystem()
// ActorRef er en nettverkstransparent referanse til en actor
val client:ActorRef = system.actorOf(Props[Client])

// ! sender melding til en actor
client ! Message("hi")

class Client extends Actor {
  // implementer all message handling i receive m/pattern matching
  def receive = {
    case Message(msg) => println(s"got $msg")
  }
}

```

---

> 50 million msg/sec on a single machine.
> Small memory footprint; ~2.5 million actors per GB of heap.

---

```scala
// instansier
system.actorOf(Props[SomeActor], name = "some-actor")

system.actorOf(Props(new SomeActor))

// lookup
system.actorSelection("/user/some-actor")
```

---

## supervision

```scala
class Supervisor extends Actor {
  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException      ⇒ Resume
      case _: NullPointerException     ⇒ Restart
      case _: Exception                ⇒ Escalate
    }
 
  val worker = context.actorOf(Props[Worker])
 
  def receive = {
    case n: Int => worker forward n
  }
}
```

