import akka.actor.ActorSystem


object Main extends App {

  val system = ActorSystem("ex1")

   val id1: String = "asd"
  val id2: String = "psd"

  val server = system.actorOf(Server.props(),name = "server")
  val client = system.actorOf(Client.props(id1 , server),name = "client")
  val client1 = system.actorOf(Client.props(id2, server), name = "client1")

  client ! ConnectionRequest(id1)
  client1 ! ConnectionRequest(id2)


  Thread.sleep(1000)

  val email = new Email("psd", "asd", "test", "ciao")


  server ! email


}
