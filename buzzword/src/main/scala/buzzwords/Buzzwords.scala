package buzzwords

import io.Source

object Buzzwords extends App{
  val content = Source.fromFile("buzz.txt", "UTF-8")
  
  val lines = for{
    line <- content.getLines()
  } yield line
  
  lines.foreach(println)
}