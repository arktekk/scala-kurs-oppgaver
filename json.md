# json

---

* [json4s](https://github.com/json4s/json4s)
* [argonaut](http://argonaut.io/)
* og mange fler

---

## json4s

* wrapper for mange json bibliotek (lift, jackson etc.)
* felles ast
* veldig enkel og grei

---

```scala
sealed abstract class JValue
case object JNothing extends JValue // 'zero' for JValue
case object JNull extends JValue
case class JString(s: String) extends JValue
case class JDouble(num: Double) extends JValue
case class JDecimal(num: BigDecimal) extends JValue
case class JInt(num: BigInt) extends JValue
case class JBool(value: Boolean) extends JValue
case class JObject(obj: List[JField]) extends JValue
case class JArray(arr: List[JValue]) extends JValue

type JField = (String, JValue)
```

---

```scala
import org.json4s._
import org.json4s.native.JsonMethods._

parse(""" { "numbers" : [1, 2, 3, 4] } """)

// JObject(List((numbers,JArray(List(JInt(1), JInt(2), JInt(3), JInt(4))))))
```

---

## argonaut

* basert på [scalaz](http://github.com/scalaz)
* codecs for mapping mellom scala/json
* zippers for navigasjon/oppdatering av json
* lignende api som json4s i bunn - men masse mer

---

```scala
import argonaut._, Argonaut._
 
case class Person(name: String, age: Int, things: List[String])
 
implicit def PersonCodecJson =
  casecodec3(Person.apply, Person.unapply)("name", "age", "things")

val person = Person("Bam Bam", 2, List("club"))

val json: Json = person.asJson

val prettyprinted: String = json.spaces2

val parsed: Option[Person] = prettyprinted.decodeOption[Person]
```