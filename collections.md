# [collections](http://www.scala-lang.org/api/current/index.html#scala.collection.package)

---

![collections](img/collections.png)

---

## [Traversable](http://www.scala-lang.org/api/current/index.html#scala.collection.Traversable)
* base trait for all scala collections
* implementerer **masse** felles metoder kun via `foreach` (over 50)

<br>

```scala
trait Traversable[+A]{
  def foreach[U](f:A => U):Unit	
}
```

---

## [Iterable](http://www.scala-lang.org/api/current/index.html#scala.collection.Iterable)
```scala
trait Iterable[+A] extends Traversable[A]{
  def iterator:Iterator[A]
}
```

---

## [Seq](http://www.scala-lang.org/api/current/index.html#scala.collection.Seq)
* ordnet
* indeksert
* `IndexedSeq`: optimalisert random access & length
* `LinearSeq`: optimalisert head / tail

<br>

```scala
trait Seq[+A] extends Iterable[A]{
  def apply(idx:Int):A
  def length:Int
  def iterator:Iterator[A]
}
```

---

## companion objects m/magisk "apply"
```scala
object List {
  def apply[A](elems:A*):List[A] = ...
}

List(1, 2, 3)

object Map {
  def apply[A, B](elems:(A, B)*):Map[A, B] = ...
}

Map(1 -> "a", 2 -> "b")
```

---

## [Set](http://www.scala-lang.org/api/current/index.html#scala.collection.Set)
* ingen duplikate elementer
* `SortedSet`: sortert
* `BitSet extends Set[Int]`: raskt og bruker lite minne

<br>

```scala
trait Set[A] extends Iterable[A] {
  def +(elem:A):Set[A]
  def -(elem:A):Set[A]
  def contains(elem:A):Boolean
  def iterator:Iterator[A]
}
```

---

## [Map](http://www.scala-lang.org/api/current/index.html#scala.collection.Map)
```scala
trait Map[A, +B] extends Iterable[(A, B)]{
  def +[B1 >: B](kv:(A, B1)):Map[A, B1]
  def -(key:A):Map[A, B]
  def get(key:A):Option[B]
  def iterator:Iterator[A]
}
```

---

## [mutable](http://www.scala-lang.org/api/current/index.html#scala.collection.mutable.package)
![mutable](img/mutable.png)

---

## [mutable](http://www.scala-lang.org/api/current/index.html#scala.collection.mutable.package)
* Stooooort utvalg med forskjellige optimaliseringer
* Synkronisering via traits (stackable modifications)!

<br>

```scala
import collection.mutable._

val myMap = new HashMap[Int, String] with SynchronizedMap[Int, String]
val myBuffer = new ListBuffer[String] with SynchronizedBuffer[String]
val mySet = new HashSet[String] with SynchronizedSet[String]
```

---

## [immutable](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.package) 
![immutable](img/immutable.png) 

---

## [immutable](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.package) 
* [Persistent](http://vimeo.com/28760673) & immutable
* [List](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List)
* [Vector](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.Vector) & [HashMap](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.HashMap)

---

## [List](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List)
* single linket liste
* `LinearSeq`
* O(1) head / prepend / tail
* O(n) random access
* Nil og :: (Cons)

---

## [Vector](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.Vector) & [HashMap](http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.HashMap)
* `Vector extends IndexedSeq`
* Hash tries (tre-struktur)
* Clojure / Rich Hickey / Phil Bagwell
* O(log32(n))
* Ekstremt god general-purpose datastruktur

---

## Stream
* lazy list
* tail er evaluert kun når den er aksessert
* muligjør uendelige lister !!

<br>
sieve of eratosthenes

```scala
def sieve(s: Stream[Int]): Stream[Int] =
  Stream.cons(s.head, sieve(s.tail filterNot { _ % s.head == 0 }))

def primes = sieve(Stream.from(2))
```

---

## Oppgavetid :-)
Implementer metodene i `List` selv

[https://github.com/arktekk/scala-kurs-oppgaver/tree/master/list](https://github.com/arktekk/scala-kurs-oppgaver/tree/master/list)