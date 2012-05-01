!SLIDE
## string literal identifiers ##
```scala
val `class` = ..

var `I can be anything` = ...
```

!SLIDE
## annotations ##
```scala
@Component("helloBean")
class Hello @Autowired()(name:String) {
  ...
}
```

!SLIDE
## named parameters ##
```scala
def setPosition(x:Int, y:Int){
  ...	
}
	
setPosition(x = 5, y = 10)

setPosition(y = 10, x = 5)
```

!SLIDE
## default parameters ##
```scala
def hello(name:String, end: String = "!") =
  "Hello " + name + end
	
hello("world") 
// Hello world!
	
hello("world", ", whats up?") 
// Hello world, whats up?
```

!SLIDE
## varargs ##
```scala
def args(ints:Int*){
  ints.foreach(println)
}

args(1, 2, 3)

val list = List(1, 2, 3)

args(list :_*)
```

!SLIDE
## operators ##
```scala
1.0.+(2.0)    "foo".charAt(0)

1.0 + 2.0     "foo" charAt 0
```
!SLIDE
## operators ##
```scala
val a = BigDecimal("100.00")
val b = BigDecimal("25.00")
	
a * b  // 2500.0000
a - b  // 75.00
a / b  // 4
a + b  // 125.00
a > b  // true
a < b  // false
a >= b // true
a <= b // false
```

!SLIDE
## imports ##
```scala
import java.util.List
import java.util._

import java.util.{List, Map}

import java.util.{List => JList}

import java.util.{List => _, _}

import java._
import util.List

import java.{util => jutil}
import jutil.List

import _root_.java.util.List
```

!SLIDE
## import fra alle objekter ##
```scala
object Foo{
  val hello = "hello"	
}

import Foo.hello
import Foo._

class Bar {
  val meh = "whatever"
}

val bar = new Bar
import bar._
```

!SLIDE
## packages ##
```scala
package com.foo.bar {
  class Hello
}

package com.foo.bar

class Hello
```

!SLIDE
## nested packages ##
```scala
package com.foo {
  package bar {
    class Hello
  }	
}

package com.foo
package bar

class Hello
```

!SLIDE
## package objects ##
```scala
// file a
package object foo {
  def say(what:String){
    println("Say " + what)
  }
}

// file b
package foo

class Foo {
  def hi = say("Hi")
}
```

!SLIDE
## type aliases ##
```scala
type JMap[A, B] = java.util.HashMap[A, B]
type Hash[A, B] = java.util.HashMap[A, B]

type JIntMap[B] = JMap[Int, B]
	
val m:JMap[String, String] = new Hash
val i:JIntMap[String] = new Hash
```

!SLIDE
## protected ##
```scala
protected def foo = ...
	
protected [mypackage] def foo = ...
	
protected [MyType] def foo = ...
```

!SLIDE
## private ##
```scala
private def foo = ..

private [mypackage] def foo = ...

private [MyType] def foo = ...

private [this] def foo = ...
```