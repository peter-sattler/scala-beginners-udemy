package net.sattler22.lectures.part2oop

/**
 * Case Classes Takeaways:
 * <ol>
 *   <li>Quick, lightweight data structure with little boilerplate (equals, hashCode, toString, etc.):</li>
 *   <ol>
 *     <li>Class parameters are promoted to fields</li>
 *     <li>Have a sensible toString implementation</li>
 *     <li>Both equals and hashCode are implemented out of the box</li>
 *     <li>Have a handy copy method</li>
 *     <li>Have companion objects with a handy factory method (apply())</li>
 *     <li>Are automatically serializable</li>
 *     <li>Have extractor patterns that can be used in pattern matching</li>
 *   </ol>
 *   <li>Case classes usually don't use new as a matter of convention</li>
 *   <li>Case objects are just like case classes, except they don't have companion objects</li>
 * </ol>
 */
object CaseClasses extends App {

  //Case classes (CCs) remove the need for boilerplate code like equals, hashCode, toString:
  case class Person(name: String, age: Int)

  //1. CCs automatically promote class parameters to fields:
  val jim = new Person("Jim", 34)
  println(jim.name)

  //2. CCs have a sensible toString:
  println(jim)                   //Person(Jim,34)

  //3. CCs implement equals and hashCode out of the box:
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)           //true (for case class)

  //4. CCs have a handy copy method:
  val jim3 = jim.copy()
  val jim4 = jim.copy(age = 45)
  println(jim3)                  //Person(Jim,34)
  println(jim4)                  //Person(Jim,45)

  //5. CCs have companion objects with handy a factory method:
  //NOTE: Don't usually use new syntax for case classes!!!
  val person1 = Person           //Companion object
  val mary = Person("Mary", 23)  //Calls apply() method
  println(mary)

  //6. CCs are serializable:
  //NOTE: Particularly important for Akka!!!

  //7. CCs have extractor patterns - can be used in PATTERN MATCHING

  //8. Case objects behave just like CCs, except they don't get companion objects
  case object UnitedKingdom {
    def name: String = "United Kingdom"
  }
}
