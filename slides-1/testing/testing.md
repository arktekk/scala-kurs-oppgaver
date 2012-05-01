!SLIDE
# Testing #
* [ScalaTest](http://scalatest.org/)
* [Specs2](http://specs2.org)
* [ScalaCheck](http://code.google.com/p/scalacheck/)

!SLIDE
## [ScalaTest](http://scalatest.org/) ##
* Et av de eldste scala prosjektene
* Støtter en rekke forskjellige test former (junit -> bdd)
* Stort utvalg av matchere
* JUnit og TestNG integrasjon
* IDEA integrasjon

!SLIDE
```scala
import org.scalatest.FunSuite

class FirstSuite extends FunSuite {

  test("addition") {
    val sum = 1 + 1
    assert(sum === 2)
  }

  test("subtraction") {
    val diff = 4 - 1
    assert(diff === 3)
  }
}
```

!SLIDE
```scala
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers._

class FirstSuite extends FunSuite {

  test("addition") {
    val sum = 1 + 1
    sum should be === (2)
  }

  test("subtraction") {
    val diff = 4 - 1
    diff should be === (3)
  }
}
```

!SLIDE
```scala
val s = "hi"
val thrown = intercept[IndexOutOfBoundsException] {
  s.charAt(-1)
}
assert(thrown.getMessage === "String index out of range: -1")
```

!SLIDE
```scala font-60
import org.scalatest.{FunSuite, BeforeAndAfter}
import collection.mutable.ListBuffer

class ExampleSuite extends FunSuite with BeforeAndAfter {

  val builder = new StringBuilder
  val buffer = new ListBuffer[String]

  before {
    builder.append("ScalaTest is ")
  }

  after {
    builder.clear()
    buffer.clear()
  }

  test("easy") {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)
    buffer += "sweet"
  }

  test("fun") {
    builder.append("fun!")
    assert(builder.toString === "ScalaTest is fun!")
    assert(buffer.isEmpty)
  }
}
```

!SLIDE
```scala font-60
import org.scalatest.fixture.FixtureFunSuite
import java.io.{FileWriter, File}

class ExampleSuite extends FixtureFunSuite {

  final val tmpFile = "temp.txt"

  type FixtureParam = FileWriter

  def withFixture(test: OneArgTest) {
    val writer = new FileWriter(tmpFile) // set up the fixture
    try {
      test(writer) // "loan" the fixture to the test
    }
    finally {
      writer.close() // clean up the fixture
    }
  }

  test("easy") { writer =>
    writer.write("Hello, test!")
    writer.flush()
    assert(new File(tmpFile).length === 12)
  }

  test("fun") { writer =>
    writer.write("Hi, test!")
    writer.flush()
    assert(new File(tmpFile).length === 9)
  }
}
```

!SLIDE
## [Specs2](http://specs2.org) ##
* Etterfølger til Specs
* BDD/Spec orientert
* Gigantisk sett med matchere
* JUnit integrasjon

!SLIDE
```scala
import org.specs2._

class HelloWorldSpec extends Specification { def is =

    "This is a specification to check the 'Hello world' string"    ^
                                                                   p^
    "The 'Hello world' string should"                              ^
      "contain 11 characters"                                      ! e1^
      "start with 'Hello'"                                         ! e2^
      "end with 'world'"                                           ! e3^
                                                                   end
    
    def e1 = "Hello world" must have size(11)
    def e2 = "Hello world" must startWith("Hello")
    def e3 = "Hello world" must endWith("world")
  }
```

!SLIDE
## [ScalaCheck](http://code.google.com/p/scalacheck/) ##
* Scala port av [QuickCheck](http://www.cse.chalmers.se/~rjmh/QuickCheck/) fra Haskell
* property basert testing
* genererer testdata for verifisering av properties
* Integrert i både [ScalaTest](http://scalatest.org/) og [Specs2](http://specs2.org)

!SLIDE
```scala font-80
import org.scalacheck._

object StringSpecification extends Properties("String") {
  property("startsWith") = Prop.forAll((a: String, b: String) => (a+b).startsWith(a))

  property("endsWith") = Prop.forAll((a: String, b: String) => (a+b).endsWith(b))

  // Is this really always true?
  property("concat") = Prop.forAll((a: String, b: String) => 
    (a+b).length > a.length && (a+b).length > b.length
  )

  property("substring") = Prop.forAll((a: String, b: String) => 
    (a+b).substring(a.length) == b
  )

  property("substring") = Prop.forAll((a: String, b: String, c: String) =>
    (a+b+c).substring(a.length, a.length+b.length) == b
  )
}
```

!SLIDE
	+ String.startsWith: OK, passed 100 tests.                                    
	+ String.endsWith: OK, passed 100 tests.                                      
	! String.concat: Falsified after 0 passed tests.                              
	> ARG_0: 
	> ARG_1: 
	+ String.substring: OK, passed 100 tests.                                     
	+ String.substring: OK, passed 100 tests.
