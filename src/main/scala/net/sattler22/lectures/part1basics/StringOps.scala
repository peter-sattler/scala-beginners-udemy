package net.sattler22.lectures.part1basics

/**
 * Smart Operations on Strings Takeaways:
 * <ol>
 *   <li>All Java string functions are available</li>
 *   <li>There are additional Scala-specific string functions</li>
 *   <li>Scala supports string interpolation (s"foo bar")</li>
 *   <li>Scala String F-interpolation (f"rutabega%s") </li>
 *   <ul>
 *     <li>Supports all printf formatters</li>
 *     <li>Compiler checks for type correctness</li>
 *   </ul>
 *   <li>Scala String Raw-interpolation</li>
 * </ol>
 */
object StringOps extends App {

  //Java string functions:
  val s1 = "Hello, I am learning Scala"
  println(s1.charAt(2))        //An "l"
  println(s1.substring(7,11))  //I am
  println(s1.split(" ").toList)
  println(s1.replace(" ", "-"))
  println(s1.toLowerCase())
  println(s1.length)

  //Scala-specific string functions:
  val numString1 = "45"
  val num1 = numString1.toInt
  println('a' +: numString1 :+ 'z')  //Prepending and appending respect
  println(s1.reverse)
  println(s1.take(2))  //He

  //Scala-specific string interpolation:
  val name = "Pete"
  val age = 56
  val greeting1 = s"Hello, my name is $name and I am $age years old"
  println(greeting1)
  val greeting2 = s"Hello, my name is $name and I am turning ${age + 1} years old"
  println(greeting2)

  //Scala-specific F-interpolation (printf formats):
  val speed = 1.2f
  val myth = f"$name can eat $speed%2.2f burgers per minute"
  println(myth)

  //Scala-specific Raw-interpolation:
  println(raw"This is a \n newline")  //Ignores escaped characters
  val escaped = "This is a \n newline"
  println(raw"$escaped")
}
