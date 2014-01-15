# typesystemet #
* declaration-site variance
* bounds
* type variabler mm.
* turing complete!

---

## [variance][1] (wikipedia)##

Within the type system of a programming language, covariance and contravariance refers to the ordering of types from narrower to wider and their interchangeability or equivalence in certain situations (such as parameters, generics, and return types).

* covariant: converting from wider (double) to narrower (float).
* contravariant: converting from narrower (float) to wider (double).
* invariant: Not able to convert.

[1]: http://en.wikipedia.org/wiki/Variance_(computer_science)

---

## sub/super typing ##
alle typer er 

* subtyper av seg selv
* supertyper av seg selv

---

## invariance ##
```scala
type X[T] // T er invariant
```
`X[A]` kan benyttes for `X[B]` hvis `A == B`

---

## +covariance ##
```scala
type X[+T] // + betyr at T er covariant
```
`X[A]` kan benyttes for `X[B]` hvis `A` er subtype av `B`


---

## -contravariance ##
```scala
type X[-T] // - betyr at T er contravariant 
```
`X[A]` kan benyttes for `X[B]` hvis `A` er supertype av `B`

---

## arvehierarki (gammelt nytt) ##
```scala
class SuperType
class TheType extends SuperType
class SubType extends TheType

def x(theType:TheType){ ... }

x(new SuperType) // ikke ok 
x(new TheType)
x(new SubType)   // ok
```

---

## invariant ##
```scala
class Invariant[A](a:A){
  def get:A = a
  def set(a:A){ ... }
}

def in(invariant:Invariant[TheType]){ ... }

in(new Invariant[SuperType]) // ikke ok
in(new Invariant[TheType])
in(new Invariant[SubType])   // ikke ok
```

---

## +covariant ##
```scala
class Covariant[+A](a:A){
  def get:A = a
  def set(a:A){ ... } // ikke ok, contravariant position
}

def co(covariant:Covariant[TheType]){ ... }

co(new Covariant[SuperType]) // ikke ok
co(new Covariant[TheType])
co(new Covariant[SubType])   // ok
```

---

## -contravariant ##
```scala
class Contravariant[-A](a:A){
  def get:A = a // ikke ok, covariant position
  def set(a:A){ ... }
}

def contra(contravariant:Contravariant[TheType]){ ... }

contra(new Contravariant[SuperType]) // ok
contra(new Contravariant[TheType]) 
contra(new Contravariant[SubType])   // ikke ok
```

---

## type bounds ##
* upper bound
* lower bound

---

## upper bound ##
* beskriver subtype relasjon

```scala
A <: B // A subtype av B
```

---
```scala
class Foo
class Bar extends Foo 
  
class Foos[F <: Foo](var init:F){
  def set(f:F){ init = f }
  def get = init
}
  
val bars = new Foos[Bar](new Bar)
bars.set(new Bar)
val f:Bar = bars.get

// found Foo, required Bar  
bars.set(new Foo) 

// type arguments [String] do not conform to class 
// Foos's type parameter bounds [F <: Foo]
val strings = new Foos[String]("")
```

---

## lower bound ##
* beskriver supertype relasjon

```scala
A >: B // A supertype av B
```

!SLIDE
```scala
class Foo
class Bar extends Foo

class Generator[F >: Bar]{
  def next:F = new Bar
}

val gen = new Generator[Foo]
val foo:Foo = gen.next
```

---

## eksempel med List ##
```scala
sealed trait Lst[A]
case object Empty extends Lst[Nothing]
case class Cons[A](head:A, tail:Lst[A]) extends Lst[A]

Cons("Hello", Empty)
```

---

```scala
sealed trait Lst[A]
case object Empty extends Lst[Nothing]
case class Cons[A](head:A, tail:Lst[A]) extends Lst[A]

Cons("Hello", Empty)

/*
found   : Empty.type (with underlying type object Empty)
required: Lst[java.lang.String]
Note: Nothing <: java.lang.String (and Empty.type <: Lst[Nothing]), 
but trait Lst is invariant in type A.
You may wish to define A as +A instead. (SLS 4.5)
    Cons("Hello", Empty)
*/
```

---

```scala
sealed trait Lst[+A]
case object Empty extends Lst[Nothing]
case class Cons[A](head:A, tail:Lst[A]) extends Lst[A]

Cons("Hello", Empty)
```

---

```scala
sealed trait Lst[+A]{ // ok, fixed it!
  def ::(a:A):Lst[A] = Cons(a, this)
}
case object Empty extends Lst[Nothing]
case class Cons[A](head:A, tail:Lst[A]) extends Lst[A]

// i bruk
"Hello" :: Empty
```

---

```scala
sealed trait Lst[+A]{
  def ::(a:A):Lst[A] = Cons(a, this)
}
case object Empty extends Lst[Nothing]
case class Cons[A](head:A, tail:Lst[A]) extends Lst[A]

/*
covariant type A occurs in contravariant position in type A of value a
     def ::(a:A):Lst[A] = Cons(a, this)
            ^
*/
```

---

```scala
sealed trait Lst[+A]{
  def ::[B >: A](b:B):Lst[B] = Cons(b, this) // ok, fixed it
}
case object Empty extends Lst[Nothing]
case class Cons[A](head:A, tail:Lst[A]) extends Lst[A]

val lst:Lst[String] = "Hello" :: Empty    
val lst2:Lst[Any] = 1 :: "Hello" :: Empty
```
