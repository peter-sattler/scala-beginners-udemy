package net.sattler22.exercises

/**
 * Implementing Our Own Collection -- VERY CHALLENGING!!!
 * <ol>
 *   <li>Describes an immutable list of integers</li>
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
 */
abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String  //Polymorphic call - Delegate implementation to sub-class
  override def toString: String = s"[${printElements}]"
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
  def printElements: String = ""
}

//Non-Empty list:
//Must be covariant too!!!
class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this)
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements  //Scala 2 only!!!
  }
}

//Scala application:
object ListTest extends App {
  var listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(listOfIntegers.head)         //1
  println(listOfIntegers.tail.head)    //2
  println(listOfIntegers.add(4).head)  //4 - Element gets added to head
  println(listOfIntegers.isEmpty)      //false
  println(listOfIntegers.toString)

  var listOfStrings: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))
  println(listOfStrings.toString)      //Hello Scala

  //Empty should be a proper value for a list of any type:
  var listOfIntegers2: MyList[Int] = Empty
  var listOfStrings2: MyList[String] = Empty
}
