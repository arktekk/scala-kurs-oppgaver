package music

import xml._

object Music {
  
  private def todo = sys.error("TODO")
  
  /*
  http://lyrics.wikia.com/api.php?func=getArtist&fmt=xml&fixXML&artist={artist}
   */
  def searchArtist(artist:String):Seq[Artist] = todo
  
  /*
   http://lyrics.wikia.com/api.php?fmt=xml&artist={artist}&song={song}
   */
  def findSong(artist:String, song:String):Seq[Song] = todo
  
  
  /*
  i retur url fra "findSong" ligger den en url til full html versjon av lyrics for sangen
  Benytt TagSoup støtten i Dispath til å hente ut lyrics fra denne html siden
   */
  def findLyrics(page:String):String = todo
  
  /*
  skal benyttes for parsing av "searchArtist"
   */
  def parseSearchArtistResponse(xml:Elem):Seq[Artist] = todo  
  
  /*
  scala benyttes for parsing av "findSong"
   */
  def parseFindSongResponse(xml:Elem):Seq[Song] = todo
}

case class Artist(name:String, albums:Seq[Album])
case class Album(name:String, year:String, amazonLink:String, songs:Seq[String])
case class Song(artist:String, title:String, lyrics:String, url:String)