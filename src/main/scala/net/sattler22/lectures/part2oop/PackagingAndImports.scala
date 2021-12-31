package net.sattler22.lectures.part2oop

import net.sattler22.lectures.part2oop.OOBasics.Writer

/**
 * Packaging and Imports Takeaways (mostly notes on this one):
 * 
 * <ol>
 *   <li>Package is a group of definitions under the same name</li>
 *   <li>Package definition use:</li>
 *   <ul>
 *     <li>In the same package or</li>
 *     <li>Import the package or</li>
 *     <li>Use the fully-qualified name</li>
 *   </ul>
 *   <li>Packages should mirror the file/folder structure (best practice)</li>
 *   <li>Package Objects:</li>
 *   <ul>
 *     <li>Holds stand-alone methods/constants</li>
 *     <li>Only one per package is allowed</li>
 *     <li>Its name must match the package name</li>
 *     <li>The file must be named "package.scala"</li>
 *   </ul>
 *   <li>Imports:</li>
 *   <ul>
 *     <li>Scala provides name aliasing to differentiate between two classes with the same name</li>
 *     <li>Use underscore (_) to import and entire package (not recommended)</li>
 *     <li>Default imports (get included automatically):</li>
 *     <ul>
 *       <li>java.lang - String, Exception, etc.</li>
 *       <li>scala - Int, Nothing, Function, etc.</li>
 *       <li>scala.Predef - println, ???, etc.</li>
 *     </ul>
 *   </ul>
 * </ol>
 */
object PackagingAndImports extends App {

  //Package members are accessible by their simple name, BUT others need to be imported (or use the fully qualified name):
  val writer = new Writer("Pete", "Sattler", 1964)

  //package object - holds stand-alone methods/constants
  sayHello        //From package object
}
