!SLIDE
## literals ##
```scala font-90
"hello"             // string
"""a multi
line "string"
"""                 // multiline escaped string
5                   // int
5.5                 // double
5f                  // float
1e30f               // float exponential
1.0e-100            // exponential double
0xA                 // 10 hex int
012                 // 10 octal int
1000L               // long
'a'                 // character
'\u0041'            // unicode character
<hello/>            // xml
true false          // boolean
'alright            // symbol
null
```

!SLIDE
## variabler ##
```scala	
var foo = 5
foo = 6

// eksplisitt typet
var foo:Int = 5
```

!SLIDE
## values ##
```scala
val x = "Hello"

// eksplisitt typet
val x:String = "Hello"
```

!SLIDE
## metode ##
```scala
def foo:Int = {
  return 5
}
```

!SLIDE
## metode ##
```scala
def foo:Int = {
  5
}

// siste expression returneres alltid
```

!SLIDE
## metode ##
```scala
def foo = {
  5
}

// return type inference
```

!SLIDE
## metode ##
```scala
def foo = 5

// single expression
```

!SLIDE
## void/Unit metoder ##
```scala
def something(i:Int):Unit = {
  println("Do " + i)
}
```

!SLIDE
## void/Unit metoder ##
```scala
def something(i:Int) {
  println("Do " + i)
}

// return type inference og ingen =
```

!SLIDE
## void/Unit uten parameter ##
```scala
def printSomething(){
  println("something")
}

// () indikerer sideeffekt
```

!SLIDE
## nøstet definisjon ##
```scala
val nesting = {
  def plus(a:Int, b:Int) = a + b
  val x = {
    val y = 5 - 3
    y - 2	
  }
  val z = plus(x, 10)
  z / 2
}
```

!SLIDE
## lazy ##
```scala
lazy val person = {
  println("fra DB")
  DB.findPersonByPk(5)
}
println("etter")
person.name

// etter
// fra DB
```

!SLIDE
## equality ##
```scala
val a = new String("Hello")
val b = new String("Hello")

a == b // true (java equals)
a eq b // false (java ==)
```

!SLIDE
```java font-60
class Person {
  private final String name;
  private Integer age;
		
  public Person(String name, Integer age){
    this.name = name;
    this.age = age;
  }
		
  public String getName(){
    return name;
  }
		
  public Integer getAge(){
    return age;
  }
		
  public void setAge(Integer age){
    this.age = age;
  }
		
  public static Person create30(String name){
    return new Person(name, 30);
  }
		
  @Override
  public String toString(){
    return "["+name+","+age+"]";
  }
}
```

!SLIDE
## object / class ##
```scala
object Person {
  def create30(name:String) = new Person(name, 30)
}
	
class Person(val name: String, var age: Int){
  override def toString = "["+name+","+age+"]"
}

```
!SLIDE
## unified access ##
```scala
class Person(private val _name: String,
             private var _age: Int) {
	  
  def name = _name	

  def age = _age	
  
  def age_=(a:Int){
    _age = a	
  }
}
```

!SLIDE
## unified access ##
```scala
val person = Person.create30("foo")
	
person.name // foo
	
person.age = 29
person.age // 29
```

!SLIDE
## tuples ##
```scala
val ab = (5, "Hello")
val ab = 5 -> "Hello"

val abc = (5.5, "World", List(1, 2, 3))

ab._1 == 5
ab._2 == "Hello"

abc._3 == List(1, 2, 3)
```
!SLIDE
## collections ##
![collections](basic/collections.png)

!SLIDE
## "literals" ##
```scala
val list  = List(1, 2, 3)
val map   = Map(1 -> "a", 2 -> "3")
val set   = Set("a", "b", "c")
val array = Array(1, 2, 3)
val range = 1 to 10
```

!SLIDE
## mutable ##
![mutable](basic/mutable.png)

!SLIDE
```scala
import collection.mutable._

val buffer = new ArrayBuffer[Int]

buffer += 1
buffer += 2

buffer -= 1

buffer.foreach(element => println(element))

for(element <- buffer)
  println(element)
```

