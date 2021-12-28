package net.sattler22.lectures.part1basics

/**
 * Abstract Data Types Takeaways:
 * <ol>
 *   <li>Abstract class - Has unimplemented fields and/or methods which will be implemented in sub-class(es)</li>
 *   <li>Traits:</li>
 *   <ul>
 *     <li>The ultimate abstract data type in Scala</li>
 *     <li>Multiple can be inherited along with classes!!!</li>
 *     <li>Used to support multiple inheritance</li>
 *   </ul>
 *   <li>Traits vs. Abstract Classes:</li>
 *   <ol>
 *     <li>Traits do not have constructor parameters</li>
 *     <li>Multiple traits may be inherited by the same class</li>
 *     <li>Traits are BEHAVIOR, but an abstract class is a TYPE OF THING</li>
 *   </ol>
 *   <li>Scala's Type Hierarchy:</li>
 *   <img src="/images/scala-type-hierarchy.jpg" alt="Scala Type Hierarchy" width="500" height="600">
 * </ol>
 */
object AbstractDataTypes extends App {

  //Abstract class
  abstract class Animal {
    val creatureType: String = "wild"
    def eat: Unit  //abstract method
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    def eat: Unit = println("Canine eats")
  }

  //Traits:
  trait Carnivore {
    val preferredMeal: String = "Fresh Meat"
    def eat(animal: Animal): Unit  //abstract method
  }

  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "Crocodile"
    def eat: Unit = "Crocodile eats"
    def eat(animal: Animal): Unit =
      println(s"I'm a crocodile and I am eating a ${animal.creatureType}")
  }

  val dog = new Dog
  val crocodile = new Crocodile
  crocodile.eat(dog)  //I'm a crocodile and I am eating a Canine
}
