package net.sattler22.lectures.part1basics

/**
 * Values, Variables and Types Takeaways:
 * <ol>
 *   <li>val is immutable</li>
 *   <li>var is mutable</li>
 *   <li>Prefer val to var</li>
 *   <li>Both have types</li>
 *   <li>Compiler infers type when omitted</li>
 *   <li>Need extends App to run in IntelliJ
 * </ol>
 */
object ValuesVariablesTypes extends App {

  /** Values (No Side Effects) */
  //val x: Int = 42;  //Okay
  val x = 42          //Type inferred by compiler
  //x = 2             //They are immutable!!!
  println(x)

  //Semi-colons are optional (required for multiple statements per line):
  val s1: String = "Pete";
  println(s1)

  /** Types */
  val bool1 = true
  val char1 = 'a'
  val int1 = x                //4 bytes
  val short1:  Short = 12345  //2 bytes
  val long1 = 123L            //8 bytes
  val float1 = 2.0f
  val double1: Double = 3.0

  /** Variables (Have Side Effects) */
  var v1: Int = 4
  println(v1)
  v1 = 5            //Mutable, but has SIDE EFFECTS
  println(v1)
}
