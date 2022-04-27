package net.sattler22.lectures.part3fp

import java.util.NoSuchElementException
import scala.:+
import scala.annotation.tailrec

/**
 * Tuples and Maps Takeaways:
 * <ol>
 *   <li>Tuples:</li>
 *   <ul>
 *     <li>Are finite ordered lists</li>
 *     <li>Can group up to 22 elements of different types because they are used in conjunction with function types</li>
 *     <li>Use <i>_n</i> to retrieve elements</li>
 *     <li>Use <i>copy</i> to create new tuples</li>
 *     <li>Swap elements via <i>swap</i></li>
 *   </ul>
 *   <li>Maps:</li>
 *   <ul>
 *     <li>Associates keys to values</li>
 *     <li>Functionals include <i>filterKeys</i> and <i>mapValues</i></li>
 *     <li>The <i>map</i>, <i>flatMap</i> and <i>filter</i> functions operate on pairings (or tuples)</li>
 *     <li>Can be converted to and from lists</li>
 *     <li>The <i>groupBy</i> function is very useful</li>
 *   </ul>
 * </ol>
 */
object TuplesAndMaps extends App {

  //Tuples:
  val tuple = Tuple2(2, "Hello Scala")
  println(s"Tuple: $tuple")                                                                          //Tuple: (2,Hello Scala)
  println(s"Tuple sugar: ${(2, "Hello Scala")}")                                                     //Syntactic sugar: Tuple2(2, "Hello Scala")
  println(s"Tuple first element: [${tuple._1}]")                                                     //[2]
  println(s"Tuple copy: ${tuple.copy(_2 = "Replace")}")                                              //(2,Replace)
  println(s"Tuple swap: ${tuple.swap}")                                                              //(Hello Scala,2)

  //Maps:
  val map: Map[String,Int] = Map()
  val phoneBook = Map(("Fred", 5555), "Pete" -> 6789).withDefaultValue(-1)                           //Syntactic sugar: ("Pete", 6789)
  println(s"Phone book #1: $phoneBook")                                                              //Map(Fred -> 5555, Pete -> 6789)
  println(s"Phone book contains: [${phoneBook.contains("Pete")}]")                                   //[true]
  println(s"Phone book get: [${phoneBook("Pete")}]")                                                 //[6789]
  println(s"Phone book get not found: [${phoneBook("Mary")}]")                                       //[-1]
  val phoneBook2 = phoneBook + ("Mary" -> 4567)                                                      //Maps are immutable
  println(s"Phone book #2: $phoneBook2")                                                             //Map(Fred -> 5555, Pete -> 6789, Mary -> 4567)

  //Map functionals (map, flatMap and filter) receive a pairing (or tuple) parameter:
  println(s"Phone book map: ${phoneBook.map(pair => pair._1.toLowerCase() -> pair._2)}")             //Map(fred -> 5555, pete -> 6789)
  println(s"Phone book filter keys: ${phoneBook.view.filterKeys(x => x.startsWith("F")).toMap}")     //Map(Fred -> 5555)
  println(s"Phone book map values: ${phoneBook.view.mapValues(num => "245-" + num).toMap}")          //Map(Fred -> 245-5555, Pete -> 245-6789)
  println(s"Phone book list conversion: ${phoneBook.toList}")                                        //List((Fred,5555), (Pete,6789))
  println(s"List to map conversion: ${List(("Pete", 6789)).toMap}")                                  //Map(Pete -> 6789)
  val names = List("Bob", "Bill", "Pat", "Pete", "Lou")
  println(s"Names group by: ${names.groupBy(name => name.charAt(0))}")                               //HashMap(L -> List(Lou), B -> List(Bob, Bill), P -> List(Pat, Pete))

  /** Exercises */
  //What happens when the map function maps two original entries to the same key:
  //CAREFUL: When mapping keys, make sure they do not overlap!!!
  val phoneBook3 = Map(("Jim", 123), "JIM" -> 456)
  println(s"Phone book #3: $phoneBook3")                                                             //Map(Jim -> 123, JIM -> 456)
  println(s"Phone book #3 lower case: ${phoneBook3.map(pair => pair._1.toLowerCase() -> pair._2)}")  //Map(jim -> 456)

