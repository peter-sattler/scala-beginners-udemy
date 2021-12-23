package net.sattler22.lectures.part1basics

/**
 * Call by Name vs. Call by Value Takeaways:
 * <ol>
 *   <li>Call by value:</li>
 *   <ul>
 *     <li>Value is computed before call</li>
 *     <li>Same value used everywhere</li>
 *   </ul>
 *   <li>Call by name:</li>
 *   <ul>
 *     <li>Expression is passed literally</li>
 *     <li>Expression is evaluated at every use within</li>
 *   </ul>
 * </ol>
 */
object CBNvsCBV extends App {

  def calledByValue(x: Long): Unit = {
    println("Call by value: " + x)
    println("Call by value: " + x)
  }

  //The expression is evaluated each time (=> delays the evaluation)
  def calledByName(x: => Long): Unit = {
    println("Call by name: " + x)
    println("Call by name: " + x)
  }

  calledByValue(System.nanoTime())  //these 2 are the same
  calledByName(System.nanoTime())   //these 2 are different

  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

  printFirst(infinite(), 34)  //StackOverflow error
  printFirst(34, infinite())  //Just fine (execution delayed and NEVER used)
}
