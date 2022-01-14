package net.sattler22.lectures.part1basics

/**
 * Functions Takeaways:
 * <ol>
 *   <li>How to define Scala functions</li>
 *   <li>Using Unit type</li>
 *   <li>Use nest functions (code blocks)</li>
 *   <li>Recursion in Scala</li>
 *   <li>Compiler can infer data types and return types, but not the return type for a recursive function</li>
 * </ol>
 */
object Functions extends App {

  def function1(a: String, b: Int): String = {
    a + " " + b
  }
  println(function1("Hello1", 3))  //Also an EXPRESSION!!!

  def function2(): Int = 42
  println(function2())
  println(function2)               //Parameterless -- Scala 2 Only!!!

  //WHEN YOU NEED LOOPS, USE RECURSION!!!
  def concatenate(s: String, n: Int): String = {
    if (n == 1) s
    else s + concatenate(s, n - 1)
  }
  println(concatenate("Recursion1", 3))

  //The compiler can infer the return type EXCEPT for recursive functions!!!
  //Best Practice: Always specify the return type
  def function2(a: String, b: Int) = a + " " + b
  println(function2("Hello2", 3))

  //Unit is an acceptable return type:
  //NOTE: Side effects are needed if it does something besides computations (like printing to the console)!!!
  def sideEffectFunction1(s: String): Unit = println(s)

  //Code blocks allow nesting:
  def bigFunction1(n: Int): Int = {
    def smallerFunction1(a: Int, b: Int): Int = a + b
    smallerFunction1(n, n - 1)     //Return EXPRESSION
  }

  /** Exercises */
  //1. Greeting function(name, age) => "Hi, my name is $name and I am $age years old."
  def greeting(name: String, age: Int): String = "Hi, my name is " + name + " and I am " + age + " years old."
  println(greeting("Pete", 56))

  //2. Factorial function => 1 * 2 * 3 ... n (recursive)
  def factorial(n: Int): Int = {
    if (n <= 0) 1
    else n * factorial(n - 1)
  }
  println(factorial(5))  //120

  //3. Fibonacci function => prints the nth number in the sequence that always starts with [1, 1]
  //   fn(1) = 1
  //   fn(2) = 1
  //   fn(n) = fn(n-1) + fn(n-2) (recursive)
  def fibonacci(n: Int): Int = {
    if (n <= 2) 1  //The first two in the sequence are 1
    else fibonacci(n - 1) + fibonacci(n - 2)
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

  //4. Prime number function: divisible by 1 and itself
  //NOTE: Uses an auxiliary function (withing a code block)
  def isPrime(n: Int): Boolean = {
    //Does n have any divisors up to the number t?
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1) //% is the mod operator
    }
    isPrimeUntil(n / 2)
  }
  println(isPrime(1))
  println(isPrime(4))
  println(isPrime(11))
}
