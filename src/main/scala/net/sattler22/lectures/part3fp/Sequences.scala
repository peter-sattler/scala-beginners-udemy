package net.sattler22.lectures.part3fp

import scala.util.Random

/**
 * Sequences Takeaways:
 * <ol>
 *   <li>Scala offers both mutable and immutable collections</li>
 *   <li>The Scala standard library has type aliases for the immutable ones, so they are the default</li>
 *   <li>The <i>Traversable</i> trait is the base trait for all collections</li>
 *   <li>Sequences:</li>
 *   <ul>
 *     <li>Have a well defined order</li>
 *     <li>Can be indexed</li>
 *   </ul>
 *   <li>List:</li>
 *   <ul>
 *     <li>Are an immutable linked list which extends <i>LinearSeq</i>:</li>
 *     <ol>
 *       <li><i>head</i>, <i>tail</i>, <i>isEmpty</i> are fast: O(1)</li>
 *       <li>Most other operations are linear time: O(n)</li>
 *     </ol>
 *     <li>Sealed with two subtypes:</li>
 *     <ol>
 *       <li>Object <i>Nil</i> which denotes an empty list (<i>MyList</i> extended <i>Empty</i>)</li>
 *       <li>Case class <i>::</i> (<i>MyList</i> used <i>Cons</i>)</li>
 *     </ol>
 *   </ul>
 *   <li>Array:</li>
 *   <ul>
 *     <li>The equivalent of simple Java arrays</li>
 *     <li>Can be constructed with predefined lengths</li>
 *     <li>Can be mutated (updated in place)</li>
 *     <li>Interoperable with Java's native arrays</li>
 *     <li>Indexing is fast: O(1)</li
 *   </ul>
 *   <li>Vector:</li>
 *   <ul>
 *     <li>The default implementation for immutable sequences because it is very efficient</li>
 *     <li>Effectively constant indexed read and write (very slow growing logarithmic): O(log32(n))</li>
 *     <li>Fast element addition: append and prepend</li>
 *     <li>Implemented as fixed branched trie (branch factor 32)</li>
 *     <li>Good performance for very large sizes</li>
 *   </ul>
 *   <li>Vector vs. List:</li>
 *     <ul>
 *       <li>List:</li>
 *       <ol>
 *         <li>GOOD: Keeps reference to tail</li>
 *         <li>BAD: Updating an element in the middle is slow</li>
 *       </ol>
 *       <li>Vector:</li>
 *       <ol>
 *         <li>GOOD: Depth of tree is small</li>
 *         <li>BAD: Needs to replace an entire 32-element chunk</li>
 *       </ol>
 *     </ul>
 *     <li>RESULT: <i>Vector</i> outperforms <i>List</i> by three orders of magnitude!!!</li>
 * </ol>
 */
object Sequences extends App {

  //Sequences:
  var seq: Seq[Int] = Seq(1,3,4,2)                           //Type is Seq[Int]
  println(s"Sequence: $seq")                                 //List(1, 3, 4, 2)
  println(s"Sequence reverse: ${seq.reverse}")               //List(2, 4, 3, 1)
  println(s"Sequence apply: [${seq(2)}]")                    //[4]
  println(s"Sequence concatenation: ${seq ++ Seq(5,6,7)}")   //List(1, 3, 4, 2, 5, 6, 7)
  println(s"Sequence sorted: ${seq.sorted}")                 //List(1, 2, 3, 4)

  //Ranges:
  val range: Seq[Int] = 1 to 3
  println(s"Range: $range")                                  //Range 1 to 3
  range.foreach(println)                                     //1 (new line), 2 (new line), 3 (new line)
  (1 to 3).foreach(x => println("Do something"))             //NOTE: A quick way to do something without recursion

  //List:
  val list = List(1,2,3)
  println(s"List: $list")                                    //List(1, 2, 3)
  println(s"List appended: ${list :+ 89}")                   //List(1, 2, 3, 89)
  println(s"List prepended #1: ${42 :: list}")               //List(42, 1, 2, 3)
  println(s"List prepended #2: ${42 +: list}")               //List(42, 1, 2, 3)
  val appleList = List.fill(3)("apple")
  println(s"List fill: $appleList")                          //List(apple, apple, apple)
  println(s"List mkString: [${appleList.mkString("-:-")}]")  //apple-:-apple-:-apple

  //Arrays:
  val numbers = Array(1,2,3,4)
  println(s"Array: [${numbers.mkString(",")}]")              //[1,2,3,4]
  Array.ofDim[Int](2).foreach(println)                       //Initialized with zeroes
  Array.ofDim[String](2).foreach(println)                    //Initialized with NULLs
  numbers(2) = 0                                             //Syntactic sugar: numbers.update(2, 0)
  println(s"Mutated array: [${numbers.mkString(",")}]")      //[1,2,0,4]
  val numbersSeq: Seq[Int] = numbers                         //Implicit conversion
  println(s"Numbers sequence: $numbersSeq")                  //ArraySeq(1, 2, 0, 4)

  //Vector:
  val vector = Vector(1,2,3)
  println(s"Vector: $vector")                                //Vector(1, 2, 3)

  //Vectors vs. Lists:
  val maxRuns = 1000
  val maxCapacity = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val random = new Random
    val times = for {
      iteration <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(random.nextInt(maxCapacity), random.nextInt())
      System.nanoTime() - currentTime
    }
    times.sum * 1.0 / maxRuns
  }
  //List:
  //GOOD: Keeps reference to tail
  //BAD: Updating an element in the middle is slow
  println("List performance is running...")
  val numbersList = (1 to maxCapacity).toList
  println(s"List performance: ${getWriteTime(numbersList)}")

  //Vector:
  //GOOD: Depth of tree is small
  //BAD: Needs to replace an entire 32-element chunk
  val numbersVector = (1 to maxCapacity).toVector
  println(s"Vector performance: ${getWriteTime(numbersVector)}")
}
