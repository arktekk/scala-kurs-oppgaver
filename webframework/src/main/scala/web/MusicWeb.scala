package web

import xml.NodeSeq
import javax.servlet.http.HttpServletRequest

trait MusicWeb extends WebApp {
  
  object Path {
    def unapply(r:HttpServletRequest) = Some(r.getRequestURI)
  }
  
  object Method {
    def unapply(r:HttpServletRequest) = Some(r.getMethod.toUpperCase)
  }
  
  object & {
    def unapply[A](a:A) = Some(a, a)
  } 
  
  def handle = {
    case r @ Method("GET") & Path("/") => Status.OK andThen Render(r.getRequestURI){
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

object MusicWeb extends MusicWeb

object RunMusicWeb extends App {    
  WebServer run MusicWeb
}