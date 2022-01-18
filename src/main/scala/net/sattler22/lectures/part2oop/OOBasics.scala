package net.sattler22.lectures.part2oop

/**
 * Object Oriented Basics Takeaways:
 * <ol>
 *   <li>OO is different in Scala</li>
 *   <li>Class is a blueprint that organizes data and behavior (code)</li>
 *   <li>Instance is a concrete realization (in memory) that conforms to the code/data structure that the class describes</li>
 *   <li>Class parameters vs. Class fields:</li>
 *   <ul>
 *     <li>Class parameters are not class fields (or members)</li>
 *     <li>Class fields (or members) can be accessed with the dot operator</li>
 *     <li>Must use val or var for a class FIELD</li>
 *   </ul>
 *   <li>Overloading is the same method name with different signatures</li>
 *   <li>Method signatures differ in number and/or types of parameters, but the return type doesn't matter.</li>
 *   <li>Constructors can be overloaded via def this (auxiliary constructors), but it is easier to use default constructor values</li>
 *   <li>Immutability is SUPER IMPORTANT in functional programming (instance values are fixed inside)</li>
 * </ol>
 */
object OOBasics extends App {

  val p1 = new Person("Pete", 56)
  println(p1.age)
  println(p1.x)
  p1.greet("Fred")
  p1.greet()

  class Person(name: String, val age: Int) {
    val x = 2                                                                 //Class field (val or var)
    def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")
    def greet(): Unit = println(s"$name and ${this.name} are the same here")  //Overloaded method
    def this(name: String) = this(name, 0)                                    //Overloaded constructor (not recommended!!!)
  }

  /** Exercises */
  //1. Novel (Book) and a Writer class
  //
  //  => Novel: name, year of release, author (a Writer)
  //    -> authorAge method - age of author at the year of release
  //    -> isWrittenBy(author) method
  //    -> copy(new year of release) = new instance of Novel (author revised the edition)
  //
  //  => Writer: first name, surname, year of birth
  //    -> fullName method (first name + surname)
  val hemingway = new Writer("Ernest", "Hemingway", 1899)
  val oldMan = new Novel("The Old Man and the Sea", 1951, hemingway)
  val bellTolls = new Novel("For Whom the Bell Tolls", 1940, hemingway)

  print(hemingway)
  print(oldMan)
  print(bellTolls)

  def print(writer: Writer) =
    println(s"[${hemingway.fullName()} was born in ${hemingway.yearOfBirth}]")

  def print(novel: Novel) =
    println(s"[${novel.name}] was written by [${novel.author.fullName()}] when he was [${novel.authorAge()}] and released in [${novel.yearOfRelease}]")

  class Novel(val name: String, val yearOfRelease: Int, val author: Writer) {
    def authorAge() = yearOfRelease - author.yearOfBirth
    def isWrittenBy(author: Writer): Boolean = this.author == author          //Same EXACT object!!!
    def copy(yearOfRelease: Int): Novel = new Novel(this.name, yearOfRelease, this.author)
  }

  class Writer(firstName: String, surName: String, val yearOfBirth:Int) {
    def fullName(): String = s"$firstName $surName"
  }

  //2. Counter class
  //  -> Receives an int value
  //  -> Current method - returns current count
  //  -> increment/decrement method (returns a new Counter)
  //  -> Overload increment/decrement to receive an amount (returns a new Counter)
  val counter = new Counter                   //No parens needed for parameter-less constructors or methods
  counter.increment.print
  counter.increment.increment.increment.print
  counter.increment(5).print

  class Counter(val count: Int = 0) {
    def increment = new Counter(count + 1)    //Immutable
    def increment(amount: Int): Counter = {
      //Can also call the other increment method n times
      //BUT instead of looping, use recursion!!!
      if (amount <= 0) this                   //Optimization - nothing to increment, so just use the SAME instance!!!
      else increment.increment(amount - 1)
    }
    def decrement = new Counter(count - 1)
    def decrement(amount: Int) = new Counter(count - amount)
    def print = println(count)
  }
}




