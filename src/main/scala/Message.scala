

/* Messaggi per la connessione */

case object ConnectionRequest
case object ConnectionSuccess
case object ConnectionFail

/* Email */

case class Email() {
  var destAddr: String = ""
  var srcAddr: String = ""
  var subject = new Array[Char](64)
  var body = new Array[Char](255)

  override def toString: String = {

    return "Dest: " + this.destAddr + "Source:" + this.srcAddr +
      "Subject: " + this.subject +
      "Body: " + this.body


  }
}

case object Ack

