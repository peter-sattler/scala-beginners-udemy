package net.sattler22.lectures.part2oop

/**
 * Exceptions Takeaways:
 * <ol>
 *   <li>Exceptions crash your program</li>
 *   <li>Throwing them returns Nothing</li>
 * </ol>
 */
object Exceptions extends App {

  //1. Throwing an exception in Scala (like everything else) is an expression:
  //Same exception hierarchy as Java (Throwable sub-classes are Exception and Error)
  val exception1 = throw new NullPointerException          //Nothing - no value, but can be assigned
  val exception2: String = throw new NullPointerException  //Nothing is a valid substitute for any other type

  //2. Catching exceptions:
  //NOTE: Exceptions are JVM specific
  //The value of try/catch is AnyVal (try is Int and catch is Int), so it depends
  //Finally block is optional and does not influence the return type. Use for side effects only!!!
  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("Integer exception")
    else 42
  }
  val potentialFail = try {
    getInt(true)
  }
  catch {
    case exception: RuntimeException => println("Caught a RuntimeException")
  }
  finally {
    println("Finally gets executed no matter what")
  }

  //3. Custom exceptions:
  class MyException extends Exception
  val exception = new MyException
  throw exception

  /** Exercises */

  //1. Crash program with an OutOfMemoryError (OOM):
  val array = Array.ofDim(Int.MaxValue)  //Too many elements

  //2. Crash with StackOverflowError:
  def infinite: Int = 1 + infinite
  val noLimit = infinite

  //3. PocketCalculator class (use Ints):
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
      //Left out exception conditions (same idea as add)...
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
  println(PocketCalculator.add(Int.MaxValue, 5))
  println(PocketCalculator.divide(10, 0))
}
