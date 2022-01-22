package net.sattler22.lectures.part2oop

/**
 * Scala Objects Takeaways:
 * <ol>
 *   <li>Scala does NOT have class-level (static) functionality (values/methods)</li>
 *   <li>Scala uses <i>object</i> instead:</li>
 *   <ul>
 *     <li>They are in their own class</li>
 *     <li>It is the only instance</li>
 *     <li>The singleton pattern in one line!!!</li>
 *     <li>They can extend a class</li>
 *     <li>They can NOT receive parameters</li>
 *   </ul>
 *   <li>Scala Companions:</li>
 *   <ul>
 *     <li>A design pattern where both the class-level and instance-level functionality use the same name and have the same scope<</li>
 *     <li>The keyword <i>object</i> is used for class-level functionality</li>
 *     <li>The keyword <i>class</i> is used for instance-level functionality</li>
 *     <li>They can access each other's private members</li>
 *     <li>Scala is more object-oriented than Java!!!</li>
 *   </ul>
 *   <li>Scala Application:</li>
 *   <ul>
 *     <li>It is a Scala object</li>
 *     <li>It MUST have method: <i>def main(args: Array[String]): Unit</i></li>
 *     <li>OR it can <i>extend App</i></li>
 *   </ul>
 * </ol>
 */
object Objects {

  object Person {
    //Class-level functionality:
    val N_EYES = 2;
    def canFly: Boolean = true
    //Factory method:
    def apply(mother: Person, father: Person): Person = new Person("Bobby")
  }

  class Person(val name: String) {
    //Instance-level functionality...
  }

  //INSTEAD OF: extends App
  def main(args: Array[String]):Unit = {
    println (Person.N_EYES)
    println (Person.canFly)

    val person1 = Person
    val person2 = Person
    println (person1 == person2)    //Point to same singleton instance

    val mary = new Person ("Mary")
    val john = new Person ("John")
    val bobby = Person(mary, john)  //Calls apply method
  }
}
