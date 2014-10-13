# [implicits](http://eed3si9n.com/revisiting-implicits-without-import-tax) #

---

* conversions
* parameters

---

## bruk/patterns ##
* pimp/enhance-my-library
* adapters
* type-classes
* constraints
* JavaConversions & JavaConverters
* Manifests

---

## [Jorge's](http://twitter.com/#!/jorgeo) lover ##
Thou shalt only use implicit conversions for one of two (2) reasons:

1. Pimping members onto an existing type
2. "Fixing" a broken type hierarchy

---

## pimp-my-library ##
1. Pimping members onto an existing type

```scala
class StringOps(s:String){
  def toInt = java.lang.Integer.parseInt(s)
}

implicit def augmentString(s:String):StringOps = new StringOps(s)

val i = "543".toInt

// augmentString("543").toInt
```

---

#implicit classes
```scala
implicit class StringOps(s:String){
  def toInt = java.lang.Integer.parseInt(s)
}
```

---

## [scala.Predef](http://www.scala-lang.org/api/current/index.html#scala.Predef$) ##
* alltid importerte typer, metoder og implicits
* et lite utvalg av de 78

```scala
type List[+A] = collection.immutable.List[A]
type Pair[+A, +B] = Tuple2[A, B]

implicit def augmentString(s:String):StringOps = new StringOps(s)

final class ArrowAssoc[A](val x: A) {
  @inline def -> [B](y: B): Tuple2[A, B] = Tuple2(x, y)
}
implicit def any2ArrowAssoc[A](x: A) = new ArrowAssoc(x)
```
<br>
inspiser implicits i scope med REPL

	> :implicits
	> :implicits -v

---


## adapters ##
* Jorge's lov #2 "fixing a broken type hierarchy"
* Er dette eksemplet i henhold ?

```scala
object Runnables {
  implicit def function2Runnable(f:() => Unit) = new Runnable{
    def run() { f() }
  }
}
import Runnables._

SwingUtilities.invokeLater(() => println("Too convenient ?"))
```

---

# Java interop m/ implicits #
* [JavaConversions](http://www.scala-lang.org/api/current/index.html#scala.collection.JavaConversions$)
* [JavaConverters](http://www.scala-lang.org/api/current/index.html#scala.collection.JavaConverters$)

---

## [JavaConversions](http://www.scala-lang.org/api/current/index.html#scala.collection.JavaConversions$) ##
implicit conversions mellom scala og java collections	

```scala
import collection.JavaConversions._

val list:java.util.List[String] = Seq("hello", "world")

val seq:Seq[String] = list
```

---

## [JavaConverters](http://www.scala-lang.org/api/current/index.html#scala.collection.JavaConverters$) ##
`asScala` og `asJava` metoder på java og scala collections

```scala
import collection.JavaConverters._

val list:java.util.List[String] = Seq("Hello", "World").asJava

val seq:Seq[String] = list.asScala
```

---

## diskusjon ##
* hvilken er lettest å bruke ?
* hvilken er lettest å lese/forstå ?
* bryter JavaConversions med Jorge's lov #2 ?

---

## implicit parameters ##
```scala
implicit val msg = "Hello"

def sayHello(s:String)(implicit m:String) = m + " " + s

sayHello("World")

> Hello World
```

---

## implicitly ##
gir deg tilgang til implicit parameters

```scala
val ordering = implicitly[Ordering[Int]]

ordering.compare(1, 2)

> -1
```

```scala
// implementasjon
def implicitly[A](implicit a:A):A = a
```

---

## view bounds ##
* definerer at vi skal kunne se på A som om den var en B
* dvs: at det finnes implicit conversion A => B

```scala
object Min {
  def min[A <% Ordered[A]](a1:A, a2:A) = 
    if(a1 < a2) a1 else a2

  // sukker for
  def min[A](a1:A, a2:A)(implicit ev:A => Ordered[A]) = ...
}

case class Num(i:Int)
  
Min.min(Num(1), Num(2))

```
	No implicit view available from MinRun.Num => Ordered[MinRun.Num].
	[error]   Min.min(Num(1), Num(2))
	[error]          ^


---

## context bounds ##
* definerer at det finnes **implicit parameter** `Ordering[A]`

```scala
object Min {
  def min[A : Ordering](a1:A, a2:A) =
    if(implicitly[Ordering[A]].lt(a1, a2)) a1 else a2

  // sukker for
  def min[A](a1:A, a2:A)(implicit ev:Ordering[A]) = ...
}

Min.min(Num(1), Num(2))

// veldig fin for å kalle videre..
def min2[A : Ordering](a1:A, a2:A) = Min.min(a1, a2)
```

---

## evidence types ##
* bruk typesystemet til å bevise ting

```scala
class Foo[A](a:A){
  def int(implicit ev:A =:= Int):Int = a 
}

new Foo(0).int

new Foo("Hello").int
// error: Cannot prove that java.lang.String =:= Int
```

---

## classtag ##
* reified generics (runtime type)
* påkrevd for instansiering av Arrays

```scala
def newInstance[A](implicit c:reflect.ClassTag[A]):A = 
  manifest.runtimeClass.asInstanceOf[Class[A]].newInstance
	
newInstance[java.util.ArrayList[String]]


object Array {
  def apply(elms:A*)[A : ClassManifest]:Array[A] = ...
}

def create[A](a:A) = Array(a)            // kompilerer ikke
def create[A : ClassTag](a:A) = Array(a) // ok
```

---

## not found ##
```scala
trait Msg[A]{
  def msg(a:A)
}

object MittApi {
  def needsMsg[A : Msg](a:A){ ... }
}

// brukers kode
MittApi.needsMsg("Hello")

/*
could not find implicit value for evidence parameter of type implicitstuff.Msg[java.lang.String]
    MittApi.needsMsg("Hello")
            ^
*/
```

---

## not found ##
```scala
import annotation.implicitNotFound

@implicitNotFound("Du må definere/importere en implicit instans av Msg[${A}]")
trait Msg[A]{
  def msg(a:A)
}

object MittApi {
  def needsMsg[A : Msg](a:A){ ... }
}

// brukers kode
MittApi.needsMsg("Hello")

/*
Du må definere/importere en implicit instans av Msg[java.lang.String]
    MittApi.needsMsg("Hello")
            ^
*/
```

---

## implicit resolution ##
* local
* imported / package object
* companion

[http://eed3si9n.com/revisiting-implicits-without-import-tax](http://eed3si9n.com/revisiting-implicits-without-import-tax)

---

## type classes ##
* ad-hoc polymorfi
* haskell
* unngår problemet med tunge arve-hierarki
* kan ha flere instanser per type
* `Comparable` vs `Comparator`
* bruker tar instans av type class som implicit parameter

---

## java.util.Comparator som type class ##
```scala
implicit object IntComparator extends java.util.Comparator[Int]{
  def compare(a:Int, b:Int) = a - b 
}
def myCompare[T](a:T, b:T)(implicit comarator:java.util.Comparator[Int]) = 
  comparator.compare(a, b)

myCompare(1,2)

myCompare("Hello", "World")
// error: could not find implicit value for evidence parameter 
//          of type java.util.Comparator[java.lang.String]
```

---

## parametere og conversions kombinert ##
```scala
implicit class Syntax[A](a:A){
  def === (other:A)(implicit c:java.util.Comparator[A]) = 
    c.compare(a, other) == 0
}

5 === 4
```
