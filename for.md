# for-comprehensions

---

* konsis måte å jobbe med "collections" og lignende
* haskell, python, erlang og mange andre har varianter
* syntax sukker for 
	*`foreach`
	*`map`
	*`flatMap`
	*`withFilter`
* lettere ?

---

## foreach ##
```scala
for {
  a <- List(1, 2)
  b <- List(3, 4)
} println(a + b)

List(1, 2).foreach {
  a => List(3, 4).foreach {
    b => println(a + b)
  }
}
```

---

## map ##
```scala
for {
  a <- List(1, 2)
} yield a + 1

List(1, 2).map(a => a + 1)
```

---

## flatMap ##
```scala
for {
  a <- List(1, 2)
  b <- List(3, 4)
  c <- List(5, 6)
} yield a + b + c

List(1, 2).flatMap {
  a => List(3, 4).flatMap {
    b => List(5, 6).map {
      c => a + b + c
    }
  }
}
```

---

## withFilter ##
```scala
for {
  a <- List(1, 2)
  b <- List(3, 4)
  if a + b < 5
} yield a * b

List(1, 2).flatMap {
  a => List(3, 4).withFilter {
    b => a + b < 5
  }.map{
    b => a * b
  }
}
```

---

## pattern matching ##
```scala
val R = "\\d+".r
for {
  R(a) <- List("123", "abc", "321")    
} yield a

List("123", "abc", "321").withFilter {
  case R(a) => true
  case _ => false
}.map{
  case R(a) => a
}
```

---

## inline variabler ##
```scala
for {
  a <- List(1, 2)
  b = a + 1
  c <- List(3, 4)
} yield a + b + c

List(1, 2).map { a =>
  val b = a + 1
  (a, b)
}.flatMap { case (a, b) =>
  List(3, 4).map {
    c => a + b + c
  }
}
```

---

## fordeler og ulemper ? ##
* skrive
* lese
* forstå
* forstå logikken