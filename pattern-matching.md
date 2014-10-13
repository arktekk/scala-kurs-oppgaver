# pattern matching #

---

* "switch på steroider"
* destructuring & matching
* vanlig blant funksjonelle språk som haskell, erlang, ml osv


---


## eksempel
* gitt en `List[List[Int]]`
* når det første element er er en liste hvor første element er er 1, 2 eller 3, return det tallet
* når det andre elementer i lista er en liste, returner det andre elementet i den listen hvis det eksisterer
* ellers returner 0


---


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

---

```scala
def f(l:List[List[Int]]) = l match {
  case List(List(x @ (1 | 2 | 3), _*), _*) => x
  case List(_, List(_, x, _*),_*) => x
  case _ => 0
}
```

---

## wildcard ##
```scala
x match {
  case _ =>
}
```

---

## variable ##
```scala
x match {
  case y =>
}
```

---

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

---

## binding ##
```scala
x match {
  case y @ _ =>
}
```

---

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

---

## @switch ##
```scala
(x: @switch) match {
  case 1 =>
  case 2 =>
  case 3 =>
}
```

---

## @switch ##
```scala
(x: @switch) match {
  case 1 =>
  case 2 =>
  case "3" =>
}

// error: could not emit switch for @switch annotated match
```

---

## stable identifier ? ##
```scala
val hello = "hello"
"world" match {
  case hello => // matcher
}
```

---

## stable identifier ##
```scala
val Hello = "hello"
"world" match {
  case Hello => // matcher ikke
}
```

---

## stable identifier ##
```scala
def f(x:Int, y:Int) = x match {
  case `y` =>
}
```

---

## constructor ##
```scala
case class Foo(a:String, b:Int)

x match {
  case Foo(aString, anInt) =>
}
```

---

## constructor ##
```scala
case class Foo(a:String, b:Int)

x match {
  case Foo("Hello", 5) =>
  case Foo(aString, anInt) =>
}
```

---

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

---

## tuple ##
```scala
x match {
  case (first, second) =>
}
```

---

## tuple ##
```scala
x match {
  case (first, second, third) =>
}
```

---

## sequences ##
```scala
x match {
  case List(a, b) =>
  case List(a, b, c, _*) =>
}
```

---

## sequences ##
```scala
x match {
  case List(a, b) =>
  case List(a, b, c, d @ _*) =>
}
```

---

## extractors ##
```scala
def unapply(a:A):Boolean

def unapply(a:A):Option[B]

def unapplySeq(a:A):Option[Seq[B]]
```

---

## unapply ##
```scala
object Empty {
  def unapply(a:String):Boolean = a.trim.size == 0
}

x match {
  case Empty() =>
}
```

---

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

---

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

---

## unapplySeq ##
```scala
object Csv {
  def unapplySeq(s:String) = Some(s.split(",").toSeq)
}

"1,2,3" match {
  case Csv(a, b, c) => a+"-"+b+"-"+c
}
```

---

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

---

```scala
def sum(tree:Tree):Int = tree match {
  case Branch(left, right) => sum(left) + sum(right)
  case Leaf(value) => value
}
```

---

## infix ##
```scala
case class XX(a:String, b:Int)

x match {
  case a XX b =>
}
```

---

## infix ##
```scala
case class ::[A](head:A, tail:List[A]) extends List[A] {
  def ::[B >: A](b:B):List[B] = ::(b, this)
}

(1 :: 2 :: 3 :: Nil) match {
  case a :: b :: c :: Nil =>
}
```

---

## infix ##
```scala
case class ::[A](head:A, tail:List[A]) extends List[A] {
  def ::[B >: A](b:B):List[B] = ::(b, this)
}

(1 :: 2 :: 3 :: Nil) match {
  case ::(1, ::(2, ::(3, Nil))) =>
}
```

---

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

---

## infix ##
```scala
case class EX(l:List[String], num:Int, s:String)

x match {
  case l EX (num, s) => 
}
```

---

## infix ##
```scala
case class EX(l:List[String], num:Int, s:String)

x match {
  case a :: b :: c EX (num, s) => 
}
```

---

## alternatives ##
```scala
x match {
  case 1 | 2 =>
  case _:Foo | _:Bar =>
}
```

---

## alternatives ##
```scala
try {
  // throw something
} catch {
  case e @ (_:ExA | _:ExB) => // multicatch
}
```

---

## xml ##
```scala
<foo>bar</foo> match {
  case <foo>{what}</foo> =>
}
```

---

## guards ##
```scala
x match {
  case y if y > 2 =>
}
```

---

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

---

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

---

```scala
val Num = "(\\d+)".r
List("123", "abc", "321").collect{
  case Num(num) => num.toInt
}
// List(123, 321)
```

---

## functions ##
```scala
List(-2, -1, 0, 1, 2).foldLeft(0){
  case (a, e) => if e < 0 => a
  case (a, e) => a + e
}
```

---

## assignment ##
```scala
val (minors, adults) = people.partition(_.age < 18)

val Email = "(.+)@(.+)".r
val EMail(name, domain) = "foo@bar.com"
```

---

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

---

## erasure ##
```scala
val list:List[Any] = List(1, 2, 3)

list match {
  case x:List[String] => "Strings" // matcher
  case x:List[Int] => "Ints"
}
```

---

## don't ##
```scala
val a:Option[Int] = ...

a match {
  case Some(i) => Some(i * 2)
  case _ => None
}
```

---

## do ##
```scala
val a:Option[Int] = ...

a.map(i => i * 2)
```

---

## don't ##
```scala
val a:Option[String] = ...
val b:Option[String] = ...

(a, b) match {
  case (Some(hello), Some(world)) => Some(hello + " " + world)
  case _ => None
}
```

---

## do ##
```scala
val a:Option[String] = ...
val b:Option[String] = ...

for{
  hello <- a
  world <- b
} yield hello + " " + world
```

---

# Oppgavetid :-) #
Skriv ditt eget webrammeverk! (thats right)

[webframework](https://github.com/arktekk/scala-kurs-oppgaver/tree/master/webframework)
