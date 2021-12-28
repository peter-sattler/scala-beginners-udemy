package net.sattler22.lectures.part1basics

/**
 * Expressions Takeaways:
 * <ol>
 *   <li>Basic Expressions: Operators</li>
 *   <li>IF in Scala is an expression</li>
 *   <li>Code Block in Scala are expressions</li>
 *   <ul>
 *     <li>The value of the block is the value of its last expression</li>
 *   </ul>
 *   <li>Expressions vs. Instructions</li>
 *   <ul>
 *     <li>Instructions are executed (think Java)</li>
 *     <li>Expressions are evaluated (think Scala)</li>
 *     <li>In Scala, we think in terms of Expressions</li>
 *   </ul>
 *   <li>Do NOT use while loops in your Scala code!!!</li>
 * </ol>
 */
object Expressions extends App {

  /** Variable Side Effects */
  val x1 = 1 + 2       //LHS is an expression
  println(x1)

  println(2 - 3 * 4)   //Math expressions (including bitwise)
  println(1 == x1)     //Relational operators
  println(!(1 == x1))  //Boolean operators

  var x2 = 2
  x2 += 3
  println(x2)

  /** Instructions (DO - Imperative Style) vs Expressions (VALUE - Functional Style) */

  //IF is an expression - gives back a VALUE!!!
  val condition1 = true
  val condition1Value =  if(condition1) 5 else 3
  println(condition1Value)

  //LOOPS are discouraged since they are an IMPERATIVE style
  //NOTE: NEVER WRITE THIS AGAIN!!!
  var i = 0
  while (i < 10) {
    println(i)
    i += 1
  }

  //EVERYTHING in Scala is an EXPRESSION!!!
  val weirdValue = (x2 = 3)  //Unit type is void
  println(weirdValue)

  //Side Effects in Scala are EXPRESSIONS returning Units
  //Examples: println(), whiles, reassignments
  val while1 = while (i < 10) {
    println(i)
    i += 1
  }
  println(while1)

  //Code Blocks:
  //An EXPRESSION whose VALUE is the VALUE of the LAST expression
  val codeBlock1 = {
    val y = 2
    val z = 1
    if (z > 2) "Hello" else "Goodbye"
  }
  //val z2 = z + 1;  //Not visible outside code block!!!

  /** Exercises */
  //1. What is the difference between:
  //"Hello World":
  // ==> Type is String (String literal)
  //println("Hello World"):
  //  ==> Type is Unit
  //  ==> Side effect: prints it to the console

  //2. What do these evaluate to?
  //someValue1: true (boolean)
  //someValue2: 42 (integer)

  val someValue1 = {
    2 < 3
  }
  println(someValue1)

  val someValue2 = {
    if(someValue1) 239 else 986
    42
  }
  print(someValue2)
}
