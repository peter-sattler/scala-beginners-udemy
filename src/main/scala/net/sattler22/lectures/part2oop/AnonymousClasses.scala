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

  abstract class Animal {
    def eat: Unit
  }

  //Anonymous class (works with traits too):
  //The compiler automatically creates a class with a unique name!!!
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("Funny Animal")
  }
  //Which is exactly the same as:
  class AnonymousClass$$anon$1 extends Animal {
    override def eat: Unit = println("Funny Animal")
  }
  //val funnyAnimal = new AnonymousClass$$anon$1
  println(funnyAnimal.getClass)  //AnonymousClasses$$anon$1

  //Still required to pass in the required super class parameters and
  //provide an implementation for all abstract fields and/or methods even if it's anonymous:
  class Person(name: String) {
    def sayHi:Unit = println(s"Hi, my name is $name, how can I help?")
  }
  val jim = new Person("Jim") {
    override def sayHi: Unit = println("Hi, my name is Jim, how can I be of service?")
  }
}
