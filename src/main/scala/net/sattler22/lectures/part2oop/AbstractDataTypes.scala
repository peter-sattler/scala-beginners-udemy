package net.sattler22.lectures.part2oop

/**
 * Abstract Data Types Takeaways:
 * <ol>
 *   <li>An abstract class has unimplemented fields and/or methods which will be implemented by it's sub-class(es)</li>
 *   <li>Traits:</li>
 *   <ul>
 *     <li>The ultimate abstract data type in Scala</li>
 *     <li>In a single class, multiple traits can be mixed in, but only one class can be inherited</li>
 *     <li>Used to support multiple inheritance</li>
 *   </ul>
 *   <li>Traits vs. Abstract Classes:</li>
 *   <ol>
 *     <li>Traits do not have constructor parameters</li>
 *     <li>Multiple traits may be mixed in using the <i>with</i> keyword</li>
 *     <li>Traits are <strong>BEHAVIOR</strong>, but an abstract class is a <strong>TYPE OF THING</strong></li>
 *   </ol>
 *   <li>Scala's Type Hierarchy:</li>
 *   <pre>
 *                       +-------------------------------+
 *                       |          scala.Any            |
 *                       +---------------+---------------+
 *                                       |
 *                  .-----------------------------------------.
 *                  |                                         |
 *  +---------------+---------------+         +---------------+---------------+
 *  +         scala.AnyVal          +         |         scala.AnyRef          |
 *  +-------------------------------+         +-------------------------------+
 *  | (primitive types: Int, Unit,  |         | (java.lang.Object: String,    |
 *  |  Boolean, Float, ...)         |         |  Set, List, ...)              |
 *  +---------------+---------------+         +---------------+---------------+
 *                  |                                         |
 *  +-------------------------------+         +-------------------------------+
 *  + (classes that extend *AnyVal) +         |          scala.Null           |
 *  +---------------+---------------+         +---------------+---------------+
 *                  |                                         |
 *                  .--------------------+--------------------.
 *                                       |
 *                       +-------------------------------+
 *                       |        scala.Nothing          |
 *                       +-------------------------------+
 *   </pre>
 * </ol>
 */
object AbstractDataTypes extends App {

  //Abstract class:
  abstract class Animal {
    val creatureType: String = "Wild"
    def eat: Unit                  //Abstract method
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    def eat: Unit = println("Canine eats")
  }

  //Traits:
  trait Carnivore {
    val preferredMeal: String = "Fresh Meat"
    def eat(animal: Animal): Unit  //Abstract method
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
  crocodile.eat(dog)               //I'm a crocodile and I am eating a Canine
}
