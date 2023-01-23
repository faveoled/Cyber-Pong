package tutorial.webapp

import org.scalajs.dom
import org.scalajs.dom.document

object TutorialApp {
  def main(args: Array[String]): Unit = {
    CyberPongMain.setup()
    document.addEventListener("DOMContentLoaded", { (e: dom.Event) =>
      CyberPongMain.startMovingBlocks()
    })
  }
}
