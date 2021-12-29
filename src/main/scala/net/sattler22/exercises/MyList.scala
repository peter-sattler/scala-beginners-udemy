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
abstract class MyList {
  def head: Int
  def tail: MyList
  def isEmpty: Boolean
  def add(element: Int): MyList
  def printElements: String  //Polymorphic call - Delegate implementation to sub-class
  override def toString: String = s"[${printElements}]"
}

//Empty List:
//Use object since its a singleton and only ONE empty instance is needed
//Three question marks will throw a "not implemented" error
object Empty extends MyList {
  def head: Int = throw new NoSuchElementException
  def tail: MyList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MyList = new Cons(element, Empty)
  def printElements: String = ""
}

//Non-Empty list:
class Cons(h: Int, t: MyList) extends MyList {
  def head: Int = h
  def tail: MyList = t
  def isEmpty: Boolean = false
  def add(element: Int): MyList = new Cons(element, this)
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements  //Scala 2 only!!!
  }
}

//Scala application:
object ListTest extends App {
  var list = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(list.head)         //1
  println(list.tail.head)    //2
  println(list.add(4).head)  //4 - Element gets added to head
  println(list.isEmpty)      //false
  println(list.toString)
}
