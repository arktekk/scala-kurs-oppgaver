package web

import xml.NodeSeq

trait MyApp extends WebApp {
   
  def handle = {
    case r => Status.OK andThen Render(r.getRequestURI){
      <h1>{r.getMethod + " " + r.getRequestURI}</h1>
    }
  }
  
  def Render(title:String)(body:NodeSeq) = Html{
    <html>
      <head><title>{title}</title></head>
      <body>
        {body}
      </body>
    </html>
  }
}

object MyApp extends MyApp

object RunMyApp extends App {    
  WebServer run MyApp
}
