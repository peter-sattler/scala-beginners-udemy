package net.sattler22.lectures.part4pm

import scala.util.Random

/**
 * Pattern Matching Takeaways:
 * <ol>
 *   <li>Pattern matching can be used as a simple switch-like statement (but on steroids)</li>
 *   <li>Cases are matched in order</li>
 *   <li>A <i>scala.MatchError</i> is thrown if no matching case can be found</li>
 *   <li>Guards can be used to specify additional case restrictions</li>
 *   <li>The return type is the lowest common ancestor of all the types in all of the cases (unified type)</li>
 *   <li>It works well with case classes because they come with extractor patterns out of the box</li>
 *   <li>The warning "<i>match may not be exhaustive</i>" will be issued for sealed classes with at least one missing case</li>
 *   <li>Be careful not to overuse pattern matching when there are simpler solutions!!!</li>
 * </ol>
 */
object PatternMatching extends App {

  //Switch on steroids:
  val random = new Random
  val value = random.nextInt(10)
  val description = value match {
    case 1 => "The one"
    case 2 => "Double or nothing"
    case 3 => "The third time's the charm"
    case _ => "Underscore is a wildcard pattern that matches anything"
  }
  println(s"Random value [$value] is described as [$description]")

  //Decomposition of values using case classes:
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)
  val greeting = bob match {
    case Person(name, age) if age < 21 => s"Hi, my name is $name and I am $age years old and I can't drink in the US"
    case Person(name, age) => s"Hi, my name is $name and I am $age years old"
    case _ => "I don't know who I am. Call my therapist!!!"
  }
  println(s"Greeting: [$greeting]")         //[Hi, my name is Bob and I am 20 years old and I can't drink in the US]

  //Sealed hierarchies:
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal
  val animal: Animal = Dog("Terra Nova")
  val animalMatch = animal match {
    case Dog(someBreed) => s"Matched a dog of the $someBreed breed"
    //NOTE: Missing [Parrot] case!!!
  }
  println(s"Animal match: [$animalMatch]")  //[Matched a dog of the Terra Nova breed]

  /** Exercise */
  trait Expression
  case class Number(n: Int) extends Expression
  case class Sum(e1: Expression, e2: Expression) extends Expression
  case class Product(e1: Expression, e2: Expression) extends Expression

  //Write a simple function that receives an Expression and returns a human readable form
  def show(e: Expression): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => s"${show(e1)} + ${show(e2)}"
    case Product(e1, e2) =>
      def maybeShowParens(exp: Expression) = exp match {
        case Product(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => s"(${show(exp)})"
      }
      s"${maybeShowParens(e1)} * ${maybeShowParens(e2)}"
  }
  println(s"Expression #1 should be [2 + 3]: [${show(Sum(Number(2), Number(3)))}]")
  println(s"Expression #2 should be [2 + 3 + 4]: [${show(Sum(Sum(Number(2), Number(3)), Number(4)))}]")
  println(s"Expression #3 should be [(2 + 1 * 3]: [${show(Product(Sum(Number(2), Number(1)), Number(3)))}]")
  println(s"Expression #4 should be [(2 + 1) * (3 + 4)]: [${show(Product(Sum(Number(2), Number(1)), Sum(Number(3), Number(4))))}]")
  println(s"Expression #5 should be [2 * 1 + 3]: [${show(Sum(Product(Number(2), Number(1)), Number(3)))}]")
}
