# [Dispatch](http://dispatch.databinder.net/Dispatch.html)
* http klient bygget på [async-http-client](https://github.com/AsyncHttpClient/async-http-client)
* async, bruker [Futures](http://docs.scala-lang.org/overviews/core/futures.html)
* bygg request -> eksekver -> håndter respons
* retries
* json, tagsoup etc..
* ([Dispatch classic](http://dispatch-classic.databinder.net/Dispatch.html) bygger på [HttpClient](http://hc.apache.org/httpcomponents-client/))

---

```scala
import dispatch._, Defaults._

val svc = url("http://api.hostip.info/country.php")
val country = Http(svc OK as.String)

// non blocking foreach
for (c <- country)
  println(c)

// blocking!
val c = country()

// map, flatMap etc. (non-blocking)
val length = for (c <- country) yield c.length
```

---

## request
```scala
// legg til form-parameter
def myPostWithParams = myPost.addParameter("key", "value")

// POST + legg til form-parameter
def myPostWithParams = myRequest << Map("key" -> "value")

// POST body
def myPostWithBody = myRequest << """{"key": "value"}"""

// legg til query param
def myRequestWithParams = myRequest.addQueryParameter("key", "value")

// legg til query param
def myRequestWithParams = myRequest <<? Map("key" -> "value")

// PUT java.io.File
def myPut = myRequest <<< myFile
```

---

## response

```scala
Http(request OK as.String) //200 range, eller Failure

Http(request > as.String) //aksepterer alle

def f(r:Response) = ...
Http(request > f) //bruk min egen funksjon
```
