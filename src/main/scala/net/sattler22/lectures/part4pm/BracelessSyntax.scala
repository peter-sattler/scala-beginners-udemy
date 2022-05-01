package net.sattler22.lectures.part4pm

/**
 * Scala 3: Braceless Syntax Takeaways:
 * <ol>
 *   <li>Scala 3 braceless syntax eliminates braces and parenthesis</li>
 *   <li>Scope is managed by using significant indentation (tabs or spaces)</li>
 *   <li>An optional end token can be used to signal the end of the indentation region</li>
 *   <li>Significant indentation:</li>
 *   <ul>
 *     <li>Simply means a strictly larger indentation, but it must be comparable</li>
 *     <li>Be careful mixing spaces and tabs since some combinations are not comparable</li>
 *     <li>For example: [3 tabs + 2 spaces] is not comparable with [2 tabs and 3 spaces]</li>
 *     <li>IMPORTANT: Do not mix tabs and spaces when using braceless syntax!!!</li>
 *   </ul>
 *   <li>Braceless syntax is supported by:</li>
 *   <ol>
 *     <li>If Expressions</li>
 *     <li>For-Comprehensions</li>
 *     <li>Pattern Matching</li>
 *     <li>Method Bodies</li>
 *     <li>Class definitions (including traits, objects, enums, data types, etc.)</li>
 *   </ol>
 *   <li>It is not recommended to mix styles. Choose one and stay consistent.</li>
 * </ol>
 */
object BracelessSyntax extends App {

  //1. If expressions:
  val ifOneLineStyle = if (2 > 3) "Bigger" else "Smaller"

  val ifJavaStyle =
    if (2 > 3) {
      "Bigger"
    }
    else {
      "Smaller"
    }

  val ifCompactStyle =
    if (2 > 3) "Bigger"
    else "Smaller"

  val ifBracelessOneLineStyle = if 2 > 3 then "Bigger" else "Smaller"

  val ifBracelessStyle =
  if 2 > 3 then
    val result = "Bigger"
    result                                                                  //Needs higher indentation than the if part
  else
    val result = "Smaller"
    result
  println(s"Braceless if expression: [$ifBracelessStyle]")                  //[Smaller]

  //2. For-comprehensions:
  val forComprehensionBracelessStyle =
    for
      num <- List(1,2,3)
      color <- List("Black","White")
    yield s"$color $num"
  println(s"Braceless for-comprehension: $forComprehensionBracelessStyle")  //List(Black 1, White 1, Black 2, White 2, Black 3, White 3)

  //3. Pattern matching:
  val meaningOfLife = 42
  val patternMatchingBracelessStyle = meaningOfLife match
    case 1 => "Keep searching..."
    case 42 => "Your life now has purpose"
    case _ => "Remember it is the journey that counts..."
  println(s"Braceless pattern matching: [$patternMatchingBracelessStyle]")  //[Your life now has purpose]

  //4. Method bodies:
  def computeMeaningOfLife(meaningLess: Long): Int =
    val partialResult = 40
    partialResult + 2
  println(s"Braceless methods: [${computeMeaningOfLife(4815162342)}]")      //[42] (But, I "Lost" my way for a bit...)

  //5. Class definitions (including traits, objects, enums, data types, etc.):
  class Animal:                                                             //Compiler requires a colon to signal the start of an indentation region
    def eat(): Unit =
      println("I'm eating")

    def grow(): Unit =
      println("I'm growing")
  end Animal                                                                //Optional end token to signal end of indentation region

  //Works for an anonymous class too:
  val specialAnimal = new Animal:
    override def eat(): Unit = println("I'm eating special food")
  end specialAnimal
}
