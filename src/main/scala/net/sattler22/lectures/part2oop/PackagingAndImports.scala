package net.sattler22.lectures.part2oop

import net.sattler22.playground.Cinderella

import java.util.Date
import java.sql.{Date => SqlDate}

/**
 * Packaging and Imports Takeaways:
 * 
 * <ol>
 *   <li>A package is a group of definitions under the same name</li>
 *   <li>Package definitions are not expressions</li>
 *   <li>Package members <strong>within</strong> the package are visible by their simple name</li>
 *   <li>Package members <strong>outside</strong> of the package must be imported from the proper package or use their fully-qualified name to be visible</li>
 *   <li>Packages are in a hierarchy which should match the folder structure (best practice)</li>
 *   <li>Package Objects:</li>
 *   <ul>
 *     <li>It holds stand-alone methods and constants</li>
 *     <li>Only one <i>package object</i> per package is allowed</li>
 *     <li>The name must match the package that it resides in</li>
 *     <li>The file must be named <i>package.scala</i></li>
 *     <li>They are visible by their simple name</li>
 *   </ul>
 *   <li>Scala Import Specific Features:</li>
 *   <ul>
 *     <li>Scala groups together multiple classes from the same package into one <i>import</i> statement using curly braces</li>
 *     <li>Use an underscore (_) to import the entire package (not recommended)</li>
 *     <li>Scala provides name aliasing (=>) to differentiate between two classes with the same name</li>
 *     <li>Default imports get included automatically:</li>
 *     <ul>
 *       <li><i>java.lang</i> - <i>String</i>, <i>Exception</i>, etc.</li>
 *       <li><i>scala</i> - <i>Int</i>, <i>Nothing</i>, <i>Function</i>, etc.</li>
 *       <li><i>scala.Predef</i> - <i>println</i>, <i>???</i>, etc.</li>
 *     </ul>
 *   </ul>
 * </ol>
 */
object PackagingAndImports extends App {

  val writer = new Writer("Pete", "Sattler", 1964)
  println(s"Visible WITHIN the package: $writer")                            //OOBasics object

  val princess = new Cinderella
  println(s"Visible OUTSIDE of the package after proper import: $princess")  //From playground package

  val ourHero = new net.sattler22.playground.PrinceCharming
  println(s"Visible OUTSIDE of the package using it's fully-qualified name: $ourHero")

  sayHello                                                                   //From package object
  println(s"Daniel the instructor says the speed of light ($SPEED_OF_LIGHT) is measured in meters per second!!!")

  val date = new Date                                                        //Uses FIRST import!!!
  val sqlDate1 = new java.sql.Date(date.getTime)                             //Fully-qualified
  val sqlDate2 = new SqlDate(date.getTime)                                   //Uses the alias
  println(s"Date [$date] and SQL Date [$sqlDate1] can be differentiated using the fully-qualified package name")
  println(s"Date [$date] and SQL Date [$sqlDate2] can be also be differentiated using aliasing")
}
