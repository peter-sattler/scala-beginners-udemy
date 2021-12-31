package net.sattler22.lectures.part2oop

/**
 * Enums Takeaways:
 * <ol>
 *   <li>Enums are only supported in Scala 3!!!</li>
 *   <li>A full fledged data type (methods allowed, etc.)</li>
 *   <li>Constructor arguments are supported, though the syntax is a bit verbose</li>
 *   <li>Companion objects are supported too</li>
 *   <li>WARNING: Use with caution - code was not actually compiled since it is Scala 3</li>
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
    case READ extends PermissionsWithBits(4)              //100
    case WRITE extends PermissionsWithBits(2)             //010
    case EXECUTE extends PermissionsWithBits(1)           //001
    case NONE extends PermissionsWithBits(0)              //000
  }

  //Enum companion objects are supported:
  object PermissionsWithBits {
    def fromBits(bits: Int): PermissionsWithBits = PermissionsWithBits.NONE
  }

  //Ordinal is the zero-based enum index:
  val perms1Ordinal = perms1.ordinal
  println(perms1Ordinal)                                   //READ ordinal = 0

  //Array of all possible values of the enum
  val allPerms = PermissionsWithBits.values()

  //Create an enum instance from a string:
  val readPerm: Permissions = Permissions.valueOf("READ")  //Permissions.READ
}
