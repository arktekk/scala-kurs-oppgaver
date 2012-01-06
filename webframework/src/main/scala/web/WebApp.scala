package web

import javax.servlet.{ServletConfig, ServletResponse, ServletRequest, Servlet}
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import xml.NodeSeq

object Response {  
  def apply(f:HttpServletResponse => Unit):HttpServletResponse => HttpServletResponse = {
    in => f(in); in
  }
}

object Status {
  val OK = Response(_.setStatus(200))
  val NOT_FOUND = Response(_.sendError(404))
}

object Html {
  def apply(html:NodeSeq) = Response(_.getWriter.write("<!DOCTYPE html>\n" + html.toString()))
}

trait WebApp extends Servlet {
  
  private var _config:ServletConfig = _
  
  def init(config: ServletConfig) {
    _config = config
  }

  def getServletConfig = _config
  def getServletInfo = "WebApp"
  def destroy() {}

  def service(req: ServletRequest, res: ServletResponse) {
    (req, res) match {
      case (hreq:HttpServletRequest, hres:HttpServletResponse) =>
        handle.lift(hreq).getOrElse(Status.NOT_FOUND)(hres)
    }
  }

  def handle:PartialFunction[HttpServletRequest, HttpServletResponse => HttpServletResponse]
}