  //Create an overly simplified social network based on maps:
  //  -> Each person has a name
  //  -> The network will keep an association between each name and a list of friends
  //  -> Must be able to:
  //    -> Add a person to the network
  //    -> Remove a person from the network
  //    -> Friend (mutual)
  //    -> Unfriend (mutual)
  //  -> Must be able to determine:
  //    -> How many friends does a person have?
  //    -> Which person has the most friends?
  //    -> How many people have no friends (just joined the network)?
  //    -> Is there a social connection between 2 people (direct or not)?
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    val friends1: Set[String] = network(person1)
    val friends2: Set[String] = network(person2)
    network + (person1 -> (friends1 + person2)) + (person2 -> (friends2 + person1))
  }

  def unfriend(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    val friends1: Set[String] = network(person1)
    val friends2: Set[String] = network(person2)
    network + (person1 -> (friends1 - person2)) + (person2 -> (friends2 - person1))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    @tailrec
    def removeFriend(friends: Set[String], friendNetwork: Map[String, Set[String]]): Map[String, Set[String]] = {
      if (friends.isEmpty) friendNetwork
      //Unfriend the first friend, then remove the remaining friends:
      else removeFriend(friends.tail, unfriend(friendNetwork, person, friends.head))
    }
    //Remove all friends before removing the person so there are no dangling references:
    val unfriended = removeFriend(network(person), network)
    unfriended - person
  }
  //Find the number of friends a person has:
  def nbrFriends(network: Map[String, Set[String]], person: String): Int = {
    if (!network.contains(person)) 0
    else network(person).size
  }

  //Find the person with the most friends:
  //NOTE: Person is [_1] and their list of friends is [_2]!!!
  def mostFriends(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1
  }

  //Find those without friends (just joined the network):
  def nbrNoFriends(network: Map[String, Set[String]]): Int = {
    network.count(pair => pair._2.isEmpty)
  }

  //Check for a social connection between 2 people (direct and indirect):
  //NOTE: Uses the Breadth First Search (BFS) algorithm!!!
  def hasConnection(network: Map[String, Set[String]], person1: String, person2: String): Boolean = {
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      //Find the target in discovered people having already considered the considered people:
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }
    bfs(person2, Set(), network(person1) + person1)
  }

  //Social Network #1:
  val empty: Map[String, Set[String]] = Map()
  val network1 = add(add(empty, "Bob"), "Mary")
  println(s"Social network #1: $network1")                                                           //Map(Bob -> Set(), Mary -> Set())
  println(s"Bob and Mary are friends: ${friend(network1, "Bob", "Mary")}")                           //Map(Bob -> Set(Mary), Mary -> Set(Bob))
  println(s"Bob and Mary no longer like each other: ${unfriend(network1, "Bob", "Mary")}")           //Map(Bob -> Set(), Mary -> Set())
  println(s"Bob removes himself: ${remove(network1, "Bob")}")                                        //Map(Mary -> Set())

  //Social Network #2:
  val network2a = add(add(add(add(empty, "Bob"), "Mary"), "Jim"), "Pete")
  val network2b = friend(network2a, "Bob", "Jim")
  val network2 = friend(network2b, "Bob", "Mary")
  println(s"Social network #2: $network2")                                                           //Map(Bob -> Set(Jim, Mary), Mary -> Set(Bob), Jim -> Set(Bob), Pete -> Set())
  println(s"Bob has [${nbrFriends(network2, "Bob")}] friends")                                       //[2]
  println(s"The Most Friends award goes to: [${mostFriends(network2)}]")                             //[Bob]
  println(s"Total without any friends: [${nbrNoFriends(network2)}]")                                 //[1]
  println(s"Jim has a connection with Mary: [${hasConnection(network2, "Jim", "Mary")}]")            //[true]
  println(s"Pete has a connection with Mary: [${hasConnection(network2, "Pete", "Mary")}]")          //[false]
}
