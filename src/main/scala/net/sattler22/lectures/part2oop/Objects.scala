package net.sattler22.lectures.part1basics

/**
 * Scala Objects Takeaways:
 * <ol>
 *   <li>Scala doesn't have "static" values/methods</li>
 *   <li>Scala uses object instead:</li>
 *   <ul>
 *     <li>Are in their own class/li>
 *     <li>Are the only instance</li>
 *     <li>Singleton pattern in one line!</li>
 *   </ul>
 *   <li>Scala Companions:/li>
 *   <ul>
 *     <li>Write both an object and class using the same name and having the same scope<</li>
 *     <li>object - class-level functionality</li>
 *     <li>class - instance-level functionality</li>
 *     <li>Can access each other's private members</li>
 *     <li>Scala is more OO than Java!</li>
 *   </ul>
 *   <li>Scala Application:</li>
 *   <ul>
 *     <li>It is a Scala object</li>
 *     <li>Must have method def main(args: Array[String]): Unit</li>
 *     <li>OR it can extend App</li>
 *   </ul>
 * </ol>
 */
object Objects {

  object Person {
    val N_EYES = 2;
    def canFly: Boolean = true
    //Factory method:
    def apply(mother: Person, father: Person): Person = new Person("Bobby")
  }

  class Person(val name: String) {
    //instance level functionality
  }

  //INSTEAD OF: extends App
  def main(args: Array[String]):Unit = {
    println (Person.N_EYES)
    println (Person.canFly)

    val person1 = Person
    val person2 = Person
    println (person1 == person2) //Point to same singleton instance

    val mary = new Person ("Mary")
    val john = new Person ("John")
    val bobby = Person (mary, john) //Calls apply method
  }
}
