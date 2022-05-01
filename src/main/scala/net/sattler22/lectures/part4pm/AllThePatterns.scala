package net.sattler22.lectures.part4pm

import net.sattler22.exercises.{Cons, Empty, MyList}

/**
 * All the Patterns Takeaways:
 * <ol>
 *   <li>Match constants (switch on steroids)</li>
 *   <li>Match anything using a wildcard or a variable</li>
 *   <li>Match on tuples (can be nested)</li>
 *   <li>Match on case classes (constructor pattern, can be nested)</li>
 *   <li>Match on list patterns</li>
 *   <li>Match on type specifiers</li>
 *   <li>Match using name binding (can be nested)</li>
 *   <li>Match using compound patterns (multi-patterns, chained via pipe operator)</li>
 *   <li>Match using if guards</li>
 *   <li>WARNING: Be aware of JVM type erasure!!!</li>
 * </ol>
 */
object AllThePatterns extends App {

  //1. Match constants (switch on steroids):
  val x: Any = "Scala"
  val constantMatch = x match {
    case 1 => "Matched number"
    case "Scala" => "Matched Scala"
    case true => "Matched boolean"
    case AllThePatterns => "Matched singleton object"
  }
  println(s"Match a constant (switch on steroids): [$constantMatch]")  //[Matched Scala]

  //2. Match anything using a wildcard or a variable:
  val matchAnything = x match {
    case _ => "Matched wildcard"
  }
  val matchVariable = x match {
    case something => s"Matched $something"
  }
  println(s"Match anything (wildcard): [$matchAnything]")              //[Matched wildcard]
  println(s"Match a variable: [$matchVariable]")                       //[Matched Scala]

  //3. Match on tuples (can be nested):
  val tuple1 = (1, 2)
  val matchTuple = tuple1 match {
    case (1, 1) => "Matched tuple of literals"
    case (something, 2) => s"Matched tuple composed of nested pattern $something"
  }
  println(s"Match a tuple: [$matchTuple]")                             //[Matched tuple composed of nested pattern 1]

  val nestedTuple = (1, (2, 3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, value)) => s"Matched nested tuple using nested pattern $value"
  }
  println(s"Match a nested tuple: [$matchNestedTuple]")                //[Matched nested tuple using nested pattern 3]

  //4. Match on case classes (constructor pattern, can be nested):
  val list1: MyList[Int] = Cons(1, Cons(2, Cons(3, Cons(4, Empty))))
  val matchMyList = list1 match {
    case Empty => "Matched case class Empty"
    case Cons(head, tail) => s"Matched case class Cons with head $head and tail $tail"
    case Cons(head, Cons(subHead, subTail)) => s"Matched case class Cons with head $head, sub-head $subHead and sub-tail $subTail"
  }
  println(s"Match a case class: [$matchMyList]")                       //[Matched case class Cons with head 1 and tail [2 3 4]]

  //5. Match on list patterns:
  val list2 = List(1,2,3,42)
  val matchStandardList = list2 match {
    case List(1, _, _, _) => "Matched List class standard library extractor"
    case List(1, _*) => "Matched List of arbitrary length"
    case 1 :: List(_) => "Matched List of something using infix pattern #1"
    case List(1,2,3) :+ 42 => "Matched List of something using infix pattern #2"
  }
  println(s"Match a list pattern: [$matchStandardList]")               //[Matched List class standard library extractor]

  //6. Match on type specifiers:
  val matchTypeSpecifier = list2 match {
    case list: List[Int] => "Matched list of integers using explicit type specifier"
  }
  println(s"Match a type specifier: [$matchTypeSpecifier]")            //[Matched list of integers using explicit type specifier]

  //7. Match using name binding (can be nested):
  val matchNameBinding = list1 match {
    case nonEmptyList @ Cons(_, _) => s"Matched using bound name $nonEmptyList"
    case Cons(1, rest @ Cons(2, _)) => s"Matched using bound name inside a nested pattern"
  }
  println(s"Match using name binding: [$matchNameBinding]")            //[Matched using bound name [1 2 3 4]]

  //8. Match using compound patterns (multi-patterns, chained via pipe operator):
  val matchMultiPatterns = list1 match {
    case Empty | Cons(1, _) => "Matched using compound pattern (multi-pattern)"
  }
  println(s"Match using multi-patterns: [$matchMultiPatterns]")        //[Matched using compound pattern (multi-pattern)]

  //9. Match using if guards:
  val matchIfGuard = list1 match {
    case Cons(_, Cons(specialElement, _)) if specialElement %2 == 0 => s"Matched special element $specialElement using if guard"
  }
  println(s"Match using if guard: [$matchIfGuard]")                    //[Matched special element 2 using if guard]

  //WARNING: Be aware of JVM type erasure!!!
  val numbers = List(1,2,3)
  val matchNumbers = numbers match {
    case listOfStrings: List[String] => "Matched a list of strings"
    case listOfIntegers: List[Int] => "Matched a list of integers"
    case _ => //returns an empty string
  }
  println(s"Weird match due to JVM type erasure: [$matchNumbers]")     //[Matched a list of strings]
}
