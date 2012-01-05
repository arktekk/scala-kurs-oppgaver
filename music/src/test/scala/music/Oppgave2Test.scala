package music

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers._
import xml.XML

class Oppgave2Test extends FunSuite {
  
  def loadFile(name:String) =
    XML.load(getClass.getResource(name + ".xml"))   
  
  lazy val tingtings = Music.parseSearchArtistResponse(loadFile("tingtings"))
  def artist = tingtings.head

  test("Music.parseSearchArtistResponse:tingtings.xml"){
    tingtings should have size 1    
  }
  
  test("artist name"){
    artist.name should be ("The Ting Tings")
  }
  
  test("artist albums"){
    artist.albums should have length 2
  }
  
  test("album titles"){
    artist.albums(0).name should be ("We Started Nothing")
    artist.albums(1).name should be ("Other Songs")
  }
  
  test("album years"){
    artist.albums(0).year should be ("2008")
    artist.albums(1).year should be ("")
  }
  
  test("album amazonLinks"){
    artist.albums(0).amazonLink should be ("http://www.amazon.com/exec/obidos/redirect?link_code=ur2&tag=wikia-20&camp=1789&creative=9325&path=external-search%3Fsearch-type=ss%26index=music%26keyword=The%20Ting%20Tings%20We%20Started%20Nothing")
    artist.albums(1).amazonLink should be ("http://www.amazon.com/exec/obidos/redirect?link_code=ur2&tag=wikia-20&camp=1789&creative=9325&path=external-search%3Fsearch-type=ss%26index=music%26keyword=The_Ting_Tings%20Other%20Songs")
  }
  
  test("album songs"){
    artist.albums(0).songs should be (Seq(
      "Great DJ",
      "That's Not My Name",
      "Fruit Machine",
      "Traffic Light",
      "Shut Up And Let Me Go",
      "Keep Your Head",
      "Be The One",
      "We Walk",
      "Impacilla Carpisung",
      "We Started Nothing"))
    
    artist.albums(1).songs should be (Seq(
      "Hands",
      "Hand",
      "Happy Birthday",
      "Hey",
      "We're Not The Same"))
  }

  test("Music.parseSearchArtistResponse:jokke.xml"){
    Music.parseSearchArtistResponse(loadFile("jokke")) should have size 1
  }
  
  test("Music.searchArtist"){
    Music.searchArtist("The Ting Tings") should equal (tingtings)
  }    
}