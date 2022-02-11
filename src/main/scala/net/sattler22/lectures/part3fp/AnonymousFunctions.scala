package net.sattler22.lectures.part3fp

import net.sattler22.exercises.{Cons, Empty, MyList}

/**
 * Anonymous Functions Takeaways:
 * <ol>
 *   <li>Passing anonymous <i>Function1 ... Function22</i> instances is cumbersome and still object-oriented</li>
 *   <li>Functional programming uses anonymous functions (lambdas) instead:</li>
 *   <ul>
 *     <li>The term originates from Lambda Calculus</li>
 *     <li>Basic form: <i>(x, y) => x + y</i></li>
 *     <li>Parameters appear at the beginning</li>
 *     <li>Each parameter has a name and an optional type</li>
 *     <li>Parentheses are mandatory for more than one parameter</li>
 *     <li>The implementation (or expression) appears after the arrow</li>
 *     <li>The return type is always inferred</li>
 *     <li>Underscores can be used as additional syntactic sugar</li>
 *   </ul>
 *   <li>Anatomy of a Lambda:</li>
 *   <pre>
 *                   name
 *                    |  type (optional)
 *       parameter    |   |  return type (always inferred)
 *     +----------+   |   |  |   +---- implementation ----+
 *     |          |   |   |  |   |  (single expression)   |
 *  +--V----------V---V---V--V---V------------------------V-+
 *  | (name: String, age:Int) => s"$name is $age years old" |
 *  +-------------------------------------------------------+
 *   </pre>
 * </ol>
 */
object AnonymousFunctions extends App {

  //Still very object-oriented:
  val ooDoubler = new Function1[Int, Int] {
    override def apply(x: Int): Int = x * 2
  }
  println(s"OO Doubler: [${ooDoubler(3)}]")

  //Functional way uses an anonymous function (lambda):
  val fxDoubler1 = (x: Int) => x * 2              //Same as OO doubler
  val fxDoubler2: Int => Int = (x: Int) => x * 2  //Including return type
  val fxDoubler: Int => Int = x => x * 2          //Short-hand form using compiler type inference
  println(s"Lambda Doubler: [${fxDoubler(3)}]")

  //Multiple parameter lambda requires parentheses:
  val fxAdder1 = (a: Int, b: Int) => a + b
  val fxAdder: (Int, Int) => Int = (a, b) => a + b
  println(s"Lambda Adder: [${fxAdder(5, 3)}]")

  //No parameter lambda:
  val noParm: () => Int = () => 3                 //Type is () => Int
  println(s"No parameter Lambda: [${noParm}]")    //Function itself (calls toString method)
  println(s"No parameter Lambda: [${noParm()}]")  //Lambdas must be called with parentheses!!!

  //Using lambdas with curly braces is common practice:
  val stringToInt = { (s: String) =>
    s.toInt
  }
  println(s"Lambda string to integer: [${stringToInt("10")}]")

  //The underscore can be used to represent the parameters:
  //NOTE: Each underscore represents a different parameter!!!
  val underscoreIncrementer: Int => Int = _ + 1   //Same as x => x + 1
  val underscoreAdder: (Int, Int) => Int = _ + _  //Same as: (a, b) => a + b
  println(s"Lambda underscore incrementer: [${underscoreIncrementer(4)}]")
  println(s"Lambda underscore adder: [${underscoreAdder(5, 3)}]")

  /** Exercises */
  //Replace all FunctionX calls from the MyList exercise with lambdas:
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))

  //[1, 2, 3].map(n * 2) ==> [2, 4, 6]
  //println(listOfIntegers.map(new Function1[Int, Int] {
  //  override def apply(element: Int): Int = element * 2
  //}))
  println(listOfIntegers.map(element => element * 2))
  println(listOfIntegers.map(_ * 2))

  //[1, 2, 3].filter(n % 2 == 0) ==> [2]
  //println(listOfIntegers.filter(new Function1[Int, Boolean] {
  //  override def apply(element: Int): Boolean = element % 2 == 0
  //}).toString)
  println(listOfIntegers.filter(element => element % 2 == 0).toString)
  println(listOfIntegers.filter(_ % 2 == 0).toString)

  //[1, 2, 3].flatMap(n => n + 1) ==> [1,2, 2,3, 3,4]
  //println(listOfIntegers.flatMap(new Function1[Int, MyList[Int]] {
  //  override def apply(element: Int): MyList[Int] = new Cons(element, new Cons(element + 1, Empty))
  //}))
  println(listOfIntegers.flatMap(element => new Cons[Int](element, new Cons[Int](element + 1, Empty))))

  //Rewrite the following curried function (from What's a Function) as an anonymous function (lambda):
  //val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
  //  override def apply(x: Int): Function1[Int, Int] = new Function[Int, Int] {
  //    override def apply(y: Int): Int = x + y
  //  }
  //}
  val superLambdaAdder = (x: Int) => (y: Int) => x + y
  println(s"Super Lambda Adder returns: [${superLambdaAdder(3)(4)}]")
}
