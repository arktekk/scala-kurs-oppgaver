package web

import xml.NodeSeq

trait MusicWeb extends WebApp {
  
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

object MusicWeb extends MusicWeb

object RunMusicWeb extends App {    
  WebServer run MusicWeb
}