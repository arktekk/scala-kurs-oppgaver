# sql
* alt fra Java!
* [Slick](http://slick.typesafe.com/)
* [sqlτyped](https://github.com/jonifreeman/sqltyped)
* og mye mer...

---

## slick

* fra typesafe
* "tabeller er scala collections"

---

```scala
object Coffees extends Table[(String, Int, Double)]("COFFEES") {
  def name  = column[String]("COF_NAME", O.PrimaryKey)
  def supID = column[Int]("SUP_ID")
  def price = column[Double]("PRICE")
  def * = name ~ supID ~ price
}
 
Coffees.insertAll(
  ("Colombian",         101, 7.99),
  ("Colombian_Decaf",   101, 8.99),
  ("French_Roast_Decaf", 49, 9.99))
 
val q = for {
  c <- Coffees if c.supID === 101
} yield (c.name, c.price)
```

---

```scala
case class Coffee(name: String, supID: Int, price: Double)
 
implicit val getCoffeeResult = GetResult(r => Coffee(r.<<, r.<<, r.<<))
 
Database.forURL("...") withSession {
  Seq(
    Coffee("Colombian", 101, 7.99),
    Coffee("Colombian_Decaf", 101, 8.99),
    Coffee("French_Roast_Decaf", 49, 9.99)
  ).foreach(c => sqlu"""
      insert into coffees values (${c.name}, ${c.supID}, ${c.price})
    """).execute)
 
  val sup = 101
  val q = sql"select * from coffees where sup_id = $sup".as[Coffee]
  //      A bind variable to prevent SQL injection ^
  q.foreach(println)
}
```

---

## sqlτyped
```scala
val q = sql("select name, age from person")
val r:List[Int] = q() map (_ get "age") // List(36, 14)

q() map (_ get "salary")
// error: No field String("salary") in record
```

---

```scala
val q = sql("select name, age from person where age > ?")

q("30") map (_ get "name")
// error: type mismatch;
//   found   : String("30")
//   required: Int

q(30) map (_ get "name") // ok
```