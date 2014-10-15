package chat

// remote messages
case class Send(msg:String)
case class Message(from:String, msg:String)
case class Subscribe(nick:String)

case class StatusMessage(msg:String)

// errors
sealed trait Error
case class NickTaken(nick:String) extends Error
