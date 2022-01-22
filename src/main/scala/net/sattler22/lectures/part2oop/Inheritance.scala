package net.sattler22.lectures.part2oop

/**
 * Inheritance Takeaways:
 * <ol>
 *   <li>Scala supports single class inheritance</li>
 *   <li>Modifiers are <i>private</i>, <i>protected</i> or none (<i>public</i>)</li>
 *   <li>Constructors:</li>
 *   <ul>
 *     <li>The JVM requires the parent constructor to be called before it's sub-class constructor</li>
 *   </ul>
 *   <li>Overriding:</li>
 *   <ul>
 *     <li>Fields (<i>val</i> or <i>var</i>), but not methods, can also be overwritten directly in the constructor as a class parameter</li>
 *     <li>Use <i>super</i> when you want to reference a parent method from a sub-class</li>
 *     <li>Automatic type substitution of derived classes (polymorphism)</li>
 *   </ul>
 *   <li>Overriding vs. Overloading:</li>
 *   <ul>
 *     <li>Overriding is using a different implementation in derived classes</li>
 *     <li>Overloading is using multiple methods with different signatures with the same name in the same class</li>
 *   </ul>
 *   <li>Preventing overrides:</li>
 *   <ol>
 *     <li>Use <i>final</i> on member to prevent it from being overridden</li>
 *     <li>Use <i>final</i> on the entire class so the class cannot be extended</li>
 *     <li>Seal (<i>sealed</i>) the class (a softer restriction than #2), so it can be extended in THIS FILE, but not in other files</li>
 *   </ol>
 * </ol>
 */
object Inheritance extends App {

  class Animal {
    val creatureType = "Wild"
    def eat = println("Animal eats...")
  }

  class Cat extends Animal {
    def crunch = {
      eat
      println("Cat eats...")
    }
  }
  val cat = new Cat
  cat.crunch
  println(cat.creatureType)

  //Constructors:
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)  //Auxiliary constructor (used below)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name)

  //JVM requires the parent constructor to be called before sub-class constructor:
  def adult = new Adult("Pete", 56, "ID-123")

  //Overriding:
  //Fields (val or var), but not methods, can also be overwritten directly in the constructor as a class parameter
  class Dog(override val creatureType: String) extends Animal {
    override def eat = {
      super.eat
      println("Dog eats")
    }
  }
  //Alternate way (exactly the same thing):
  class Dog2(dogType: String) extends Animal {
    override val creatureType = dogType
  }
  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  //Type substitution (polymorphism):
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat                       //Uses dog instance since its the most overridden (derived) one!!!
}
