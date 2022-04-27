package net.sattler22.lectures.part3fp

import scala.util.Random

/**
 * Options Takeaways:
 * <ol>
 *   <li>Tony Hoare's Billion Dollar Mistake (the NULL reference)</li>
 *   <li>In Scala, an <i>Option</i> represents the possible absence of a value:</li>
 *   <ul>
 *     <li><i>Some</i> wraps a concrete value</li>
 *     <li><i>None</i> is a singleton for absent values</li>
 *     <li>It is a functional way of dealing with absence (<i>map</i>, <i>flatMap</i>, <i>filter</i>, <i>orElse</i>, etc.)</li>
 *   </ul>
 *   <li>Use them to stay away from:</li>
 *   <ul>
 *     <li>Runtime crashes due to NULL pointer exceptions (NPEs)</li>
 *     <li>Numerous NULL related assertions</li>
 *   </ul>
 *   <li>When designing a method that may return NULL, always return an <i>Option</i> of that type instead</li>
 * </ol>
 */
object Options extends App {

  println(s"Has a value: ${Some(4)}")                                                      //Some(4)
  println(s"Absence of a value: ${None}")                                                  //None

  //Work with unsafe APIs:
  def unsafeMethod(): String = null
  println(s"DO NOT DO THIS (Always wrap a valid value): ${Some(unsafeMethod())}")          //Some(null)
  println(s"Use Option apply on unsafe APIs: ${Option(unsafeMethod())}")                   //Will return Some or None
  def backupMethod(): String = "Valid value"
  println(s"Chained methods: ${Option(unsafeMethod()).orElse(Option(backupMethod()))}")    //Some(Valid value)

  //Design unsafe APIs to return Options instead:
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("Better value")
  println(s"Better chained methods: ${betterUnsafeMethod() orElse betterBackupMethod()}")  //Some(Better value)

  //Functions on Options:
  println(s"Is empty: [${Some(4).isEmpty}]")                                               //[false]
  println(s"DO NOT DO THIS (Defeats purpose of options): [${Some(4).get}]")                //[4]
  println(s"Map: ${Some(4).map(_ * 2)}")                                                   //Some(8)
  println(s"FlatMap: ${Some(4).flatMap(x => Option(x * 10))}")                             //Some(40)
  println(s"Filter: ${Some(4).filter(x => x > 10)}")                                       //None (nothing matches predicate)

  /** Exercise */
  //Given the following API, try to establish a connection:
  //  -> If it connects, then call the connected method
  //  -> The configuration is fetched from somewhere else, so it might be missing!!!
  val config: Map[String, String] = Map(
    "host" -> "75.258.219.51",
    "port" -> "110"
  )

  class Connection {
    def connect = "Connected to server!!!"
  }

  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean()) Some(new Connection)
      else None
    }
  }
  //Functional solution:
  val host = config.get("host")
  val port = config.get("port")

  //Same as this imperative logic:
  //  -> if (h != null)
  //  ->   if (p != null)
  //  ->     return Connection.apply(h, p)
  //  -> return null
  val functionalConnection = host.flatMap(h => port.flatMap(p => Connection(h, p)))

  //Same as this imperative logic:
  //  -> if (c != null)
  //  ->   return c.connected
  //  -> return null
  val functionalConnectionStatus = functionalConnection.map(c => c.connect)

  //Same as this imperative logic:
  //  -> if (connectionStatus == null) println(None)
  //  -> else println(Some(connectionStatus).get)
  println(s"Functional connection status: $functionalConnectionStatus")

  //Same as this imperative logic:
  //  -> if (connectionStatus != null)
  //  ->   println(connectionStatus)
  functionalConnectionStatus.foreach(println)

  //Chained method solution:
  val chainedMethodConnectionStatus = config.get("host")
                                            .flatMap(host => config.get("port")
                                            .flatMap(port => Connection(host, port))
                                            .map(connection => connection.connect))
  println(s"Chained method connection status: $chainedMethodConnectionStatus")
  chainedMethodConnectionStatus.foreach(println)

  //For-comprehensions:
  //NOTE: Given a host which is obtained by x, a port which is obtained by y and a connection which is obtained by z, then
  //      yield (give me) connection.connection. Otherwise, yield None
  val forComprehensionConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  println(s"For-comprehension connection status: $forComprehensionConnectionStatus")
  forComprehensionConnectionStatus.foreach(println)
}
