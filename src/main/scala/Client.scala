import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging

/* Client Actor che ha:
   - Un id corrispondente all' Email
   - Un ActorRef (ovvero il Server )
   - Una lista delle mail ricevute
* */

object Client {
  def props( id: String , server : ActorRef  ): Props = Props(new Client(id , server))
}

class Client(id : String , server : ActorRef) extends Actor {

  /* Lista mail ricevute */

 var inbox = List[Email]()

 val log = Logging(context.system,this)

  override def receive: Receive = {


    /* Gestione della connessione */
    case  ConnectionRequest(id) =>
      log.info("Inizializzazione della Connessione")
      val id : String = scala.io.StdIn.readLine()

   /*
     if(verifyId(id))
        println("email corretta")
*/
             server ! ConnectionRequest(id)


    case ConnectionSuccess=>
      log.info("Connessione Riuscita")
      // WriteMAIL
      //Verify Mail
      // server ! Email

    case ConnectionFail =>
      log.info("Connessione Fallita")

      /* Email */

      /* Email ricevuta */
    case Email(destAddr, srcAddr , subject , body)=>
      log.info("Ricevuta Email  da " + srcAddr )
       /* Aggiunge a Inbox*/
      inbox =  Email(destAddr,srcAddr, subject , body) :: inbox
      log.info("Aggiunta ad Inbox , invio ACK")
      viewInbox
        /* Invia ACK */
      server ! Ack(destAddr,srcAddr)


    case Ack(srcAddr, dstAddr) =>
      log.info("Ricevuto ACK da " + srcAddr  )

  }

  /* Funzione che printa le mail */
  def viewInbox() : Unit = {
    println("EMAIL RICEVUTE: ")
    inbox.foreach((email: Email) => println(email.toString))
  }

    def writeMail(destAddr: String , subject: String , body: String) : Email ={
      val email =  Email(this.id , destAddr, subject,body )
      email
    }

    def verifyMail(email : Email) : Boolean = {
        if(email.subject.length() <= 64 && email.body.length() <= 255)
      true
        else false
    }


  /* Funzione che verifica il formato dell'email */
  def verifyId( id : String)
 : Boolean = {
    """(\w+)@([\w\.]+)""".r.unapplySeq(id).isDefined

  }
}