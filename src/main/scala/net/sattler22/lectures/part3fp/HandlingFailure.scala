package net.sattler22.lectures.part3fp

import scala.util.{Failure, Random, Success, Try}

/**
 * Handling Failure Takeaways:
 * <ol>
 *   <li>Exceptions are handled inside <i>try-catch</i> blocks, but:</li>
 *   <ul>
 *     <li>Multiple/nested blocks make the code hard to follow</li>
 *     <li>Multiple operations that are prone to failure cannot be chained together</li>
 *   </ul>
 *   <li>Use <i>Try</i> to handle exceptions gracefully:</li>
 *   <ul>
 *     <li>Avoids runtime crashes due to uncaught exceptions</li>
 *     <li>Avoids too many nested <i>try-catch</i> blocks</li>
 *   </ul>
 *   <li><i>Try</i> is a wrapper for a computation that might fail:</li>
 *   <ul>
 *     <li><i>Success</i> wraps the result of a successful computation</li>
 *     <li><i>Failure</i> holds the <i>Throwable</i> that would've been thrown upon failure</li>
 *     <li>The companion object's <i>apply</i> method takes care of instantiating the correct case class</li>
 *     <li>It is functional way of dealing with failure (<i>map</i>, <i>flatMap</i>, <i>filter</i>, <i>orElse</i>, etc.)</li>
 *   </ul>
 *   <li>When designing a method to return some type which may throw an exception, return a <i>Try</i> of that type instead</li>
 * </ol>
 */
object HandlingFailure extends App {

  println(s"Successful value: ${Success(42)}")                                                   //Success(42)
  println(s"Computation failed: ${Failure(new RuntimeException("Something bad happened!!!"))}")  //Failure(java.lang.RuntimeException: Something bad happened!!!)

  //Try objects:
  def unsafeMethod(): String = throw new RuntimeException("No String for you!!!")                //Failure(java.lang.RuntimeException: No String for you!!!)
  def backupMethod(): String = "A valid result"
  println(s"Potential failure: ${Try(unsafeMethod())}")
  val potentialFailure = Try {
    throw new RuntimeException("Syntactic sugar...")
  }
  println(s"Syntactic sugar: $potentialFailure")                                                 //Failure(java.lang.RuntimeException: Syntactic sugar...)
  println(s"Is successful: [${potentialFailure.isSuccess}]")                                     //[false]
  println(s"Try orElse chain: ${Try(unsafeMethod()).orElse(Try(backupMethod()))}")               //Success(A valid result)

  //Design unsafe APIs to wrap your result in a Try instead:
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException("No better String for you!!!"))
  def betterBackupMethod(): Try[String] = Success("A better, valid result")
  println(s"Better potential failure: ${betterUnsafeMethod() orElse betterBackupMethod()}")      //Success(A better, valid result)

  //Functions on Try:
  val success1 = Success(3)
  println(s"Map: ${success1.map(_ * 2)}")                                                        //Success(6)
  println(s"FlatMap: ${success1.flatMap(x => Success(x * 10))}")                                 //Success(30)
  println(s"Filter: ${success1.filter(_ > 10)}")                                                 //Failure(java.util.NoSuchElementException: Predicate does not hold for 3)

  /** Exercise */
  //Given the following API, try to print the HTML page:
  //  -> If it connects, then call the renderHtml method
  def renderHtml(page: String) = println(page)

  class Connection {
    def get(uri: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) s"<html><h1>Home Page ($uri)</h1></html>"
      else throw new RuntimeException("Connection interrupted!!!")
    }
    //NOTE: Added as part of the solution!!!
    def getSafe(uri: String): Try[String] = Try(get(uri))
  }

  object HttpService {
    def getConnection(host: String, port: String): Connection = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException(s"Port $port is unavailable")
    }
    //NOTE: Added as part of the solution!!!
    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }
  //Functional solution:
  val host: String = "localhost"
  val port: String = "8080"
  val functionalPossibleConnection = HttpService.getSafeConnection(host, port)
  val functionalPossibleHtml = functionalPossibleConnection.flatMap(connection => connection.getSafe("/functional.html"))
  println(s"Functional connection: $functionalPossibleConnection")
  println(s"Functional HTML: $functionalPossibleHtml")
  functionalPossibleHtml.foreach(println)

  //Chained method solution:
  val chainedMethodPossibleHtml = HttpService.getSafeConnection(host, port)
                                             .flatMap(connection => connection.getSafe("/chained.html"))
  println(s"Chained method HTML: $chainedMethodPossibleHtml")
  chainedMethodPossibleHtml.foreach(println)

  //For-comprehensions:
  //NOTE: Given a connection which is obtained from the HTTP service (using the host and port) and an HTML page which is
  //      obtained from the connection, then render the HTML page. Otherwise, yield Failure.
  //
  //Same as this imperative logic:
  //  -> try {
  //  ->   connection = HttpService.getConnection(host, port)
  //  ->   try {
  //  ->     html = connection.get("/imperative.html)
  //  ->     renderHtml(html)
  //  ->   catch(Exception e) {
  //  ->     //Handle connection get exception...
  //  ->   }
  //  -> }
  //  -> catch(Exception e) {
  //  ->   //Handle HTTP service exception...
  //  -> }
  val forComprehensionPossibleHtml = for {
    connection <- HttpService.getSafeConnection(host, port)
    html <- connection.getSafe("/for-comprehension.html")
  } yield renderHtml(html)
  if (forComprehensionPossibleHtml.isFailure)
    println(s"For-comprehension HTML: $forComprehensionPossibleHtml")
}
