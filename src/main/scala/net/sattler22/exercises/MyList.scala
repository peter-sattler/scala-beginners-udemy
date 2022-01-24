package net.sattler22.exercises

/**
 * Implementing Our Own Collection
 *
 * <h3>Initial Requirements</h3>
 * <ol>
 *   <li>Describes an immutable list of any type</li>
 *   <li>A singly linked list</li>
 *   <li>Methods:</li>
 *   <ul>
 *     <li><i>head</i> returns the first element of the list</li>
 *     <li><i>tail</i> returns the remainder of the list</li>
 *     <li><i>isEmpty</i> checks if the list does not contain any elements</li>
 *     <li><i>add(int)</i> adds an element to the list and returns a new (immutable) list</li>
 *     <li><i>toString</i> provides a meaningful string representation of the list</li>
 *   </ul>
 * </ol>
 *
 * <h3>Object-Oriented Exercises: Expanding Our Collection Enhancements</h3>
 * <ol>
 *   <li>Create a generic trait <i>MyPredicate(-T)</i>:</li>
 *   <ul>
 *     <li>Tests if T passes a condition</li>
 *     <li>Method: <i>test(T): Boolean</i></li>
 *     <li><strong>HINT</strong>: Define <i>MyPredicate</i> contravariant in the type T (otherwise code will not compile)</li>
 *   </ul>
 *   <li>Create a generic trait <i>MyTransformer(-A, B)</i>:</li>
 *   <ul>
 *     <li>Converts type A to type B</li>
 *     <li>Method: <i>transform(A): B</i></li>
 *     <li><strong>HINT</strong>: Define <i>MyTransformer</i> contravariant in the type A (otherwise code will not compile)</li>
 *   </ul>
 *   <li>Expand <i>MyList</i> functionality:</li>
 *   <ul>
 *     <li>Method <i>map(transformer)</i> which returns <i>MyList</i> of a different type</li>
 *     <li>Method <i>filter(predicate)</i> which returns <i>MyList</i> including only the matching elements</li>
 *     <li>Method <i>flatMap(transformer from A to MyList[B]</i> which returns <i>MyList[B]</i></li>
 *     <li>Create <i>class EvenPredicate extends MyPredicate[Int]</i> with method <i>test</i> which will return true if T is even</li>
 *     <li>Create <i>class StringToIntTransformer extends MyTransformer(String, Int)</i> with method <i>transform</i> which
 *         will convert the string into an integer</li>
 *     <li>Examples:</li>
 *     <ul>
 *       <li>[1, 2, 3].map(n * 2) results in [2, 4, 6]</li>
 *       <li>[1, 2, 3].filter(n % 2 == 0) results in [2]</li>
 *       <li>[1, 2, 3].flatMap(n => n, n+1) results in [1,2, 2,3, 3,4]</li>
 *     </ul>
 *   </ul>
 * </ol>
 */
abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]                               //Use return type [B >: A] to avoid contravariant error message
  def map[B](transformer: MyTransformer[A, B]): MyList[B]              //OO Exercises Enhancements
  def filter(predicate: MyPredicate[A]): MyList[A]                     //OO Exercises Enhancements
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]  //OO Exercises Enhancements
  def ++[B >: A](list: MyList[B]): MyList[B]                           //Concatenation
  def printElements: String                                            //Polymorphic call which delegates implementation to a sub-class
  override def toString: String = s"[$printElements]"                  //Override scala.AnyRef class
}

//Empty List:
//  -> Use object since its a singleton and only ONE empty instance is needed
//  -> Three question marks will throw a "not implemented" error
//  -> The scala.Nothing type is ALWAYS a proper substitute for scala.Any type
//  -> Good use for a case class!!!
case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list              //List remains unchanged!!!
  def printElements: String = ""
}

