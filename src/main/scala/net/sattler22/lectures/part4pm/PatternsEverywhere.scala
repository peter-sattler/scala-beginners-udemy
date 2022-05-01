package net.sattler22.lectures.part4pm

/**
 * Patterns Everywhere Takeaways:
 * <ol>
 *   <li>The <i>catch</i> clause of a <i>try-catch</i> expression uses pattern matching</li>
 *   <li>Generators within <i>for-comprehensions</i> are based on pattern matching</li>
 *   <li>Pattern matching also works for case classes, the prepend operator (<i>::</i>), etc.</li>
 *   <li>Multiple values can be assigned by exploiting the name binding property of pattern matching</li>
 *   <li>Partial function literals are also based on pattern matching</li>
 * </ol>
 */
object PatternsEverywhere extends App {

  //1. The catch clause of a try-catch expression uses pattern matching:
  try {
    //some code goes here
  } catch {
    case rte: RuntimeException => "Matched runtime exception"
    case npe: NullPointerException => "Matched NULL pointer exception"
    case _ => "Matched something else"
  }

  //2. Generators are based on pattern matching:
  val list1 = List(1,2,3,4)
  val evenOnly = for {
    x <- list1 if x % 2 == 0                             //Evens only generator
  } yield x * 10
  println(s"Evens only: ${evenOnly}")                    //List(20, 40)

  val tuples1 = List((1,2), (3,4))
  val tuples2 = for {
    (first, second) <- tuples1                           //Decomposed in the same way as tuple pattern matching
  } yield first * second
  println(s"Tuples multiplied: ${tuples2}")              //List(2, 12)

  //3. Pattern matching also works for case classes, the prepend operator (::), etc.

  //4. Multiple values can be assigned by exploiting the name binding property of pattern matching:
  val tuples3 = (1,2,3)
  val (a, b, c) = tuples3
  println(s"Name binding result: [$b]")                  //[2]

  val head :: tail = list1
  println(s"Prepend operator head: [$head]")             //[1]
  println(s"Prepend operator tail: $tail")               //List(2, 3, 4)

  //4. Partial function literals are also based on pattern matching:
  val mappedList1 = list1.map {
    case value if value % 2 == 0 => s"$value is even"
    case 1 => "The one"
    case _ => "Some other value"
  }
  println(s"Partial function literal #1: $mappedList1")  //List(The one, 2 is even, Some other value, 4 is even)

  //The partial function literal (above) is equivalent to:
  val mappedList2 = list1.map { x => x match {
      case value if value % 2 == 0 => s"$value is even"
      case 1 => "The one"
      case _ => "Some other value"
    }
  }
  println(s"Partial function literal #2: $mappedList2")  //List(The one, 2 is even, Some other value, 4 is even)
}