!SLIDE
```scala
import collection.mutable._

val map = new HashMap[Int, String]
map += (1 -> "World")
map += (2 -> "World")

map -= 1
map(2) = "Oh yeah"
```

!SLIDE
## immutable ##
![immutable](basic/immutable.png)

!SLIDE
```scala
val list = List(1, 2, 3)
val cons = 1 :: 2 :: 3 :: Nil

val map = Map(1 -> "Hello")
val helloWorld = map + (2 -> "World")
val world = helloWorld - 1

val vector = Vector(1, 2)
val done = vector :+ 3
```

!SLIDE
## map ##
```scala
List[+A]{
  def map[B](f:A => B):List[B]
}

val list = List(1, 2, 3, 4)

list.map(element => element * 2)
// List(1, 4, 6, 8)
```

!SLIDE
## filter & filterNot ##
```scala
List[+A]{
  def filter(f:A => Boolean):List[A]
}

val list = List(1, 2, 3, 4)

list.filter(element => element % 2 == 0)
// List(2, 4)

list.filterNot(element => element % 2 == 0)
// List(1, 3)
```

!SLIDE
## flatMap ##
```scala
List[+A]{
  def flatMap[B](f:A => Traversable[B]):List[B]
}

class Person(val pets:List[String])

val family = List(new Person(List("Dog", "Cat")), new Person(List("Fish")))

val familyPets = family.flatMap(p => p.pets)
// List("Dog", "Cat", "Fish")

val strings = List("a,b,c", "d,e,f").flatMap(s => s.split(","))
// List(a, b, c, d, e, f)
```

!SLIDE
## mer funksjonalitet ##
```scala
val first10 = list.take(10)

val dropped = list.drop(5)

val familier:Map[String, List[Person]] =
  personer.groupBy(_.etternavn)

val fornavn:Map[String, List[String]] = 
  familier.mapValues(personer => personer.map(_.fornavn))

val sortert:Seq[(String, List[String])] =
  fornavn.toSeq.sortBy(_._1.size)

List(1, 2, 3).contains(2) == true
```

!SLIDE
## Null ♥ ? ##
```java
import java.util.HashMap
val map = new HashMap[Int, String]
map.put(0, null)
...
map.get(0) // null
map.get(1) // null

if(map.containsKey(0)){
  return map.get(0)
}
```

!SLIDE
## Option ##
```scala
val map = Map(1 -> "Hello", 2 -> "World")
map.get(1) // Some("Hello")
map.get(0) // None

map(1) // "Hello"
map(0) // java.util.NoSuchElementException
```

!SLIDE
## Option ##
```scala
trait Option[+A] {
  def get:A // eller java.util.NoSuchElementException
  def getOrElse(a:A):A
  def map(f:A => B):Option[B]
  def flatMap(f:A => Option[B]):Option[B]
}

object None extends Option[Nothing]
case class Some[A](value:A) extends Option[A]
```

!SLIDE
```scala
class Adresse(val gate:String, val post:Option[String])
class Person(val navn:String, val adresse:Option[Adresse])

val kaare = new Person("Kaare", Some(new Adresse("Herr Nilsen", Some("Oslo")))
val jonanders = new Person("Jon-Anders", None)

jonanders.adresse.map(_.gate) // None
kaare.adresse.map(_.gate)     // Some("Herr Nilsen")

val jAddr = jonanders.adresse.flatMap(_.post) // None
val kAddr = kaare.adresse.flatMap(_.post)     // Some("Oslo")

jAddr.getOrElse("Ingen adresse") // "Ingen adresse"
kAddr.get // "Oslo"
jAddr.get // java.util.NoSuchElementException
```

!SLIDE
## NullPointerException ♥ ? ##
```java
class Stuff {
  /* can be null */	
  public String getIt(){ ... }
}
```
```scala
val stuff:Option[String] = Option(stuff.getIt)
```

!SLIDE
# Oppgavetid :-) #
Datamining!

[https://github.com/arktekk/scala-kurs-oppgaver/tree/master/buzzword](https://github.com/arktekk/scala-kurs-oppgaver/tree/master/buzzword)