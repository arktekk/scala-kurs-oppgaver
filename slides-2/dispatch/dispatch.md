!SLIDE
# [Dispatch](http://dispatch.databinder.net/Dispatch.html) #
* scala [http](http://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) klient bibliotek
* wrapper for [Apache Http client](http://hc.apache.org/httpcomponents-client-ga/index.html)
* masse [symboler](http://www.flotsam.nl/dispatch-periodic-table.html), http dsl
* blocking, nio og google-appengine executors
* integrerer med mye forskjellig som f.eks [TagSoup](http://ccil.org/~cowan/XML/tagsoup/)

!SLIDE

* request builder - definerer request
* handler - håndterer respons
* executor - gjør selve kallet

```scala
import dispatch._

val request = url("http://www.yr.no/place/Norway/Telemark/Sauherad/Gvarv/forecast_hour_by_hour.xml")
val handler = request.as_str
val result = Http(handler)
```

!SLIDE
## request ##
```scala
url("http://www.yr.no") / "place" / "Norway" / "Telemark" / "Sauherad" / "Gvarv" / "forecast_hour_by_hour.xml"

url("http://sporing.posten.no/sporing.html") <<? Map("q" -> "123123123")
```

!SLIDE
## handlers ##
```scala
val http = new Http
val request = url("http://scala-lang.org")

http(request >>> System.out) // til OutputStream

http(request as_str) // som string

http(request <> (xml:Elem => xml \ "foo" \ "bar") // håndtert som xml

import tagsoup.TagSoupHttp._
http(request </> (xml:NodeSeq => xml \\ "body" \ "@href") // vasket html og håndtert som xml
```

!SLIDE
## executors ##
* Threadsafe m/threadpool  `Http / new Http with thread.Safety`
* Current Thread           `new Http`
* NIO                      `new nio.Http`
* Google App Engine        `new gae.Http`

!SLIDE
## eksempel ##
```scala
import xml._
import dispatch._

val http = new Http
def parse(xml:Elem) = 
  for {
    consignment <- xml \ "Consignment"
    totalweight <- consignment \ "TotalWeight"
  } yield totalweight.text

http(url("http://beta.bring.no/sporing/sporing.xml") <<? Map("q" -> "TESTPACKAGE-AT-PICKUPPOINT") <> parse)

<ConsignmentSet xmlns="http://www.bring.no/sporing/1.0">
  <Consignment consignmentId="SHIPMENTNUMBER">
    <TotalWeight unitCode="kg">16.5</TotalWeight>
      ..

// List(16,5)
```

!SLIDE
## Oppgavetid :-) ##