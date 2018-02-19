

  /* Messaggi per la connessione */
  case class ConnectionRequest(id :String)
  case object ConnectionSuccess
  case object ConnectionFail

  /* Messaggio */

  case class Message(txt : String){
    def getSrcAddr(): String = {
      val mail  = this.unapply(this.txt).get
      val src = mail._1
      src
    }
    def getDstAddr(): String = {
      val mail  = this.unapply(this.txt).get
      val dst = mail._2
      dst
    }

    def getSub( ): String = {

      val mail  = this.unapply(this.txt).get
      val sub = mail._3
      sub

    }

    def getBody():String = {
      val mail  = this.unapply(this.txt).get
      val body = mail._4
      body
    }

    /**
      * funzione che splitta la stringa con separatore &
      */
    def unapply(arg: String ): Option[(String, String , String , String)] =  {
      val pts =arg split "&"
      if(pts.length == 4) {
        Some(pts(0), pts(1), pts(2), pts(3))
      } else {None}
    }

    /*
      verica formato mail secondo regex
      */
    def verifyEmail(id : String): Boolean = {

      """(\w+)@([\w\.]+)""".r.unapplySeq(id).isDefined
    }

    /* Verifica formato mail secondo regex */
    def verifyMailFormat(msg : String): Boolean = {
      """(\w+)@([\w\.]+)&(\w+)@([\w\.]+)&(\w{0,64})&(\w{0,255})""".r.unapplySeq(msg).isDefined
    }

    override def toString: String = {
      val mail  = this.unapply(this.txt).get
      val src = mail._1
      val dst = mail._2
      val s = mail._3
      val t = mail._4
     "Sorgente: " +  src + "Destinatario: " + dst +"Oggetto: " + s + "Testo: "+ t

    }




  }
/* Oggetto che contiene il messaggio */
  object Message {

    def verifyEmail(id : String): Boolean = {

      """(\w+)@([\w\.]+)""".r.unapplySeq(id).isDefined
    }
  }


  /* Email che contiene l'oggetto message */

  case class Email( msg: Message){

    override def toString: String =
    {
      msg.toString
    }
  }

  /* Messaggio di ACK */
  case class Ack(srcAddr : String , dstAddr : String) {

  }


