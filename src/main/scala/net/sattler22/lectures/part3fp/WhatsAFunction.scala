package net.sattler22.lectures.part3fp

/**
 * What's a Function Takeaways:
 * <ol>
 *   <li>We want to work with functions as first-class programming elements:</li>
 *   <ul>
 *     <li>Pass functions as parameters</li>
 *     <li>Return functions as results</li>
 *   </ul>
 *   <li>Problem - Scala works on top of the JVM:</li>
 *   <ul>
 *     <li>It was designed for Java</li>
 *     <li>Java's first class elements are objects and instances of objects (OOP)</li>
 *   </ul>
 *   <li>Solution - All Scala functions are objects:</li>
 *   <ul>
 *     <li>Scala simulates functional programming by turning functions into instances of special kinds of function-like types</li>
 *     <li>Scala supports function traits with up to 22 parameters (<i>Function1 ... Function22</i>)</li>
 *     <ul>
 *       <li>The last parameter in the argument list represents the return type</li>
 *       <li>Scala becomes a truly functional language through a series of syntactic features</li>
 *       <ul>
 *         <li>For example: <i>Function2[Int, String, Int]</i> becomes <i>(Int, String) => Int</i></li>
 *       </ul>
 *       <li>Higher-order functions either receive functions as parameters or return other functions as a result</li>
 *       <li>Curried functions can be called with multiple parameter lists</li>
 *     </ul>
 *   </ul>
 * </ol>
 */
object WhatsAFunction extends App {

  //Functional programming simulation using OOP concepts:
  trait MyFunction[A, B] {
    def apply(element: A): B                              //Special meaning in Scala
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }
  println(s"Double your money: [${doubler(2)}]")          //Acts like a function!!!

  //Function types (up to 22 parameters):
  val stringToIntegerConverter = new Function1[String, Int] {
    override def apply(s: String): Int = s.toInt
  }
  println(s"String to integer converter: [${stringToIntegerConverter("3") + 4}]")

  //Syntactic sugar:
  val adder: Function2[Int, Int, Int] = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }
  val adderWithSugar: (Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b       //Function2[A, B, R] can also be expressed as (A, B) => R
  }
  val adderWithExtraSugar: (Int, Int) => Int = (a: Int, b: Int) => a + b
  println(s"Adding two integers: [${adderWithExtraSugar(2, 3)}]")

  /** Exercises */
  //Define a function which takes two strings and concatenates them:
  def concat: (String, String) => String = (s1: String, s2: String) => s1 + s2
  println(s"Concatenate two strings: [${concat("Hello ", "Scala")}]")

  //Modify the MyPredicate and MyTransformer from the MyList exercise into function types:
  //ANSWER: These don't make sense anymore because they can be replaced with their function type (see below)!!!
  trait MyPredicate[-T] {
    def test(element: T): Boolean                         //Function type: T => Boolean
  }

  trait MyTransformer[-A, B] {
    def transform(element: A): B                          //Function type: A => B
  }

  //Define a higher-order function which takes an integer argument and returns another function which takes an integer argument and returns an integer:
  //  -> What is the type of this function?
  //    -> Function1[Int, Function1[Int, Int]]
  //  -> How is it implemented?
  //    -> Use nested overrides
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }
  val adder3 = superAdder(3);                             //New function with type Int => Int
  println(s"Adder3 returns: [${adder3(4)}]")
  println(s"Super Adder returns: [${superAdder(3)(4)}]")  //Curried function (called with multiple parameter lists)
}
