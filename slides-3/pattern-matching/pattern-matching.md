!SLIDE
# pattern matching #
* "switch på steroider"
* destructuring & matching
* vanlig blant funksjonelle språk som haskell, erlang, ml osv



!SLIDE
# krav #
* gitt en `List[List[Int]]`
* når det første element er er en liste hvor første element er er 1, 2 eller 3, return det tallet
* når det andre elementer i lista er en liste, returner det andre elementet i den listen hvis det eksisterer
* ellers returner 0
	
!SLIDE
```java
public int f(List<List<Integer>> l){
  if(l != null){
    if(l.size() >= 1){
      List<Integer> l2 = l.get(0);
      if(l2 != null && l2.size() >= 1){
        Integer i = l2.get(0);
        if(i == 1 || i == 2 || i == 3)
          return i;
      }
    }
    if(l.size() >= 2){
      List<Integer> l2 = l.get(1);
      if(l2 != null && l2.size() >= 2)
        return l2.get(1);      
    }
  }
  return 0;
}
```

!SLIDE
```scala
def f(l:List[List[Int]]) = l match {
  case List(List(x @ (1 | 2 | 3), _*), _*) => x
  case List(_, List(_, x, _),_*) => x
  case _ => 0
}
```

!SLIDE
## wildcard ##
```scala
x match {
  case _ =>
}
```

!SLIDE
## variable ##
```scala
x match {
  case y =>
}
```

!SLIDE
## typed ##
```scala
x match {
  case _:Foo =>
  case a:C =>
  case b:p.C =>
  case c:T#C =>
  case d:Singleton.type => 
  case e:(A with B with C) =>
}
```

!SLIDE
## binding ##
```scala
x match {
  case y @ _ =>
}
```

!SLIDE
## literal ##
```scala
x match {
  case "hello" =>
  case 5 =>
  case true =>
  case 'A' =>
  case 5.5 =>
}

```

!SLIDE
## @switch ##
```scala
(x: @switch) match {
  case 1 =>
  case 2 =>
  case 3 =>
}
```

!SLIDE
## @switch ##
```scala
(x: @switch) match {
  case 1 =>
  case 2 =>
  case "3" =>
}

// error: could not emit switch for @switch annotated match
```

!SLIDE
## stable identifier ? ##
```scala
val hello = "hello"
"world" match {
  case hello => // matcher
}
```

!SLIDE
## stable identifier ##
```scala
val Hello = "hello"
"world" match {
  case Hello => // matcher ikke
}
```
!SLIDE
## stable identifier ##
```scala
def f(x:Int, y:Int) = x match {
  case `y` =>
}
```

!SLIDE
## constructor ##
```scala
case class Foo(a:String, b:Int)

x match {
  case Foo(aString, anInt) =>
}
```

!SLIDE
## constructor ##
```scala
case class Foo(a:String, b:Int)

x match {
  case Foo("Hello", 5) =>
  case Foo(aString, anInt) =>
}
```

!SLIDE
## Algebraiske Data Typer ##
```scala
sealed trait Tree
case class Branch(l:Tree, r:Tree) extends Tree
case class Leaf(v:Int) extends Tree

def sum(t:Tree):Int = t match {
  case Branch(l, r) => sum(l) + sum(r)
  case Leaf(v) => v
}

val tree = Branch(
  Branch(Leaf(1), Leaf(2)),
  Branch(Leaf(3), Leaf(4)))
sum(tree)
```

!SLIDE
## tuple ##
```scala
x match {
  case (first, second) =>
}
```

!SLIDE
## tuple ##
```scala
x match {
  case (first, second, third) =>
}
```

!SLIDE
## sequences ##
```scala
x match {
  case List(a, b) =>
  case List(a, b, c, _*) =>
}
```

!SLIDE
## sequences ##
```scala
x match {
  case List(a, b) =>
  case List(a, b, c, d @ _*) =>
}
```

!SLIDE
## extractors ##
```scala
def unapply(a:A):Boolean

def unapply(a:A):Option[B]

def unapplySeq(a:A):Option[Seq[B]]
```

!SLIDE
## unapply ##
```scala
object Empty {
  def unapply(a:String):Boolean = a.trim.size == 0
}

x match {
  case Empty() =>
}
```

!SLIDE
## unapply ##
```scala
object Even {
  def unapply(a:Int):Option[Int] =
    if(a % 2 == 0) Some(a) else None
}

x match {
  case Even(even) => 
}
```

!SLIDE
```java
class Name{
  String first; String last;
  public Name(first:String, last:String){
    this.first = first; this.last = last;	
  }
}
```
```scala
object FirstLast {
  def unapply(name:Name):Option[(String, String)] = 
    Some(name.first, name.last)
}

(new Name("scala", "kurs")) match {
  case FirstLast(first, last) =>
}
```

!SLIDE
## unapplySeq ##
```scala
object Csv {
  def unapplySeq(s:String) = Some(s.split(",").toSeq)
}

"1,2,3" match {
  case Csv(a, b, c) => a+"-"+b+"-"+c
}
```

