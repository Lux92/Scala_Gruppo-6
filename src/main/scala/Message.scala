

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

    def unapply(arg: String ): Option[(String, String , String , String)] =  {
      val pts =arg split "&"
      if(pts.length == 4) {
        Some(pts(0), pts(1), pts(2), pts(3))
      } else {None}
    }

    def verifyMessage(): Boolean ={
      val mail  = this.unapply(this.txt).get
      val src = mail._1
      val dst = mail._2
      val s = mail._3
      val t = mail._4


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

  object Message {

    def verifyEmail(id : String): Boolean = {

      """(\w+)@([\w\.]+)""".r.unapplySeq(id).isDefined
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


