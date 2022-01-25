package net.sattler22.lectures.part2oop

/**
 * Exceptions Takeaways:
 * <ol>
 *   <li>Exceptions crash your program :(</li>
 *   <li>Throwing Exceptions:</li>
 *   <ul>
 *     <li>Throwing an exception in Scala (like everything else) is an <strong>expression</strong></li>
 *     <li>Throwing an exception always returns scala.Nothing</li>
 *     <li>Scala uses the same exception hierarchy as Java:</li>
 *     <ul>
 *       <li>Exceptions are classes that extend the <i>Throwable</i> class</li>
 *       <li><i>Exception</i> and <i>Error</i> are the major <i>Throwable</i> sub-types</li>
 *       <li>Exceptions indicate something went wrong with the <strong>program</strong></li>
 *       <li>Errors indicate something went wrong with the <strong>system</strong> (JVM)</li>
 *     </ul>
 *   </ul>
 *   <li>How to Catch Exceptions:</li>
 *   <ul>
 *     <li>Exceptions come from Java and are specific to the JVM</li>
 *     <li>The value of the <i>try/catch/finally</i> expression is scala.AnyVal, but it may be more specific depending on the usage</li>
 *     <li>The <i>finally</i> expression is optional and will get executed no matter what</li>
 *     <li>The <i>finally</i> does not influence the return type of the expression (use for side effects only)</li>
 *   </ul>
 *   <li>Custom Exceptions:</li>
 *   <ul>
 *     <li>Simply extend it from the <i>Exception</i> class</li>
 *     <li>Then throw it like any other exception!!!</li>
 *   <ul>
 * </ol>
 */
object Exceptions extends App {

  //Throwing an exception:
  //val exception1 = throw new NullPointerException          //Nothing (no value), but can still be assigned!!!
  //val exception2: String = throw new NullPointerException  //scala.Nothing is always a valid substitute for any other type

  //Catching an exception:
  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("Integer Runtime Exception!!!")
    else 42
  }
  val potentialFail = try {
    getInt(true)
  }
  catch {
    case exception: RuntimeException => println(exception.getMessage)
  }
  finally {
    println("Finally expression gets executed no matter what")
  }

  //Custom exceptions:
  class MyException extends Exception
  try {
    val exception = new MyException
    throw exception
  }
  catch {
    case exception: MyException => println("Custom Exception!!!")
  }

  /** Exercises */
  //Crash program with an out of memory error (OOM):
  try {
    val crash = Array.ofDim[Int](Int.MaxValue)               //Too many elements
  }
  catch {
    case exception: OutOfMemoryError => println(exception.getMessage)
  }

  //Crash with a stack overflow error:
  def infinite: Int = 1 + infinite
  try {
    val fillTheKnownUniverse = infinite
  }
  catch {
    case exception: StackOverflowError => println(exception)
  }
  //PocketCalculator class (use Ints):
  //   -> add(x,y)
  //   -> subtract(x,y)
  //   -> multiply(x,y)
  //   -> divide(x,y)
  //   Throw
  //     -> OverflowException if add(x,y) exceeds Int.MAX_VALUE
  //     -> UnderflowException if subtract(x,y) exceeds Int.MIN_VALUE
  //     -> MathCalculationException for division by zero
  class OverflowException extends RuntimeException
  class UnderflowException extends RuntimeException
  class MathCalculationException extends RuntimeException("Division by zero")

  //Use object since we don't need instances:
  object PocketCalculator {
    def add(x: Int, y: Int) = {
      val result = x + y
      //Test for a negative result given 2 positive numbers (overflows):
      //IMPORTANT: Cannot check if result is bigger than Int.MaxValue since it will always be false!!!
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      //Test for a positive result given 2 negative numbers (underflow):
      if (x < 0 && y < 0 && result > 0) throw new UnderflowException
      result
    }
    def subtract(x: Int, y: Int) = {
      //NOTE: Left out exception conditions here (same idea as add method)...
      x - y
    }
    def multiply(x: Int, y: Int) = {
      x * y
    }
    def divide(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      x / y
    }
  }
  try {
    println(PocketCalculator.add(Int.MaxValue * - 1, -5))
  }
  catch {
    case exception: UnderflowException => println(s"Pocket Calculator: $exception")
  }
  try {
    println(PocketCalculator.add(Int.MaxValue, 5))
  }
  catch {
    case exception: OverflowException => println(s"Pocket Calculator: $exception")
  }
  try {
    println(PocketCalculator.divide(10, 0))
  }
  catch {
    case exception: MathCalculationException => println(s"Pocket Calculator: ${exception.getMessage}")
  }
}
