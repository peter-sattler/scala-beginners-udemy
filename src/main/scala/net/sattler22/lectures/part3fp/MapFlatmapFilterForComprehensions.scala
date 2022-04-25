package net.sattler22.lectures.part3fp

/**
 * Map, Flat Map, Filter and For-Comprehensions Takeaways:
 * <ol>
 *   <li>This lecture uses the official Scala standard library (see https://www.scala-lang.org/api/current)</li>
 *   <li>Functional programming uses <i>flatMap</i> and <i>map</i> for "iteration":</li>
 *   <ul>
 *     <li>For-comprehensions make the it more readable</li>
 *     <li>For-comprehensions yield an expression</li>
 *   </ul>
 *   <li>Functional programming uses <i>Maybe</i> to denote optional values:</li>
 *   <ul>
 *     <li>Optional values denote the possible absence of a value which is an important concept</li>
 *   </ul>
 * </ol>
 */
object MapFlatmapFilterForComprehensions extends App {

  val list = List(1,2,3)
  println(s"Scala standard library LIST implementation: [$list]")
  println(s"List head: [${list.head}]")                                      //1
  println(s"List tail: [${list.tail}]")                                      //List(2, 3)

  println(s"List map add one: ${list.map(_ + 1)}")                           //List(2, 3, 4)
  println(s"List map concatenate: ${list.map(_ + " is a number")}")          //List(1 is a number, 2 is a number, 3 is a number)

  println(s"List filter even only: ${list.filter(_ % 2 == 0)}")              //List(2)

  val toPair = (x: Int) => List(x, x + 1)
  println(s"List flatMap toPair: ${list.flatMap(toPair)}")                   //List(1, 2, 2, 3, 3, 4)

  //Functional programming "iteration":
  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val twoCombiner = numbers.flatMap(num => chars.map(char => "" + char + num))
  println(s"Two list combinations: $twoCombiner")                            //List(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4)
  val colors = List("black","white")
  val threeCombiner = numbers.flatMap(num => chars.flatMap(char => colors.map(color => "" + char + num + "-" + color)))
  println(s"Three list combinations: $threeCombiner")                        //List(a1-black, a1-white, b1-black ... d4-white)

  list.foreach(println)                                                      //1 (new line) 2 (new line) 3 (new line)

  //For-comprehensions:
  val forComprehension = for {
    num <- numbers
    char <- chars
    color <- colors
  } yield "" + char + num + "-" + color
  println(s"Three list for-comprehension: $forComprehension")                //List(a1-black, a1-white, b1-black ... d4-white)

  //Compiler translates the guard into a filter function:
  //Example: numbers.filter(num %2 == 0).flatMap(num => chars.flatMap(char => colors.map(color => "" + char + num + "-" + color)))
  val forComprehensionWithGuard = for {
      num <- numbers if num %2 == 0
      char <- chars
      color <- colors
  } yield "" + char + num + "-" + color
  println(s"Three list even for-comprehension: $forComprehensionWithGuard")  //a2-black, a2-white, b2-black ... d4-white)

  //Identical to numbers.foreach(println):
  for {
    num <- numbers
  } println(num)                                                             //1 (new line) 2 (new line) 3 (new line) 4 (new line)

  //Be aware of syntax overload (these are identical):
  val normalSyntax = list.map(x => x * 2)
  val overloadedSyntax = list.map { x =>
    x * 2
  }

  /** Exercises */
  //Check if MyList supports for-comprehensions:
  //NOTE: Since the compiler rewrites them into chains of map, flatMap and filter, they must have these signatures:
  //  -> map(transformer: A => B) and returns MyList[B]
  //  -> flatMap(transformer: A => MyList[B]) and returns MyList[B]
  //  -> filter(predicate: A => Boolean) and returns MyList[A]

  //Implement a small collection of at MOST one element called Maybe[+T]:
  //NOTE: Maybe is used in functional programming to denote optional values!!!
  //  -> map, flatMap and filter
  abstract class Maybe[+T] {
    def map[B](transformer: T => B): Maybe[B]
    def flatMap[B](transformer: T => Maybe[B]): Maybe[B]
    def filter(predicate: T => Boolean): Maybe[T]
  }

  //Empty collection:
  case object MaybeNot extends Maybe[Nothing] {
    def map[B](transformer: Nothing => B): Maybe[B] = MaybeNot
    def flatMap[B](transformer: Nothing => Maybe[B]): Maybe[B] = MaybeNot
    def filter(predicate: Nothing => Boolean): Maybe[Nothing] = MaybeNot
  }

  //One element collection:
  case class Just[+T](value: T) extends Maybe[T] {
    def map[B](transformer: T => B): Maybe[B] = Just(transformer(value))
    def flatMap[B](transformer: T => Maybe[B]): Maybe[B] = transformer(value)
    def filter(predicate: T => Boolean): Maybe[T] = {
      if (predicate(value)) this
      else MaybeNot
    }
  }

  val just3 = Just(3)
  println(s"Just 3: [$just3]")                                               //Just(3)
  println(s"Just 3 map: [${just3.map(_ * 2)}]")                              //Just(6)
  println(s"Just 3 flatMap: [${just3.flatMap(x => Just(x % 2 == 0))}]")      //Just(false)
  println(s"Just 3 filter: [${just3.filter(_ %2 == 0)}]")                    //MaybeNot
}
