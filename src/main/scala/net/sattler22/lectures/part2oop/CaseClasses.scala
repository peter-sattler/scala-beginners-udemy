package net.sattler22.lectures.part2oop

/**
 * Case Classes Takeaways:
 * <ol>
 *   <li>Allows for quick implementation of lightweight data structures without all of the boilerplate code:</li>
 *   <ol>
 *     <li>All class parameters are promoted to fields</li>
 *     <li>Both equals and hashCode are implemented out of the box</li>
 *     <li>They have a sensible toString implementation</li>
 *     <li>They have a handy copy method</li>
 *     <li>They have companion objects with a handy factory method (<i>apply()</i>)</li>
 *     <li>They are automatically serializable</li>
 *     <li>They have extractor patterns that can be used in pattern matching</li>
 *   </ol>
 *   <li>Case classes usually don't use <i>new</i> as a matter of convention</li>
 *   <li>Case objects work the same way, except they don't have companion objects</li>
 * </ol>
 */
object CaseClasses extends App {

  case class Person(name: String, age: Int)

  //Class parameters are promoted to fields:
  val jim = new Person("Jim", 34)
  println(jim.name)

  //Both equals and hashCode are implemented out of the box:
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)           //true (for case class)

  //Sensible toString:
  println(jim)                   //Person(Jim,34)

  //Handy copy method:
  val jim3 = jim.copy()
  val jim4 = jim.copy(age = 45)  //Named parameters too
  println(jim3)                  //Person(Jim,34)
  println(jim4)                  //Person(Jim,45)

  //Companion objects with a handy factory method:
  //NOTE: Don't usually use new syntax for case classes!!!
  val person1 = Person           //Companion object
  val mary = Person("Mary", 23)  //Calls apply() method
  println(mary)

  //Case objects work the same way, but don't get companion objects:
  case object UnitedKingdom {
    def name: String = "The United Kingdom of Great Britain and Northern Ireland"
  }
}
