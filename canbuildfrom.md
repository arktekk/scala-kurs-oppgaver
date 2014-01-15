# CanBuildFrom ??? #
```scala
def map [B, That](f: (A) ⇒ B)(implicit bf: CanBuildFrom[Set[A], B, That]): That
```

---

```scala
trait Traversable[+A] extends TraversableLike[A, Traversable[A]] with ... {
  ...
}

// alle implementasjonene
trait TraversableLike[+A, +Repr] {
  	
  def map[B, That](f: A => B)(implicit bf: CanBuildFrom[Repr, B, That]): That = {
    val b = bf(repr)
    b.sizeHint(this) 
    for (x <- this) b += f(x)
    b.result
  }
  ...
}
```

---

## implicit instanser av CanBuildFrom ##
```scala
object BitSet extends BitSetFactory[BitSet] {  
  def newBuilder = immutable.BitSet.newBuilder  
  implicit def canBuildFrom: CanBuildFrom[BitSet, Int, BitSet] = bitsetCanBuildFrom
}

object Set extends SetFactory[Set] {
  def newBuilder[A] = immutable.Set.newBuilder[A]
  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Set[A]] = setCanBuildFrom[A]
}

val b2:BitSet = BitSet(1, 2, 3).map(i => i * 2)
val Set[String] = b2.map(i => i.toString)
```

---

## [usecase] vs full ##
```scala
trait Set[A]{
  def map [B](f: (A) ⇒ B): Set[B] // [use case] 

  def map [B, That](f: (A) ⇒ B)(implicit bf: CanBuildFrom[Set[A], B, That]): That
}
```

---

## eksempel ##
```scala
package lst2

import collection.mutable.{Builder}
import collection.immutable.LinearSeq
import collection.LinearSeqOptimized
import collection.generic.{GenericTraversableTemplate, SeqFactory}

object Lst extends SeqFactory[Lst] {
  def newBuilder[A]: Builder[A, Lst[A]] = new Builder[A, Lst[A]] {
    private[this] var lst: Lst[A] = Empty

    def +=(elem: A) = {
      lst = Cons(elem, lst)
      this
    }

    def clear() { lst = Empty }

    def result() = lst
  }
}

```

---

```scala
sealed trait Lst[+A]
  extends LinearSeq[A]
  with GenericTraversableTemplate[A, Lst]
  with LinearSeqOptimized[A, Lst[A]] {
  
  override def companion = Lst
}

case object Empty extends Lst[Nothing]{
  override def isEmpty = true
}

final case class Cons[A](override val head: A, override val tail: Lst[A]) extends Lst[A]{
  override def isEmpty = false
}
```