//Non-Empty list:
//  -> Composed of an element (head) and the rest of the list (tail)
//  -> Must be covariant too!!!
//  -> Good use for a case class!!!
case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this)
  def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    //Transform the head and tail:
    //NOTE: Result will have the same number of elements!!!
    //Example: [1,2,3].map(n * 2)
    // -> new Cons(2, [2,3].map(n * 2))
    // -> new Cons(2, new Cons(4, [3].map(n * 2)))
    // -> new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
    // -> new Cons(2, new Cons(4, new Cons(6, Empty)))
    new Cons(transformer.transform(h), t.map(transformer))
  }
  def filter(predicate: MyPredicate[A]): MyList[A] = {
    //Test head and always filter the tail:
    //Example: filter [1,2,3], results in [2]
    // -> [1,2,3].filter(element % 2 == 0)                  //element=1, 1 / 2 (R=1), test fails (else), remove head, filter=[2,3]
    // -> [2,3].filter(element % 2 == 0)                    //element=2, 2 / 2 (R=0), test passes (if), new Cons with head=2, filter=[3]
    // -> new Cons(2, [3].filter(element % 2 == 0)          //element=3: 3 / 2 (R=1), test fails (else), remove head, filter=[2]
    // -> new Cons(2. Empty.filter(element % 2 == 0)        //element=2: 2 / 2 (R=0), test passes (if), new Cons with head=2, filter=[Empty]
    // -> new Cons(2, Empty)                                //result=[2]
    if (predicate.test(h)) new Cons(h, t.filter(predicate)) //Head included
    else t.filter(predicate)                                //Head filtered out (excluded)
  }
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    //Example: [1, 2].flatMap(n => n + 1) results in [1,2, 2,3]
    // -> [1,2] ++ [2].flatMap(n => n + 1)
    // -> [1,2] ++ [2,3] ++ Empty.flatMap(n => n + 1)
    // -> [1,2] ++ [2,3] ++ Empty
    // -> [1,2,2,3]
    transformer.transform(h) ++ t.flatMap(transformer)
  }
  def ++[B >: A](list: MyList[B]): MyList[B] = {
    //Example: [1,2] ++ [3,4,5], results in [1,2,3,4,5]
    // -> new Cons(1, [2] ++ [3,4,5])
    // -> new Cons(1, new Cons(2, [Empty] ++ [3,4,5]))
    // -> new Cons(1, new Cons(2, [3,4,5])) which is just...
    // -> new Cons(1, new Cons(2, new Const(3, new Const(4, new Const(5)))))
    new Cons(h, t ++ list)
  }
  def printElements: String = {
    if (t.isEmpty) "" + h
    //else h + " " + t.printElements              //This only works in Scala 2
    else s"$h ${t.printElements}"                 //For Scala 3, use a String interpolator
  }
}

//OO Exercises Enhancements:
trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}

//Scala application:
object ListTest extends App {
  //Initial Requirements:
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(listOfIntegers.head)                    //1
  println(listOfIntegers.tail.head)               //2
  println(listOfIntegers.add(4).head)             //4 - Element gets added to head
  println(listOfIntegers.isEmpty)                 //false
  println(listOfIntegers.toString)                //[1 2 3]

  val listOfStrings: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))
  println(listOfStrings.toString)                 //"Hello Scala"

  //Empty should be a proper value for a list of Any type:
  val listOfIntegersEmpty: MyList[Int] = Empty
  val listOfStringsEmpty: MyList[String] = Empty

  //OO Exercise Example 1:
  //[1, 2, 3].map(n * 2) ==> [2, 4, 6]
  println(listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(element: Int): Int = element * 2
  }))

  //OO Exercise Example 2:
  //[1, 2, 3].filter(n % 2 == 0) ==> [2]
  //NOTE: Modulus operator finds the remainder after division of one number by another
  println(listOfIntegers.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element % 2 == 0
  }).toString)

  //OO Exercise Example 3:
  //[1, 2, 3].flatMap(n => n + 1) ==> [1,2, 2,3, 3,4]
  val testConcatenation: MyList[Int] = new Cons(4, new Cons(5, Empty))
  println(listOfIntegers ++ testConcatenation)    //[1,2,3,4,5]
  println(listOfIntegers.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(element: Int): MyList[Int] = new Cons(element, new Cons(element + 1, Empty))
  }))

  //Case Classes Enhancement:
  val listOfIntegersClone: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(listOfIntegers == listOfIntegersClone)  //Case class equals() returns true
}
