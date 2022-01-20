package net.sattler22.lectures.part2oop

import scala.language.postfixOps

/**
 * Method Notations Takeaways:
 * <ol>
 *   <li>Gets Scala closer to natural language</li>
 *   <li>All operators are methods</li>
 *   <li>Syntactic Sugar:</li>
 *   <ul>
 *     <li>Infix (or operator) notation (single parameter only) uses an object method parameter in a more english-like fashion</li>
 *     <li>Prefix notation (+ - ! ~) is used with unary operators (unary_ prefix)</li>
 *     <li>Postfix notation (parameter-less only) can be used to avoid the dot operator</li>
 *   </ul>
 *   <li>The apply method (single parameter only) allows objects to be called as functions</li>
 * </ol>
 */
object MethodNotations extends App {

  //Infix (or operator) notation (syntactic sugar):
  //NOTE: For single parameter methods only!!!
  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception")

  //"Operators" are methods in Scala:
  //Method names are very flexible (+ is allowed, etc.)
  val tom = new Person("Tom", "Fight Club")
  println(mary + tom)        //Mary is hanging out with Tom
  println(mary.+(tom))       //Mary dot plus (a method) with parameter tom
  println(1 + 2)             //All operators are methods and work the same way!!!
  println(1.+(2))            //One dot plus (an operator) with parameter two

  //Prefix notation (syntactic sugar):
  //NOTE: Only works with the - + ~ and ! operators
  val x = -1
  val y = 1.unary_-          //Unary operators are methods with a "unary_" prefix
  println(mary.unary_!)
  println(!mary)             //Cool ;)

  //Postfix notation (syntactic sugar):
  //NOTE: Parameter-less methods only!!!
  //NOTE: Needs import above!!!
  println(mary.isAlive)
  println(mary isAlive)      //Rarely used in practice (introduces ambiguities reading the code)

  //Apply method name:
  //!!!!! SUPER IMPORTANT !!!!!
  println(mary.apply())      //"Hi, my name is Mary and I like Inception"
  println(mary())            //Equivalent!!!

  /** Exercises */
  //For the Person class:
  // 1. Overload the + operator (infix operator):
  //   -> mary + "the rock star" returns a new person the name "Mary (the rock star)"
  // 2. Add an age:
  //   -> Add a unary + operator to increment the age and return a new, older person
  // 3. Add a "learns" method to return "Mary learns Scala":
  //   -> Then add a learnsScala method which calls leans method with "Scala"
  //   -> Use it in postfix notation
  // 4. Overload the apply method:
  //   -> mary.apply(2) returns "Mary watched Inception 2 times"
  val maryRockStar = mary + "the rock star"
  println(maryRockStar())    //"Hi, my name is Mary (the rock star) and I like Inception" (calls apply method)
  println((+mary).age)       //Unary operator... An older Mary with an age of one
  println(mary learns "Scala")
  println(mary learnsScala)  //Postfix notation
  println(mary(2))           //Calls apply method

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String) = movie == favoriteMovie
    def +(person: Person) = s"${this.name} is hanging out with ${person.name}"
    def +(nickName: String) = new Person(s"$name ($nickName)", favoriteMovie)
    //NOTE: Space after method name is needed here!!!
    def unary_! = s"$name, what the heck?!?!"
    def unary_+ = new Person(name, favoriteMovie, age + 1)
    def isAlive = true
    def learns(topic: String) = s"$name is learning $topic"
    def learnsScala = this learns "Scala"
    //NOTE: Parenthesis are needed here!!!
    def apply() = s"Hi, my name is $name and I like $favoriteMovie"
    def apply(n: Int) = s"$name watched $favoriteMovie $n times"
  }
}
