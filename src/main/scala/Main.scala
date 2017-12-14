import akka.actor.{ActorRef, ActorSystem}


object Main extends App {

  var clients = List[ActorRef]()
  val system = ActorSystem("ex1")
/*
   val id1: String = "alice@test.com"
  val id2: String = "bob@test.com"
*/
  val server = system.actorOf(Server.props(),name = "server")


  /* Inserimento Utenti (non registrati ) */
  var c = "n"
  while(c == "n") {
    var id : String = ""

      println("Inserisci email")
       id = scala.io.StdIn.readLine()
if(verifyEmail(id)) {
  val client = system.actorOf(Client.props(id, server), name = id)
  // val client = new Client(id,server)
  clients = client :: clients
  // Registrazione (provvisoria )
  client ! ConnectionRequest(id)
}else
      println("Formato email non corretto")



    println("Hai finito s/n ?")
    c = scala.io.StdIn.readLine()
  }

  /*
  val client = system.actorOf(Client.props(id1 , server),name = "client")
  val client1 = system.actorOf(Client.props(id2, server), name = "client1")


  client ! ConnectionRequest(id1)
  client1 ! ConnectionRequest(id2)


  Thread.sleep(1000)

  val msg = new Message("alice@test.com" + "&" + "bob@test.com" + "&" + "Test" + "&" + "body Test")
  val email = new Email(msg)


  server ! email
*/

  /* Scrittura Email */

  c = "n"
  while(c =="n") {
    println("Inserisci Email : (sorgente, destinazione, oggetto , testo) <separatore &> ")
    val txt = scala.io.StdIn.readLine()
    val msg = new Message(txt)
    if(msg.verifyMessage())
      {
        val email = new Email(msg)
        server ! email
      }

  }




  def verifyEmail(id : String): Boolean = {

    """(\w+)@([\w\.]+)""".r.unapplySeq(id).isDefined
  }
}