!SLIDE
## extractor tree ##
```scala
sealed trait Tree
object Branch {
  private class Impl(val l:Tree, r:Tree) extends Tree
  def apply(l:Tree, r:Tree):Tree = new Impl(l, r)
  def unapply(t:Tree) = t match {
    case i:Impl => Some(i.l, i.r)
    case _ => None	
  }
}
object Leaf {
  private class Impl(val v:Int) extends Tree
  def apply(v:Int):Tree = new Impl(v)
  def unapply(t:Tree) = t match {
    case i:Impl => Some(i.v)
    case _ => None	
  }
}
```

!SLIDE
```scala
def sum(tree:Tree):Int = tree match {
  case Branch(left, right) => sum(left) + sum(right)
  case Leaf(value) => value
}
```

!SLIDE
## infix ##
```scala
case class XX(a:String, b:Int)

x match {
  case a XX b =>
}
```

!SLIDE
## infix ##
```scala
case class ::[A](head:A, tail:List[A]) extends List[A] {
  def ::[B >: A](b:B):List[B] = ::(b, this)
}

(1 :: 2 :: 3 :: Nil) match {
  case a :: b :: c :: Nil =>
}
```

!SLIDE
## infix ##
```scala
case class ::[A](head:A, tail:List[A]) extends List[A] {
  def ::[B >: A](b:B):List[B] = ::(b, this)
}

(1 :: 2 :: 3 :: Nil) match {
  case ::(1, ::(2, ::(3, Nil))) =>
}
```

!SLIDE
## infix ##
```scala
object -> {
  def unapply[A, B](ab:(A, B)):Option[(A, B)] =
	Some(ab)
}

(1 -> 2) match {
  case a -> b => 
}

(1 -> 2 -> 3) match {
  case a -> b -> c =>
}
```

!SLIDE
## infix ##
```scala
case class EX(l:List[String], num:Int, s:String)

x match {
  case l EX (num, s) => 
}
```

!SLIDE
## infix ##
```scala
case class EX(l:List[String], num:Int, s:String)

x match {
  case a :: b :: c EX (num, s) => 
}
```

!SLIDE
## alternatives ##
```scala
x match {
  case 1 | 2 =>
  case _:Foo | _:Bar =>
}
```

!SLIDE
## alternatives ##
```scala
try {
  // throw something
} catch {
  case e @ (_:ExA | _:ExB) => // multicatch
}
```

!SLIDE
## xml ##
```scala
<foo>bar</foo> match {
  case <foo>{what}</foo> =>
}
```

!SLIDE
## guards ##
```scala
x match {
  case y if y > 2 =>
}
```

!SLIDE
## partial functions ##
```scala
val pos:PartialFunction[Int, String] = {
  case n if n > 0 => n.toString
}

pos.isDefinedAt(5) // true
pos.isDefinedAt(-1) // false

pos(5)  // "5"
pos(-1) // java.util.NoSuchElementException
```

!SLIDE
## partial functions ##
```scala
val pos:PartialFunction[Int, String] = {
  case n if n > 0 => n.toString
}

val notPos:PartialFunction[Int, String] = {
  case n if n <= 0 => "Not positive"
}

val all = pos orElse notPos

for(i <- -5 to 5)
  println(all(i))
```

!SLIDE
```scala
val Num = "(\\d+)".r
List("123", "abc", "321").collect{
  case Num(num) => num.toInt
}
// List(123, 321)
```

!SLIDE
## functions ##
```scala
List(-2, -1, 0, 1, 2).foldLeft(0){
  case (a, e) => if e < 0 => a
  case (a, e) => a + e
}
```

!SLIDE
## assignment ##
```scala
val (minors, adults) = people.partition(_.age < 18)

val Email = "(.+)@(.+)".r
val EMail(name, domain) = "foo@bar.com"
```

!SLIDE
## for-comprehensions ##
```scala
object Even {
  def unapply(i:Int) = 
    if(i % 2 == 0) Some(i) else None
}

for{
  Even(number) <- List(1, 2, 3, 4)
} yield number
```

!SLIDE
## erasure ##
```scala
val list:List[Any] = List(1, 2, 3)

list match {
  case x:List[String] => "Strings" // matcher
  case x:List[Int] => "Ints"
}
```

!SLIDE
## #1133 ##
`Exception in thread "main" java.lang.Error: 
ch.epfl.lamp.fjbg.JCod$OffsetTooBigException: offset to big to fit in 16 bits: 55087`

!SLIDE
## don't ##
```scala
val a:Option[Int] = ...

a match {
  case Some(i) => Some(i * 2)
  case _ => None
}
```

!SLIDE
## do ##
```scala
val a:Option[Int] = ...

a.map(i => i * 2)
```

!SLIDE
## don't ##
```scala
val a:Option[String] = ...
val b:Option[String] = ...

(a, b) match {
  case (Some(hello), Some(world)) => Some(hello + " " + world)
  case _ => None
}
```

!SLIDE
## do ##
```scala
val a:Option[String] = ...
val b:Option[String] = ...

for{
  hello <- a
  world <- b
} yield hello + " " + world
```

!SLIDE
# Oppgavetid :-) #
Skriv ditt eget webrammeverk! (thats right)

[webframework](https://github.com/arktekk/scala-kurs-oppgaver/tree/master/webframework)
