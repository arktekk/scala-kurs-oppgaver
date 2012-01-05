package music

import org.scalatest.matchers.ShouldMatchers._
import xml.XML
import org.scalatest.{FunSuite}

class Oppgave1Test extends FunSuite {

  def loadXml(name:String) = XML.load(getClass.getResource(name + ".xml"))
  
  lazy val altkanrepareres = Music.parseFindSongResponse(loadXml("altkanrepareres"))
  def song = altkanrepareres.head

  test("Music.parseLyricsResponse"){
    altkanrepareres should have size 1
  }
  
  test("song artist"){
    song.artist should be ("Jokke & Valentinerne")
  }
  
  test("song title"){
    song.title should be ("Alt Kan Repareres")
  }
  
  test("song lyrics"){
    song.lyrics should be (
"""All den dopen som jeg tok i går
Kommer ikke til å hjelpe meg når jeg våkner
Forhåpentligvis i en seng

Og all den alk'en so[...]""")
  }
  
  test("song url"){
    song.url should be ("http://lyrics.wikia.com/Jokke_%26_Valentinerne:Alt_Kan_Repareres")
  }

  test("Music.findSong"){
    Music.findSong("Jokke", "Alt kan repareres") should be (altkanrepareres)
  }
}