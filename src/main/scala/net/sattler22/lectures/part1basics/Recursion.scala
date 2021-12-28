package net.sattler22.lectures.part1basics

import scala.annotation.tailrec

/**
 * Recursion Takeaways:
 * <ol>
 *   <li>The JVM uses a call stack to track recursive calls (each one is a stack frame)</li>
 *   <li>When you need loops, use TAIL recursion (to avoid a stack overflow error)</li>
 * </ol>
 */
object Recursion extends App {

  /** Watch the stack */
  //WARNING: Potential stack over flow error!!!
  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + ", but I first need factorial of " + (n - 1))
      val result = n * factorial(n - 1)  //Needs ANOTHER stack frame
      println("Computed factorial of " + n)
      result
    }
  }
  println(factorial(10))
//  println(factorial(5000))  //FAIL -- Recursion too deep!!!

  //TAIL RECURSION - use recursive call as the LAST expression on each code path
  def factorialTailRecursion(n: Int): BigInt = {
    @tailrec
    def factorialHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factorialHelper(x - 1, x * accumulator)  //REPLACES current stack frame
    }
    factorialHelper(n, 1)
  }

  /*
   * factorialTailRecursion(10)
   *   = factorialHelper(10, 1)   //else branch
   *   = factorialHelper(9, 10 * 1)
   *   = factorialHelper(8, 9 * 10 * 1)
   *   = factorialHelper(7, 8 * 9 * 10 * 1)
   *   = ... more ...
   *   = factorialHelper(2, 3 * 4 * ... * 10 * 1)
   *   = factorialHelper(1, 1 * 2 * 3 * 4 * ... * 10)
   *   = 1 * 2 * 3 * 4 * .. * 10  //if branch
   */
  println(factorialTailRecursion(20000))

  /** Exercises */

  //1. Concatenate a string n times (tail recursive)
  @tailrec
  //NOTE: The accumulator type always matches the return type!!!
  def concatenateTailRec(s: String, n: Int, accumulator: String): String = {
    if (n <= 0) accumulator
    else concatenateTailRec(s, n - 1, accumulator + s)
  }
  println(concatenateTailRec("TailRec1", 3, ""))

  //2. isPrime function (tail recursive)
  def isPrime(n: Int): Boolean = {
    @tailrec
    //NOTE: Instead of a recursive call, store the intermediate result in isStillPrime
    def isPrimeTailRec(limit: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (limit <= 1) true
      else isPrimeTailRec(limit - 1, n % limit != 0 && isStillPrime)
    }
    isPrimeTailRec(n / 2, true)
  }
  println(isPrime(1))
  println(isPrime(4))
  println(isPrime(11))

  //3. Fibonacci function (tail recursive)
  def fibonacci(n: Int): Int = {
    @tailrec
    //NOTE: Tail recursion requires an accumulator for each recursive call on the call path
    //  last accumulator => fibonacci(n-1) recursive call
    //  nextToLast accumulator => fibonacci(n-2) recursive call
    def fibonacciTailRec(i: Int, last: Int, nextToLast: Int): Int = {
      if (i >= n) last  //Final result
      else fibonacciTailRec(i + 1, last + nextToLast, last)
    }
    if (n <= 2) 1  //Optimization
    else fibonacciTailRec(2, 1, 1)
  }
  // 1 1 2 3 5 8 13 21
  print(fibonacci(1) + " ")
  print(fibonacci(2) + " ")
  print(fibonacci(3) + " ")
  print(fibonacci(4) + " ")
  print(fibonacci(5) + " ")
  print(fibonacci(6) + " ")
  print(fibonacci(7) + " ")
  println(fibonacci(8))
}
