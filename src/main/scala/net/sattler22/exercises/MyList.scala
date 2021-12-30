package net.sattler22.exercises

/**
 * Implementing Our Own Collection -- VERY CHALLENGING!!!
 *
 * <h3>Initial Requirements</h3>
 * <ol>
 *   <li>Describes an immutable list of any type</li>
 *   <li>Singly linked list</li>
 *   <li>Methods:</li>
 *   <ul>
 *     <li>head = first element of the list</li>
 *     <li>tail = remainder of the list</li>
 *     <li>isEmpty = is this list empty</li>
 *     <li>add(int) => new list with this element added</li>
 *     <li>toString => a string representation of the list</li>
 *   </ul>
 * </ol>
 *
 * <h3>Object-Oriented Exercises: Expanding Our Collection Enhancements</h3>
 * <ol>
 *   <li>Create a generic trait MyPredicate(-T):</li>
 *   <ul>
 *     <li>tests if T passes a condition</li>
 *     <li>test(T): Boolean</li>
 *     <li>HINT: Define MyPredicate contravariant in the type T (otherwise code will not compile)</li>
 *   </ul>
 *   <li>Create a generic trait MyTransformer(-A, B):</li>
 *   <ul>
 *     <li>converts A to B</li>
 *     <li>transform(A): B</li>
 *     <li>HINT: Define MyTransformer contravariant in the type A (otherwise code will not compile)</li>
 *   </ul>
 *   <li>Expand MyList functionality:</li>
 *   <ul>
 *     <li>map(transformer) which returns MyList of different type</li>
 *     <li>filter(predicate) which returns MyList</li>
 *     <li>flatMap(transformer from A to MyList[B] which returns MyList[B]</li>
 *   </ul>
 *   <li>Concrete classes:</li>
 *   <ul>
 *     <li>class EvenPredicate extends MyPredicate[Int]</li>
 *     <ul>
 *       <li>test will return true if T is even</li>
 *     </ul>
 *     <li>class StringToIntTransformer extends MyTransformer(String, Int)</li>
 *     <ul>
 *       <li>transform method convert the string to an integer</li>
 *     </ul>
 *   </ul>
 *   <li>Examples:</li>
 *   <ul>
 *     <li>[1, 2, 3].map(n * 2) results in [2, 4, 6]</li>
 *     <li>[1, 2, 3].filter(n % 2 == 0) results in [2]</li>
 *     <li>[1, 2, 3].flatMap(n => n + 1) results in [1,2, 2,3, 3,4]</li>
 *   </ul>
 * </ol>
 */
abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]      //Use return type [B >: A] to avoid contravariant error message
  def map[B](transformer: MyTransformer[A, B]): MyList[B]              //OO Exercises Enhancements
  def filter(predicate: MyPredicate[A]): MyList[A]                     //OO Exercises Enhancements
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]  //OO Exercises Enhancements
  def ++[B >: A](list: MyList[B]): MyList[B]  //Concatenation
  def printElements: String  //Polymorphic call - Delegate implementation to sub-class
  override def toString: String = s"[$printElements]"
}

//Empty List:
//Use object since its a singleton and only ONE empty instance is needed
//NOTE: Three question marks will throw a "not implemented" error
//NOTE: The Nothing type is ALWAYS a proper substitute for AnyType
object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list  //List remains unchanged!!!
  def printElements: String = ""
}

//Non-Empty list:
//Must be covariant too!!!
class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this)
  def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    //Transform the head and tail:
    //NOTE: Result will have the same number of elements!!!
    new Cons(transformer.transform(h), t.map(transformer))
  }
  def filter(predicate: MyPredicate[A]): MyList[A] = {
    //Test head and always filter the tail:
    //Example: filter [1,2,3], results in [2]
    // -> [1,2,3].filter(element % 2 == 0)            //element=1, 1 / 2 (R=1), test fails (else), remove head, filter=[2,3]
    // -> [2,3].filter(element % 2 == 0)              //element=2, 2 / 2 (R=0), test passes (if), new Cons with head=2, filter=[3]
    // -> new Cons(2, [3].filter(element % 2 == 0)    //element=3: 3 / 2 (R=1), test fails (else), remove head, filter=[2]
    // -> new Cons(2. Empty.filter(element % 2 == 0)  //element=2: 2 / 2 (R=0), test passes (if), new Cons with head=2, filter=[Empty]
    // -> new Cons(2, Empty)                          //result=[2]
    if (predicate.test(h)) new Cons(h, t.filter(predicate)) //head included
    else t.filter(predicate)                                //head filtered out (excluded)
  }
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    //Example: [1, 2].flatMap(n => n + 1) results in [1,2, 2,3]
    // -> [1,2] ++ [2].flatMap(n => n + 1)
    // -> [1,2] ++ [2,3] ++ Empty.flatMap(n => n + 1)
    // -> [1,2] ++ [2,3]
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
    else h + " " + t.printElements  //Scala 2 only!!!
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
  println(listOfIntegers.head)         //1
  println(listOfIntegers.tail.head)    //2
  println(listOfIntegers.add(4).head)  //4 - Element gets added to head
  println(listOfIntegers.isEmpty)      //false
  println(listOfIntegers.toString)     //[1 2 3]

  val listOfStrings: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))
  println(listOfStrings.toString)      //Hello Scala

  //Empty should be a proper value for a list of any type:
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
  println(listOfIntegers ++ testConcatenation)  //[1,2,3,4,5]
  println(listOfIntegers.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(element: Int): MyList[Int] = new Cons(element, new Cons(element + 1, Empty))
  }))
}
