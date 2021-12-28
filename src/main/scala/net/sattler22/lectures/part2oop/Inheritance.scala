package net.sattler22.lectures.part1basics

/**
 * Inheritance Takeaways:
 * <ol>
 *   <li>Single class inheritance</li>
 *   <li>Modifiers - private, protected or none (public)</li>
 *   <li>Constructors:</li>
 *   <ul>
 *     <li>The JVM requires parent constructor to be called before subclass constructor</li>
 *   </ul>
 *   <li>Overriding:</li>
 *   <ul>
 *     <li>Fields (val or var), but not methods, can also be overwritten directly in the constructor as a class parameter</li>
 *     <li>super - used when you want to reference a method from a parent class</li>
 *   </ul>
 *   <li>Overriding vs. Overloading:</li>
 *   <ul>
 *     <li>Overriding - supplying a different implementation in derived classes</li>
 *     <li>Overloading - supplying multiple methods with different signatures with the same name in the same class</li>
 *   </ul>
 *   <li>Type substitution (polymorphism)</li>
 *   <li>Preventing overrides:</li>
 *   <ol>
 *     <li>Use final on member</li>
 *     <li>Use final on the entire class - class cannot be extended</li>
 *     <li>Seal the class (softer restriction than #2) - can extend in THIS FILE, but PREVENTS extension in other files</li>
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
    //Auxiliary CTOR:
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name)

  //JVM requires parent CTOR to be called before subclass CTOR
  def adult = new Adult("Pete", 56, "ID-123")

  //Overriding:
  //Fields (val or var), but not methods, can also be overwritten directly in the CTOR as a class parameter
  class Dog(override val creatureType: String) extends Animal {
    override def eat = {
      super.eat
      println("Dog eats")
    }
  }
  //Alternate way (exactly the same):
  class Dog2(dogType: String) extends Animal {
    override val creatureType = dogType
  }
  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  //Type substitution (polymorphism):
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat  //Uses dog since its the most overridden one!!!
}
