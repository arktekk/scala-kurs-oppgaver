# [Futures](http://www.scala-lang.org/api/current/#scala.concurrent.Future)

---

> Future[A] er en representasjon av en verdi A som kan bli tilgjengelig på et eller annet tidspunkt

Future kan være i 2 tilstander

* uferdig
* ferdig

En ferdig future er enten 'successful' eller 'failed'

---

Mye brukt for asynkron / concurrent / parallell kode

* non-blocking nettverks-kode
* map/reduce
* ...

Alle computations kjøres på en [ExecutionContext](http://www.scala-lang.org/api/current/index.html#scala.concurrent.ExecutionContext)

---

```scala
import scala.concurrent._
import ExecutionContext.Implicits.global

val work:Future[Result] = Future {
  ... // do heavy work returning Result
}

```

---

```scala
import scala.util.{Success, Failure}

work.onComplete{
  case Success(result) => ..
  case Failure(ex)     => ...
}

work.onSuccess{ case result => ... }
work.onFailure{ case ex:Exception => ... }
```

---

map, flatMap, filter, forEach og mye annet

```scala
val fa:Future[Int] = ...
def fb(a:A):Future[String] = ...

val x:Future[String] = for {
  a <- fa
  b <- fb(a) if a > 10
} yield b
```

---

## error recovery / exception handling

```scala
val ok =  Future{ 2 / 0 } recover { case x:ArithmeticException => 0 }

for(r <- ok)
  println(r)
// 0
```

---

## zipping

```scala
val fa:Future[A] = ...
val fb:Future[B] = ...

val fab:Future[(A, B)] = fa zip fb
```

---

## sequencing

```scala
val listOfFuture:List[Future[Int]] = ...

val futureOfList:Future[List[Int]] = Future.sequence(listOfFuture)

```

---

## promises

```scala
val p = Promise[Int]
val f = p.future
f.foreach(i => println(i))
// på et eller annet tidspunkt
p.success(5)
```

---

## blocking await

```scala
import scala.concurrent.duration._

val f:Future[Int] = ...
val i:Int = Await.result(f, 5.seconds)
```
