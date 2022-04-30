package net.sattler22.lectures.part3fp

import scala.util.Random

/**
 * Pattern Matching Takeaways:
 * <ol>
 *   <li>xxx</li>
 *   <li>xxx</li>
 *   <li>xxx</li>
 *   <li>xxx</li>
 *   <li>xxx</li>
 *   <li>xxx</li>
 *   <li>xxx</li>
 *   <li>xxx</li>
 *   <li>xxx</li>
 *   <li>xxx</li>
 * </ol>
 */
object PatternMatching extends App {

  //Switch on steroids:
  val random = new Random
  val value = random.nextInt(10)
  val description = value match {
    case 1 => "The One"
    case 2 => "Double or nothing"
    case 3 => "The third time's the charm"
    case _ => "Underscore is a wildcard pattern that matches everything"
  }
  println(s"Random value: [$value], description: [$description]")

  //Decomposition of values using case classes:
  //  -> Guards can be used to specify additional restrictions
  //  -> Cases are matched in order
  //  -> A scala.MatchError is thrown if no cases match
  //  -> The return type is the lowest common ancestor of all the types in all the cases (unified type)
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)
  val greeting = bob match {
    case Person(name, age) if age < 21 => s"Hi, my name is $name and I can't drink in the US"  //[Hi, my name is Bob and I can't drink in the US]
    case Person(name, age) => s"Hi, my name is $name and I am $age years old"
    case _ => "I don't know who I am. Call my therapist!!!"
  }
  println(s"Greeting: [$greeting]")

  //Sealed hierarchies:
  //  -> Compiler will issue the warning [match may not be exhaustive] for sealed classes with missing possibilities
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal
  val animal: Animal = Dog("Terra Nova")
  val animalMatch = animal match {
    case Dog(someBreed) => s"Matched a dog of the $someBreed breed"
    //NOTE: Missing [Parrot] case!!!
  }
  println(s"Animal match: [$animalMatch]")                                                     //[Matched a dog of the Terra Nova breed]

  /** Exercises */
  //xxxxx
}
