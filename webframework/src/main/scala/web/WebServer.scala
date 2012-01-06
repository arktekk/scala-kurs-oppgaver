package web

import org.eclipse.jetty.server.Server
import javax.servlet.Servlet
import org.eclipse.jetty.servlet.{ServletHolder, ServletContextHandler}

object WebServer {

  def run(servlet: Servlet) {

    val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
    context.setContextPath("/")
    context.addServlet(new ServletHolder(servlet), "/*")
    val server = new Server(8080)
    server.setHandler(context)
    
    server.start()
    Console.readLine()
    server.stop()
  }
}