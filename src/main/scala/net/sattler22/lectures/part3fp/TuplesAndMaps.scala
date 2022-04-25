package net.sattler22.lectures.part3fp

import java.util.NoSuchElementException

/**
 * Tuples and Maps Takeaways:
 * <ol>
 *   <li>Tuples:</li>
 *   <ul>
 *     <li>Are finite ordered lists</li>
 *     <li>Can group up to 22 elements of different types because they are used in conjunction with function types</li>
 *     <li>Use <i>_n</i> to retrieve elements</li>
 *     <li>Use <i>copy</i> to create new tuples</li>
 *     <li>Swap elements via <i>swap</i></li>
 *   </ul>
 *   <li>Maps:</li>
 *   <ul>
 *     <li>Associates keys to values</li>
 *     <li>Functionals include <i>filterKeys</i> and <i>mapValues</i></li>
 *     <li>The <i>map</i>, <i>flatMap</i> and <i>filter</i> functions operate on pairings (or tuples)</li>
 *     <li>Can be converted to and from lists</li>
 *     <li>The <i>groupBy</i> function is very useful</li>
 *   </ul>
 * </ol>
 */
object TuplesAndMaps extends App {

  //Tuples:
  val tuple = Tuple2(2, "Hello Scala")
  println(s"Tuple: $tuple")                                                 //Tuple: (2,Hello Scala)
  println(s"Tuple sugar: ${(2, "Hello Scala")}")                            //Syntactic sugar: Tuple2(2, "Hello Scala")
  println(s"Tuple first element: [${tuple._1}]")                            //[2]
  println(s"Tuple copy: ${tuple.copy(_2 = "Replace")}")                     //(2,Replace)
  println(s"Tuple swap: ${tuple.swap}")                                     //(Hello Scala,2)

  //Maps:
  val map: Map[String,Int] = Map()
  val phoneBook = Map(("Fred", 5555), "Pete" -> 6789).withDefaultValue(-1)  //Syntactic sugar: ("Pete", 6789)
  println(s"Phone book #1: $phoneBook")                                     //Map(Fred -> 5555, Pete -> 6789)
  println(s"Phone book contains: [${phoneBook.contains("Pete")}]")          //[true]
  println(s"Phone book get: [${phoneBook("Pete")}]")                        //[6789]
  println(s"Phone book get not found: [${phoneBook("Mary")}]")              //[-1]
  val phoneBook2 = phoneBook + ("Mary" -> 4567)                             //Maps are immutable
  println(s"Phone book #2: $phoneBook2")                                    //Map(Fred -> 5555, Pete -> 6789, Mary -> 4567)

  //Map functionals (map, flatMap and filter) receive a pairing (or tuple) parameter:
  println(s"Phone book map: ${phoneBook.map(pair => pair._1.toLowerCase() -> pair._2)}")          //Map(fred -> 5555, pete -> 6789)
  println(s"Phone book filter keys: ${phoneBook.view.filterKeys(x => x.startsWith("F")).toMap}")  //Map(Fred -> 5555)
  println(s"Phone book map values: ${phoneBook.view.mapValues(num => "245-" + num).toMap}")       //Map(Fred -> 245-5555, Pete -> 245-6789)
  println(s"Phone book list conversion: ${phoneBook.toList}")                                     //List((Fred,5555), (Pete,6789))
  println(s"List to map conversion: ${List(("Pete", 6789)).toMap}")                               //Map(Pete -> 6789)
  val names = List("Bob","Bill","Pat","Pete","Lou")
  println(s"Names group by: ${names.groupBy(name => name.charAt(0))}")                            //HashMap(L -> List(Lou), B -> List(Bob, Bill), P -> List(Pat, Pete))

  /** Exercises */
  //xxxxx
}
