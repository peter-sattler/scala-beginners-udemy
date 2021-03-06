package net.sattler22.exercises

/**
 * Implementing Our Own Collection
 *
 * <h3>Section 3: Initial Requirements</h3>
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
 * <h3>Section 3: Object-Oriented Exercises: Expanding Our Collection Enhancements</h3>
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
 *
 * <h3>Section 4: What's a Function Exercise</h3>
 * <ol>
 *   <li>Higher-order functions:</li>
 *   <ul>
 *     <li>Replace the <i>MyPredicate(-T)</i> trait with it's function type <i>T => Boolean</i></li>
 *     <li>Replace the <i>MyTransformer[-A, B]</i> trait with it's function type <i>A => B</i></li>
 *   </ul>
 * </ol>
 *
 * <h3>Section 4: Anonymous Functions (Lambdas) Exercise</h3>
 * <ol>
 *   <li>Replace all <i>FunctionX</i> calls with lambdas</li>
 * </ol>
 *
 * <h3>Section 4: Higher Order Functions (HOFs) and Curries Exercise</h3>
 * <ol>
 *   <li>New methods:</li>
 *   <ul>
 *     <li><i>forEach</i> which receives a function <i>A => Unit</i> and applies it to every element in the list</li>
 *     <li><i>sort</i> which receives a comparator <i>(A, A) => Int</i> (-1 if less than, zero if equal and +1 if greater than) and returns another <i>MyList[A]</i></li>
 *     <li><i>zipWith</i> which receives another list and a zip function <i>(A, A) => B</i> (two elements to a new one) and returns <i>myList[B]</i></li>
 *     <li><i>fold</i> which receives a start value and a function as separate parameter lists (curried) and returns a value</li>
 *   </ul>
 * </ol>
 *
 * <h3>Section 4: Map, Flat Map, Filter and For-Comprehensions</h3>
 * <ol>
 *   <li>Check for-comprehensions support</li>
 *   <li>Since the compiler rewrites them into chains of <i>map</i>, <i>flatMap</i> and <i>filter</i>, they must have these signatures:</li>
 *   <ul>
 *     <li><i>map(transformer: A => B)</i> and returns MyList[B]</li>
 *     <li><i>flatMap(transformer: A => MyList[B])</i> and returns MyList[B]</li>
 *     <li><i>filter(predicate: A => Boolean)</i> and returns MyList[A]</li>
 *   </ul>
 * </ol>
 */
abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]                               //Use return type [B >: A] to avoid contravariant error message
  def map[B](transformer: A => B): MyList[B]                           //Higher-order functions exercise
  def filter(predicate: A => Boolean): MyList[A]                       //Higher-order functions exercise
  def flatMap[B](transformer: A => MyList[B]): MyList[B]               //Higher-order functions exercise
  def forEach(fx: A => Unit): Unit                                     //HOFs and curries exercise
  def sort(comparator: (A, A) => Int): MyList[A]                       //HOFs and curries exercise
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]      //HOFs and curries exercise
  def fold[B](start: B)(operator: (B, A) => B): B                      //HOFs and curries exercise
  def ++[B >: A](list: MyList[B]): MyList[B]                           //Concatenation
  def printElements: String                                            //Polymorphic call which delegates implementation to a sub-class
  override def toString: String = s"[$printElements]"                  //Override scala.AnyRef class
}

