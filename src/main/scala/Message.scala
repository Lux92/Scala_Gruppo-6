

  /* Messaggi per la connessione */
  case object Init
  case class ConnectionRequest(id :String)
  case object ConnectionSuccess
  case object ConnectionFail

  /* Messaggio */

  case class Message(txt : String){
    def getSrcAddr(): String = {
      val roba  = this.unapply(this.txt).get
      val src = roba._1
      src
    }
    def getDstAddr(): String = {
      val roba  = this.unapply(this.txt).get
      val dst = roba._2
      dst
    }

    def getSub( ): String = {

      val roba  = this.unapply(this.txt).get
      val sub = roba._3
      sub

    }

    def getBody():String = {
      val roba  = this.unapply(this.txt).get
      val body = roba._4
      body
    }

    def unapply(arg: String ): Option[(String, String , String , String)] =  {
      val pts =arg split "&"
      if(pts.length == 4) {
        Some(pts(0), pts(1), pts(2), pts(3))
      } else {None}
    }


    def verifyMessage(): Boolean ={
      val roba  = this.unapply(this.txt).get
      val src = roba._1
      val dst = roba._2
      val s = roba._3
      val t = roba._4

      /* Usare Eccezioni ? Fa figo! */

      if(verifyEmail(src))
        println("Email sorgente corretta")
      if(verifyEmail(dst))
        println("Email destinatario corretta")
      if(s.length()> 64)
        println("Oggetto Email troppo grande")
      if(t.length() > 255)
        println("Testo Email troppo grande")

      if(verifyEmail(src) && verifyEmail(dst) && s.length() <= 64 && t.length() <= 255)
        true
      else
        false
    }

    def verifyEmail(id : String): Boolean = {

      """(\w+)@([\w\.]+)""".r.unapplySeq(id).isDefined
    }

    override def toString: String = {
      val roba  = this.unapply(this.txt).get
      val src = roba._1
      val dst = roba._2
      val s = roba._3
      val t = roba._4
     "Sorgente: " +  src + "Destinatario: " + dst +"Oggetto: " + s + "Testo: "+ t

    }




  }

  case class Email( msg: Message){

    override def toString: String =
    {
      msg.toString
    }
  }

  case class Ack(srcAddr : String , dstAddr : String) {

  }


