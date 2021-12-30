package net.sattler22.lectures.part2oop

/**
 * Generics Takeaways:
 * <ol>
 *   <li>Use the same code on many (potentially unrelated) types</li>
 *   <li>Generic methods - specify type parameter in method definition</li>
 *   <li>Classes and traits can be type parameterized, but (companion) objects cannot</li>
 *   <li>Multiple type parameters can be used</li>
 *   <li>Variance problem - Does a list of a sub-type extend a list of its parent?</li>
 *   <ol>
 *     <li>Yes - covariance (use +)</li>
 *     <li>No, they are two separate things - invariance (no sign)</li>
 *     <li>No, they are inversely related - contravariance  (use -)</li>
 *   </ol>
 *   <li>Bounded Types:</li>
 *   <ol>
 *     <li>Upper bound (<:) - Only accepts the SUB type</li>
 *     <li>Lower bound (>:) - Only accepts the SUPER type</li>
 *   </ol>
 * </ol>
 */
object Generics extends App {

  //Generic class with type parameter A
  class MyList[+A] {  //Covariant
    //Compiler error: Covariant type A occurs in contravariant position in type A of value element
    //HARD QUESTION: If a list of animals contains only cats, can I add a dog?
    //def add(element: A): MyList[A] = ???  <== FAILS!!!

    //Answers the HARD QUESTION
    //IMPORTANT: Takes another type parameter B, which is a super type of A, which will return a
    //           list of B in order to turn the list into a list of type B
    //For example: Add a dog to a list of cats, then it becomes a list of animals
    def add[B >: A](element: B): MyList[B] = ???
  }

  //Multiple type parameters are allowed
  class MyMap[Key, Value] {
  }

  //Generic methods
  //Companion objects cannot be type parameterized, but their methods can be
  object MyList {
    def empty[A]: MyList[A] = ???  //Not implemented
  }

  val listOfIntegers = new MyList[Int]
  val listOfString= new MyList[String]
  //Not implemented - val emptyListOfIntegers = MyList.empty[Int]

  //Variance problem:
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  //QUESTION: Does a list of cat (sub-class) extend a list of animal (parent)?

  //  -> ANSWER 1. Yes - covariance (use +)
  class CovariantList[+A]
  val animal: Animal = new Cat  //Polymorphic
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  //Can I add any animal to it like Dog?  *** HARD QUESTION ***

  //  -> ANSWER 2. No - invariance (no sign) - they are 2 separate things
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]  //Cat does not work!!!

  //  -> ANSWER 3. Hell, no - contravariance  (use -)
  //Example: Here an animal trainer is specifically training a cat
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  //Bounded types:
  //Upper bound (<:) Cage only accepts a SUB type of animal
  //Lower bound (>:) Cage only accepts a SUPER type of animal
  class Cage[A <: Animal](animal: A)  //Upper bound
  class Car
  val cage = new Cage(new Dog)
  //Generic type needs proper bounded type
  //val fails = new Cage(new Car)
}
