package tutorial.webapp

import utest._

import scala.scalajs.js

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.ext._

object TutorialTest extends TestSuite {

  def tests = Tests {
    test("HelloWorld") {
      assert(1 == 1)
    }
  }
}
