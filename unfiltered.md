# Unfiltered
* toolkit for å håndtere http request
* bittelite (core er 1376 CLOC)
* pattern matching, funksjoner
* veldig nært HTTP

---

```nocode
GET / HTTP/1.1
User-Agent: curl/7.19.7
Host: localhost:8080
Accept: */*

HTTP/1.1 200 OK
Content-Type: text/html; charset=utf-8
Content-Length: 357
Server: Jetty(7.6.0.v20120127)
```
```scala
class Example extends Plan {
  def intent = {
    case GET(Path("/")) => OK ~> Html5(<html>...</html>)    
  }
}
```

---

* http api
* synkront | async
* servlets, jetty, netty...
* websockets
* uploads
* oauth, oauth2
* directives (høynivå api)

---

## design
* Intents
* Plans
* Request matchers
* Response functions
* Servers

---

## Intent
```scala
// sync
type Intent[-A,-B] = PartialFunction[HttpRequest[A], ResponseFunction[B]]

//async
type Intent[-A,-B] =
  PartialFunction[HttpRequest[A] with Responder[B], Any]

trait Responder[+R] {
  def respond(rf: unfiltered.response.ResponseFunction[R])
}
```

---

## Plan
```scala
trait Plan extends Filter {
  def intent:Intent[HttpServletRequest, HttpServletResponse]

  def doFilter(request: ServletRequest,
               response: ServletResponse,
               chain: FilterChain) = ...
}
```

---

## request matchers

* støtter det vanligste
* extractors (pattern matching) / lett å lage egne

```scala
object Path {
  def unapply[T](req: HttpRequest[T]) = Some(req.uri.split('?')(0))
}
object Seg {
  def unapply(path: String): Option[List[String]] = 
    path.split("/").toList match {
      case "" :: rest => Some(rest) // skip a leading slash
      case all => Some(all)
    }
}
```

---

```nocode
GET /item/5 HTTP/1.1
User-Agent: curl/7.19.7
Host: localhost:8080
Accept: */*
```
```scala
def intent = {
  case Path(Seg(List("item", number))) => // number == "5"
    Ok ~> Html5(...)
  
  case Path(Seg(List("you", can, "have", many))) =>
    Ok ~> Html5(...)
}
```

---

```nocode
GET /item?filter=foo&filter=bar&monkey=donkey HTTP/1.1
User-Agent: curl/7.19.7
Host: localhost:8080
Accept: */*
````
```scala
def intent = {
  case Path("/item") & QueryParams(q) =>
    val filter = q("filter") // Seq("foo", "bar")
    val monkey = q("monkey") // Seq("donkey")
    val nope   = q("nope")   // Seq()
    Ok ~> Html5(...)    
}
```

---

## response functions
```scala
def intent = {
  case Path("/") =>
    ServiceUnavailable ~> TextXmlContent ~> 
      ResponseString("<error>not available</error>)
}
```
```nodecode
HTTP/1.1 503 Service Unavailable
Content-Type: text/xml; charset=utf-8
Content-Length: 28
Server: Jetty(7.6.0.v20120127)

<error>not available</error>
```

---

## servers
* jetty
* netty

```scala
unfiltered.jetty.Http.local(8080).filter(new Example).run()
```