//Empty List:
//  -> Use object since its a singleton and only ONE empty instance is needed
//  -> Three question marks will throw a "not implemented" error
//  -> The scala.Nothing type is ALWAYS a proper substitute for scala.Any type
//  -> Good use for a case object!!!
case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = Cons(element, Empty)
  def map[B](transformer: Nothing => B): MyList[B] = Empty
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty
  def forEach(fx: Nothing => Unit): Unit = ()                          //Return unit value
  def sort(comparator: (Nothing, Nothing) => Int): MyList[Nothing] = Empty
  def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] = {
    if(!list.isEmpty) throw new RuntimeException("Lists must have the same number of elements")
    else Empty
  }
  def fold[B](start: B)(operator: (B, Nothing) => B): B = start
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
  def add[B >: A](element: B): MyList[B] = Cons(element, this)
  def map[B](transformer: A => B): MyList[B] = {
    //Transform the head and tail:
    //NOTE: Result will have the same number of elements!!!
    //Example: [1,2,3].map(n * 2)
    // -> Cons(2, [2,3].map(n * 2))
    // -> Cons(2, Cons(4, [3].map(n * 2)))
    // -> Cons(2, Cons(4, Cons(6, Empty.map(n * 2))))
    // -> Cons(2, Cons(4, Cons(6, Empty)))
    Cons(transformer(h), t.map(transformer))
  }
  def filter(predicate: A => Boolean): MyList[A] = {
    //Test head and always filter the tail:
    //Example: filter [1,2,3], results in [2]
    // -> [1,2,3].filter(element % 2 == 0)                  //element=1, 1 / 2 (R=1), test fails (else), remove head, filter=[2,3]
    // -> [2,3].filter(element % 2 == 0)                    //element=2, 2 / 2 (R=0), test passes (if), Cons with head=2, filter=[3]
    // -> Cons(2, [3].filter(element % 2 == 0)              //element=3: 3 / 2 (R=1), test fails (else), remove head, filter=[2]
    // -> Cons(2. Empty.filter(element % 2 == 0)            //element=2: 2 / 2 (R=0), test passes (if), Cons with head=2, filter=[Empty]
    // -> Cons(2, Empty)                                    //result=[2]
    if (predicate(h)) Cons(h, t.filter(predicate))          //Head included
    else t.filter(predicate)                                //Head filtered out (excluded)
  }
  def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    //Example: [1, 2].flatMap(n => n + 1) results in [1,2, 2,3]
    // -> [1,2] ++ [2].flatMap(n => n + 1)
    // -> [1,2] ++ [2,3] ++ Empty.flatMap(n => n + 1)
    // -> [1,2] ++ [2,3] ++ Empty
    // -> [1,2,2,3]
    transformer(h) ++ t.flatMap(transformer)
  }
  def forEach(fx: A => Unit): Unit = {
    fx(h)
    tail.forEach(fx)
  }
  def sort(comparator: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] = {                   //Insertion sort
      if (sortedList.isEmpty) Cons(x, Empty)                                 //Return only one element
      else if (comparator(x, sortedList.head) <= 0) Cons(x, sortedList)      //New smallest element is x
      else Cons(sortedList.head, insert(x, sortedList.tail))                 //New smallest element is the head
    }
    val sortedTail = tail.sort(comparator)
    insert(head, sortedTail)
  }
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] = {
    //Example: [4,5].zipWith(["Hello","Scala", _ + "-" + _)
    // -> ["4-Hello", "5-Scala"]
    if(list.isEmpty) throw new RuntimeException("Lists must have the same number of elements")
    else Cons(zip(head, list.head), tail.zipWith(list.tail, zip))
  }
  //Example: [1,2,3].fold(0)(+)
  // -> [2,3].fold(1)(+)
  // -> [3].fold(3)(+)
  // -> [].fold(6)(+)
  // -> 6
  def fold[B](start: B)(operator: (B, A) => B): B = {
    tail.fold(operator(start, head))(operator)              //Compose start value with head, then do the rest
  }
  def ++[B >: A](list: MyList[B]): MyList[B] = {
    //Example: [1,2] ++ [3,4,5], results in [1,2,3,4,5]
    // -> Cons(1, [2] ++ [3,4,5])
    // -> Cons(1, Cons(2, [Empty] ++ [3,4,5]))
    // -> Cons(1, Cons(2, [3,4,5])) which is just...
    // -> Cons(1, Cons(2, Const(3, Const(4, Const(5)))))
    Cons(h, t ++ list)
  }
  def printElements: String = {
    if (t.isEmpty) "" + h
    //else h + " " + t.printElements                        //This only works in Scala 2
    else s"$h ${t.printElements}"                           //For Scala 3, use a String interpolator
  }
}

//Scala application:
object ListTest extends App {
  //Initial Requirements:
  val listOfIntegers: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  println(listOfIntegers.head)                    //1
  println(listOfIntegers.tail.head)               //2
  println(listOfIntegers.add(4).head)             //4 - Element gets added to head
  println(listOfIntegers.isEmpty)                 //false
  println(listOfIntegers.toString)                //[1 2 3]

  val listOfStrings: MyList[String] = Cons("Hello", Cons("Scala", Empty))
  println(listOfStrings.toString)                 //"Hello Scala"

  //Empty should be a proper value for a list of Any type:
  val listOfIntegersEmpty: MyList[Int] = Empty
  val listOfStringsEmpty: MyList[String] = Empty

  //Concatenation operator:
  val testConcatenation: MyList[Int] = Cons(4, Cons(5, Empty))
  println(listOfIntegers ++ testConcatenation)    //[1,2,3,4,5]

  //OO Exercise Example 1:
  //[1, 2, 3].map(n * 2) ==> [2, 4, 6]
  //println(listOfIntegers.map(new Function1[Int, Int] {
  //  override def apply(element: Int): Int = element * 2
  //}))

  //Lambda Replacement Exercise Example 1:
  //[1, 2, 3].map(n * 2) ==> [2, 4, 6]
  println(listOfIntegers.map(_ * 2))

  //OO Exercise Example 2:
  //[1, 2, 3].filter(n % 2 == 0) ==> [2]
  //NOTE: Modulus operator finds the remainder after division of one number by another
  //println(listOfIntegers.filter(new Function1[Int, Boolean] {
  //  override def apply(element: Int): Boolean = element % 2 == 0
  //}).toString)

  //Lambda Replacement Exercise Example 2:
  //[1, 2, 3].filter(n % 2 == 0) ==> [2]
  println(listOfIntegers.filter(_ % 2 == 0).toString)

  //OO Exercise Example 3:
  //[1, 2, 3].flatMap(n => n + 1) ==> [1,2, 2,3, 3,4]
  //println(listOfIntegers.flatMap(new Function1[Int, MyList[Int]] {
  //  override def apply(element: Int): MyList[Int] = Cons(element, Cons(element + 1, Empty))
  //}))

  //Lambda Replacement Exercise Example 3:
  //[1, 2, 3].flatMap(n => n + 1) ==> [1,2, 2,3, 3,4]
  println(listOfIntegers.flatMap(element => Cons[Int](element, Cons[Int](element + 1, Empty))))

  //Case Classes Enhancement:
  val listOfIntegersClone: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  println(listOfIntegers == listOfIntegersClone)  //Case class equals() returns true

  //HOFs and Curries Exercise:
  listOfIntegers.forEach(x => println(x))         //Lambda
  listOfIntegers.forEach(println)                 //Short-hand notation
  println(listOfIntegers.sort((x, y) => y - x))   //Reverse the elements
  println(testConcatenation.zipWith[String, String](listOfStrings, _ + "-" + _))
  println(listOfIntegers.fold(0)(_ + _))          //Often called reduce

  //Map, Flat Map, Filter and For-Comprehensions Exercise:
  val combinations = for {
    num <- listOfIntegers
    string <- listOfStrings
  } yield num + "-" + string
  println(combinations)
}
