package net.sattler22.lectures.part3fp

import scala.annotation.tailrec

/**
 * Higher Order Functions (HOFs) and Curries Takeaways:
 * <ol>
 *   <li>Functional programming is all about working with functions:</li>
 *   <ul>
 *     <li>Pass functions as parameters:</li>
 *     <ul>
 *       <li>Examples include <i>map</i>, <i>flatMap</i> and <i>filter</i> from the <i>MyList</i> exercise because each one
 *           has a function as a parameter</li>
 *     </ul>
 *     <li>Return functions as results</li>
 *     <li>These are called higher order functions (HOFs)</li>
 *     <li>Currying refers to functions with multiple parameter lists:</li>
 *     <ul>
 *       <li>Allows smaller functions to be defined for later use</li>
 *     </ul>
 *   </ul>
 *   <li>Functional programming is a direct mapping over a number of branches of mathematics</li>
 *   <li>Functional programming is not easy!!!</li>
 * </ol>
 */
object HOFsCurries extends App {

  //Super function takes two parameters (an integer and a function) and returns another function:
  //Parameters (LHS):
  //  -> First: Int
  //  -> Second: String, (Int => Boolean)) => Int (another function which returns an integer)
  //Return Type (RHS):
  //  -> Int => Int (a function that takes an integer and returns an integer)
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null

  //Define a function that applies a function n times over a given value x:
  //nTimes(f, n, x) where:
  //  -> f is the function to apply
  //  -> n is the number of applications
  //  -> x is the subject of the application of the function
  //nTimes(f, 3, x) would be f(f(f(x))) or nTimes(f, 2, f(x))
  //nTimes(f, n, x) would be generalized as f(f(...f(x))) or nTimes(f, n-1, f(x))
  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x                   //Nothing to apply so just return the value
    else nTimes(f, n-1, f(x))
  }
  val plusOne = (x: Int) => x + 1
  println(s"Plus one function applied ten times to the value one: [${nTimes(plusOne, 10, 1)}]")

  //Instead of passing all parameters (f, n and x), just pass the first two and return a lambda that can be applied to any value:
  //nTimesBetter(f, n) = x => f(f(...f(x))) which returns a lambda which can be applied later to various values
  //increment10 = nTimesBetter(plusOne, 10) = x => plusOne(plusOne(...plusOne...(x)))
  //val y = increment10(1)
  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
    if (n <= 0) (x: Int) => x       //Nothing to apply so return the identity function
    else (x: Int) => nTimesBetter(f, n-1)(f(x))
  }
  val plusTen = nTimesBetter(plusOne, 10)
  println(s"Plus ten function applied to the value one: [${plusTen(1)}]")

  //Curried functions:
  val superAdder1 = (x: Int) => (y: Int) => x + y
  def superAdder2: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  val adder3 = superAdder2(3)       //Helper function (lambda): y => 3 + y
  println(s"Adder3 returns: [${adder3(10)}]")
  println(s"Super Adder2 returns: [${superAdder2(3)(10)}]")

  //Functions with multiple parameter lists:
  //  -> There can be as many parameter lists as needed, but their type MUST be specified!!!
  //  -> Smaller functions can then be created for later use
  def curriedFormatter(c: String)(x: Double): String = c.format(x)
  val standardFormatter: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormatter: (Double => String) = curriedFormatter("%10.8f")
  println(s"PI using standard formatter: [${standardFormatter(Math.PI)}]")
  println(s"PI using precise formatter: [${preciseFormatter(Math.PI)}]")
}
