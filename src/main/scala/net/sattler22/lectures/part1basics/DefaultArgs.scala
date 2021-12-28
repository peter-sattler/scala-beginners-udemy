package net.sattler22.lectures.part1basics

/**
 * Default and Named Arguments Takeaways:
 * <ol>
 *   <li>99% of the time we call a function with the same parameters</li>
 *   <li>We can supply defaults for each parameter</li>
 *   <li>BUT we cannot OMIT leading parameters</li>
 *   <li>Named parameters allow us to call the function specifying the parameters in any order that we choose</li>
 * </ol>
 */
object DefaultArgs extends App {

  /** Default and Named Arguments */
  def factorialTailRec(n: Int, accumulator: Int = 1): Int = {
    if (n <= 1) accumulator
    else factorialTailRec(n -1, n * accumulator)
  }
  println(factorialTailRec(10))                 //Default accumulator is 1
  println(factorialTailRec(10, 2))  //Overrides default

  def savePicture(format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit = println("Saving picture...")
  savePicture("jpg", 800, 600)  //Works fine
//  savePicture(800, 600)           //FAIL - compiler assumes 800 is first parameter

  /*
   * We can fix this by either:
   * 1. pass in every leading argument
   * 2. Name the arguments
   */
  savePicture(height = 600, width = 800)  //Named argument can be in any order
}
