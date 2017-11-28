

/* Messaggi per la connessione */

case object ConnectionRequest
case object ConnectionSuccess
case object ConnectionFail

/* Email */

case class Email() {
var  destAddr : String = ""
  var srcAddr : String = ""
  var subject = new  Array[Char](64)
  var body = new Array[Char](255)
}

case object Ack

