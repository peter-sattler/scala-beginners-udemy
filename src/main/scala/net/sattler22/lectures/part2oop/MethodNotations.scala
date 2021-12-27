package net.sattler22.lectures.part2oop

import scala.language.postfixOps

/**
 * Method Notations Takeaways:
 * <ol>
 *   <li>Gets Scala closer to natural language</li>
 *   <li>All operators are methods</li>
 *   <li>Syntactic Sugar!!!</li>
 *   <li>Infix notation (single parameter only) - use object method parameter in a more english like fashion</li>
 *   <li>Prefix notation (+ - ! ~) - use with unary operators</li>
 *   <li>Postfix notation (parameter-less only) - Do not need dot</li>
 *   <li>The apply method (single parameter only)- allows objects to be called as functions</li>
 * </ol>
 */
object MethodNotations extends App {
  /** Lesson */
  //SYNTACTIC SUGAR - Infix (or operator) notation:
  //NOTE: For single parameter methods only!!!
  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception")

  //"Operators" in Scala
  //Method names are very flexible (+ is allowed, etc.)
  val tom = new Person("Tom", "Fight Club")
  println(mary + tom)   //Mary is hanging out with Tom
  println(mary.+(tom))  //Same thing!!!
  println(1 + 2)
  println(1.+(2))       //Mathematical operations are methods too!!!

  //SYNTACTIC SUGAR - Prefix notation:
  //NOTE: Works only with - + ~ and !
  val x = - 1
  val y = 1.unary_-  //Same as above
  println(mary.unary_!)
  println(!mary)     //Cool ;)

  //SYNTACTIC SUGAR - Postfix notation:
  //NOTE: Parameter-less methods only!!!
  //NOTE: Needs import above!!!
  println(mary.isAlive)
  println(mary isAlive)

  //SYNTACTIC SUGAR - Apply method name:
  // !!!!! SUPER IMPORTANT !!!!!
  println(mary.apply())  //Can call mary as a function
  println(mary())        //Works too!!!

  /** Exercises */

  /*
   * 1. Overload the + operator (infix operator)
   *   -> mary + "the rock star" => new Person "Mary (the rock star)"
   * 2. Add an age to Person class
   *   -> Add a unary + operator to increment the age and return a new person
   * 3. Add a "learns" method in the Person class => Mary learns Scala
   *   -> Add a learnsScala method, calls leans method with "Scala"
   *   -> Use it in postfix notation
   * 4. Overload the apply method
   *   -> mary.apply(2) => Mary watched Inception 2 times
   */
  val maryRockStar = mary + "the rock star"
  println(maryRockStar())    //Hi, my name is Mary (the rock star) and I like Inception (calls apply)
  println((+mary).age)       //Unary operator - An older Mary
  println(mary learns "Scala")
  println(mary learnsScala)  //Postfix notation
  println(mary(2))           //Calls apply method

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String) = movie == favoriteMovie
    def +(person: Person) = s"${this.name} is hanging out with ${person.name}"
    def +(nickName: String) = new Person(s"$name ($nickName)", favoriteMovie)
    //NOTE: Space after method name is needed here!!!
    def unary_! = s"$name, what the heck?!"
    def unary_+ = new Person(name, favoriteMovie, age + 1)
    def isAlive = true
    def learns(topic: String) = s"$name is learning $topic"
    def learnsScala = this learns "Scala"
    //NOTE: Parenthesis are needed here!!!
    def apply() = s"Hi, my name is $name and I like $favoriteMovie"
    def apply(n: Int) = s"$name watched $favoriteMovie $n times"
  }
}
