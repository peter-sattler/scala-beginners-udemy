package net.sattler22.lectures.part2oop

/**
 * Enums Takeaways:
 * <ol>
 *   <li>A full fledged data type (allows fields, methods, etc.)</li>
 *   <li>Constructor arguments are supported, though the syntax is a bit verbose</li>
 *   <li>Companion objects are supported too</li>
 *   <li>Only Scala 3 has first-class support for Enums!!!</li>
 * </ol>
 */
object Enums extends App {

  //Basic functionality:
  enum Permissions {
    case READ, WRITE, EXECUTE, NONE
    def openDocument(): Unit = {
      if (this == READ) println("Opening document")
      else println("Cannot open document")
    }
  }
  val perms1: Permissions = Permissions.READ
  perms1.openDocument()

  //Constructor arguments:
  enum PermissionsWithBits(bits: Int) {
    case READ extends PermissionsWithBits(4)               //100
    case WRITE extends PermissionsWithBits(2)              //010
    case EXECUTE extends PermissionsWithBits(1)            //001
    case NONE extends PermissionsWithBits(0)               //000
  }

  //Companion objects are supported:
  object PermissionsWithBits {
    def fromBits(bits: Int): PermissionsWithBits = PermissionsWithBits.NONE
  }

  //Standard API:
  val perms1Ordinal = perms1.ordinal                       //Get zero-based enum index
  val allPerms = PermissionsWithBits.values                //Array of all possible values
  val readPerm: Permissions = Permissions.valueOf("READ")  //Create an instance from a string
  println(perms1Ordinal)                                   //0
  println(readPerm)                                        //Permissions.READ
}
