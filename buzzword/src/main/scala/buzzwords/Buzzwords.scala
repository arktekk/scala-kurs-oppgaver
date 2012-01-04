package buzzwords

import io.Source

object Buzzwords extends App{
  val content = Source.fromFile("buzz.txt", "UTF-8")
  
  val lines = content.getLines().toList
  
  
  lines.foreach(println)
}