package net.sattler22.lectures.part2oop

/**
 * Generics Takeaways:
 * <ol>
 *   <li>Allows the same code to be used on many (potentially unrelated) types</li>
 *   <li>Generic methods specify one or more type parameters in it's method definition</li>
 *   <li>Classes and traits can be type parameterized</li>
 *   <li>Companion objects cannot be type parameterized, but their methods can be</li>
 *   <li>Types can be defined with multiple type parameters (using single letters by convention)</li>
 *   <li>Variance problem: Does a list of a sub-type extend a list of it's parent?</li>
 *   <ol>
 *     <li>Yes, this is called <strong>covariance</strong> (use +)</li>
 *     <li>No, they are two separate things which is called <strong>invariance</strong> (no sign)</li>
 *     <li>No, they are inversely related which is called <strong>contravariance</strong> (use -)</li>
 *   </ol>
 *   <li>Bounded Types:</li>
 *   <ol>
 *     <li>Upper bound (<:) operator only accepts the SUB-TYPE(s)</li>
 *     <li>Lower bound (>:) operator only accepts the SUPER-TYPE</li>
 *   </ol>
 * </ol>
 */
object Generics extends App {

  //Generic class with type parameter A:
  class MyList[+A] {                             //Covariant list

    //def add(element: A): MyList[A] = ???       //COMPILER ERROR: "Covariant type A occurs in contravariant position in type A of value element"

    //Answers the HARD QUESTION...
    //IMPORTANT: Takes another type parameter B, which is a super type of A, which will return a list of B in order to turn the list into a list of type B
    //EXAMPLE: By adding a dog to a list of cats, it becomes a list of animals!!!
    def add[B >: A](element: B): MyList[B] = ???
  }

  //Multiple type parameters are allowed:
  class MyMap[Key, Value] {
  }

  //Companion objects cannot be type parameterized, but their methods can be:
  object MyList {
    def empty[A]: MyList[A] = ???                //Generic method
  }

  val listOfIntegers = new MyList[Int]
  val listOfString= new MyList[String]
  //val emptyListOfIntegers = MyList.empty[Int]  //Not implemented

  //Variance problem:
  //HARD QUESTION: Does a list of a sub-class extend a list of it's parent???
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  //ANSWER #1. Yes, a list of cats extends a list of animals [covariance (use +)]:
  class CovariantList[+A]
  val animal: Animal = new Cat                                                           //Polymorphic
  val covariantAnimalList: CovariantList[Animal] = new CovariantList[Cat]                //Cat works!!!

  //ANSWER #2. No, they are two separate things [invariance (no sign]:
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]             //Cat does not work!!!

  //ANSWER #3. No, they are inversely related [contravariance  (use -)]:
  //EXAMPLE: Here an animal trainer is specifically training a cat, but can also train other animals...
  class TrainerContravariantList[-A]
  val trainerList: TrainerContravariantList[Cat] = new TrainerContravariantList[Animal]  //Cat works!!!

  //Bounded types:
  //Upper bound operator (<:) => Cage only accepts a SUB-TYPE of animal
  //Lower bound operator (>:) => Cage only accepts a SUPER-TYPE of animal
  class Cage[A <: Animal](animal: A)           //Upper bound restriction
  class Car
  val cage = new Cage(new Dog)
  //val fails = new Cage(new Car)              //No way... Generic type needs proper bounded type
}
