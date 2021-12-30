package net.sattler22.lectures.part2oop

/**
 * Anonymous Classes Takeaways:
 * <ol>
 *   <li>We can instantiate types and override fields or methods on the spot</li>
 *   <li>Rules:</li>
 *   <ul>
 *     <li>Must pass in required constructor arguments (if needed)</li>
 *     <li>Must implement all abstract fields and/or methods</li>
 *   </ul>
 *   <li>They work for abstract and non-abstract data types</li>
 * </ol>
 */
object AnonymousClasses extends App {

  //Works with a trait too:
  abstract class Animal {
    def eat:Unit
  }

  //Anonymous class - compiler creates a class with a unique name
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("Funny Animal")
  }
  //Which is exactly the same as:
  class AnonymousClass$$anon$1 extends Animal {
    override def eat: Unit = println("Funny Animal")
  }
  //val funnyAnimal = new AnonymousClass$$anon$1
  println(funnyAnimal.getClass)

  //Must pass in the required super class parameters even if the class is anonymous:
  //Must provide implementation for all abstract fields and/or methods
  class Person(name: String) {
    def sayHi:Unit = println(s"Hi, my name is $name, how can I help?")
  }
  val jim = new Person("Jim") {
    override def sayHi: Unit = println("Hi, my name is Jim, how can I be of service?")
  }
}
