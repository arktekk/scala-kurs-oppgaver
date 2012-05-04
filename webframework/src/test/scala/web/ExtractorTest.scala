package web

import org.scalatest.FunSuite
import org.eclipse.jetty.server.Request
import javax.servlet.http.HttpServletRequest

class ExtractorTest extends FunSuite {
  
  test("Path matcher /"){
    val request:HttpServletRequest = new Request{
      override def getRequestURI = "/"
    }
    
    request match {
//      case Path("/") =>
      case _ => fail()
    }
  }
  
  test("Path matcher /hello"){
    val request:HttpServletRequest = new Request{
      override def getRequestURI = "/hello"
    }
    
    request match {
    //  case Path("/hello") =>
      case _ => fail()
    }
  }
  
  test("Method extractor"){
    val request:HttpServletRequest = new Request{
      override def getMethod = "GET"
    }
    
    request match {
    //  case Method(method) => method should be === request.getMethod
      case _ => fail()
    }
  }
  
  test("GET Method extractor"){
    val request:HttpServletRequest = new Request{
      override def getMethod = "GET"
    }
    
    request match {
    //  case GET() =>
      case _ => fail() 
    }
  }
  
  test("POST Method extractor"){
    val request:HttpServletRequest = new Request{
      override def getMethod = "POST"
    }
    
    request match {
    //  case POST() =>
      case _ => fail()
    }
  }
  
  test("Parts extractor"){
    
    "/scala/kurs/web/framework" match {
    //  case Parts("scala", "kurs", "web", "framework") =>
      case _ => fail()
    }    
  }
  
  test("Request Params extractor"){
    import collection.JavaConverters._
    val params = Map("A" -> Array("1", "2"), "B" -> Array("3", "4"))
    
    val request:HttpServletRequest = new Request{
      override def getParameterNames = params.keysIterator.asJavaEnumeration
      override def getParameter(name:String) = params.get(name).flatMap(_.headOption).orNull
      override def getParameterValues(name: String) = params.get(name).orNull
      override def getParameterMap = params.asJava
    }
    
    request match {
    //  case Params(p) => p should be === params.mapValues(_.toSeq)
      case _ => fail()
    }
  }
  
  test("Headers extractor"){
    import collection.JavaConverters._
    val headers = Map("Accept" -> Seq("text/html", "application/xhtml+xml"))
    
    val request:HttpServletRequest = new Request{
      override def getHeaders(name: String) = headers.get(name).flatten.iterator.asJavaEnumeration
      override def getHeaderNames = headers.keysIterator.asJavaEnumeration
      override def getHeader(name: String) = headers.get(name).flatMap(_.headOption).orNull
    }
    
    request match {
    //  case Headers(h) => h should be === headers
      case _ => fail()
    }
  }
  
  /*
    Lag en webapp for Music api'et du laget i går!
    Det skal være mulig å 
    (1)+søke etter band og liste ut album
    (2)+liste ut sanger for et album (linkes til fra (1))
    (3)+vise frem fulle lyrics for en sang (linkes til fra (2))    
   */
}