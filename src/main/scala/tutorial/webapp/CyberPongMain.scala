package tutorial.webapp

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.html
import org.scalajs.dom.raw.HTMLSelectElement
import org.scalajs.dom.raw.HTMLInputElement
import org.scalajs.dom.raw.HTMLImageElement
import org.scalajs.dom.raw.HTMLElement
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.annotation.JSExport

@JSExportTopLevel("CyberPongMain")
object CyberPongMain {
  
  var X1 = 0
  var X2 = 0
  var Y1 = 0
  var Y2 = 0
  var inter_1: Option[Int] = Option.empty;
  var inter_2: Option[Int] = Option.empty;
  var inter_3: Option[Int] = Option.empty;
  var FirstPlayerPoints = 0;
  var SecondPlayerPoints = 0;
  var DirectionSwitcher = 0;
  var Direction = 2;
  var Up_Down1 = 345;
  var Up_Down2 = 345;
  var X = 13;
  var Y = 10;
  var Random = -999;
  var Strike = -999;
  var StrikeDirection = -999;
  var operators = Array('+', '-', '*');
  var operator = "";
  var operand_1 = 0;
  var operand_2 = 0;
  var correct = 0;
  var answer = -999;
  var autostrike = false;
  var up_1 = false;
  var up_2 = false;
  var down_1 = false;
  var down_2 = false;
  var SR = false;

  def setByName(name: String, input: String): Unit = {
    val elems = document.getElementsByName(name)
    if (elems.size != 1) {
      throw Exception(s"Found ${elems.size} elements for name ${name}, expected 1")
    }
    elems(0).asInstanceOf[HTMLInputElement].value = input
  }

  def getByName(name: String): String = {
    val elems = document.getElementsByName(name)
    if (elems.size != 1) {
      throw Exception(s"Found ${elems.size} elements for name ${name}, expected 1")
    }
    elems(0).asInstanceOf[HTMLInputElement].value
  }

  def setup(): Unit = 
    document.onkeyup = (keyEv: KeyboardEvent) =>
      if (document.getElementById("switch-PC").asInstanceOf[html.Input].checked) {
        keyEv.keyCode match {
          case 87 =>
            up_1 = false
          case 83 =>
            down_1 = false
          case 38 =>
            up_2 = false
          case 40 =>
            down_2 = false
          case _ =>
            println(s"clicked unhandled: ${keyEv.keyCode}")
        }
      }
      if (document.getElementById("switch-MMK").asInstanceOf[html.Input].checked) {
        keyEv.keyCode match {
          case 37 =>
            up_1 = false
          case 39 =>
            down_1 = false
          case 38 =>
            up_2 = false
          case 40 =>
            down_2 = false
          case _ =>
            println(s"code unhandled: ${keyEv.keyCode}")
        }
      }
    document.onkeydown = (keyEv: KeyboardEvent) => {
      if (document.getElementById("switch-PC").asInstanceOf[html.Input].checked) {
        keyEv.keyCode match {
          case 87 => 
            up_1 = true
          case 83 => 
            down_1 = true
          case 38 => 
            up_2 = true
          case 40 => 
            down_2 = true
          case 13 => 
            Start()
          case 32 => 
            Restart()
          case 189 => 
            setByName("result", getByName("result") + "-")
          case _ => 
            println(s"code unhandled: ${keyEv.keyCode}")
        }
      }
      if (document.getElementById("switch-MMK").asInstanceOf[html.Input].checked) {
        keyEv.keyCode match {
          case 37 => 
            up_1 = true
          case 39 => 
            down_1 = true
          case 38 => 
            up_2 = true
          case 40 => 
            down_2 = true
          case 13 => 
            Start()
          case 32 => 
            Restart()
          case 189 => 
            setByName("result", getByName("result") + "-")
          case _ => 
            println(s"code unhandled: ${keyEv.keyCode}")       
        }
      }
      if (keyEv.keyCode >= 48 && keyEv.keyCode <= 57) {
        setByName("result", getByName("result") + (keyEv.keyCode.toChar))
      }
    }
    
  def movingBlocks(): Unit = {
    if (up_1 == true && document.getElementById("FirsBlock").asInstanceOf[HTMLElement].offsetTop > 1) {
      Up_Down1 -= 5;
      document.getElementById("FirsBlock").asInstanceOf[HTMLElement].style.top = Up_Down1 + "px";
    }

    if (down_1 == true && document.getElementById("FirsBlock").asInstanceOf[HTMLElement].offsetTop + 100 < 799) {
      Up_Down1 += 5;
      document.getElementById("FirsBlock").asInstanceOf[HTMLElement].style.top = Up_Down1 + "px";
    }

    if (up_2 == true && document.getElementById("SecondBlock").asInstanceOf[HTMLElement].offsetTop > 1) {
      Up_Down2 -= 5;
      document.getElementById("SecondBlock").asInstanceOf[HTMLElement].style.top = Up_Down2 + "px";
    }

    if (down_2 == true && document.getElementById("SecondBlock").asInstanceOf[HTMLElement].offsetTop + 100 < 799) {
      Up_Down2 += 5;
      document.getElementById("SecondBlock").asInstanceOf[HTMLElement].style.top = Up_Down2 + "px";
    }
  }

  @JSExport("startMovingBlocks")
  def startMovingBlocks(): Unit = {
    inter_3 = Some(dom.window.setInterval(() => movingBlocks(), 1))
  }

  def saveXY(): Unit = {
    X2 = X1;
    Y2 = Y1;
  }

  def question(): Unit = {
    if (correct == 0) {
      val operand_1 = (js.Math.random() * 10).toInt + 1
      val operand_2 = (js.Math.random() * 10).toInt + 1
      val i = ((Math.random() * 10) / 4).toInt
      val operator = operators(i)
      answer = operator match {
        case '+' =>
          operand_1 + operand_2
        case '-' =>
          operand_1 - operand_2
        case '*' =>
          operand_1 * operand_2
        case '/' =>
          operand_1 / operand_2
      }
      setByName("operandi", operand_1 + " " + operator + " " + operand_2 + " = ")
      setByName("result", "")

      correct = 1
    }
    if (correct == 1) {
      if (answer.toString == getByName("result")) {
        autostrike = true;
      }
    }
  }

  def parseInt(input: String): Option[Int] = { 
    val realInput = 
      if (input.endsWith("px")) then input.substring(0, input.length - 2) else input
    realInput.toIntOption
  }

  def Move(): Unit = {
    val ballTop = parseInt(document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top)
    val ballLeft = parseInt(document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left)
    if (ballTop.isDefined && ballTop.get + 50 > 800) {
      autostrike = false;
      DirectionSwitcher = 1;
      if (X1 < X2) {
          Direction = 1;
      }
      if (X1 > X2) {
          Direction = 2;
      }
      Random = (js.Math.random() * 10).toInt / 2;
      saveXY();
    }

    if (ballLeft.isDefined && ballLeft.get + 50 > 1399) {
      autostrike = false;
      correct = 0;
      DirectionSwitcher = 2;
      if (Y1 < Y2) {
        Direction = 1;
      }
      if (Y1 > Y2) {
        Direction = 2;
      }
      Random = (js.Math.random() * 10).toInt / 2;
      saveXY();

      FirstPlayerPoints += 1;
      setByName("value_1", FirstPlayerPoints.toString())
    }

    if (ballTop.isDefined && ballTop.get < 5) {
      autostrike = false;
      DirectionSwitcher = 3;
      if (X1 < X2) {
        Direction = 1;
      }
      if (X1 > X2) {
        Direction = 2;
      }
      Random = (js.Math.random() * 10).toInt / 2;
      saveXY();
    }

    if (ballLeft.isDefined && ballLeft.get < 5) {
      autostrike = false;
      correct = 0;
      DirectionSwitcher = 4;
      if (Y1 < Y2) {
        Direction = 1;
      }
      if (Y1 > Y2) {
        Direction = 2;
      }
      Random = (js.Math.random() * 10).toInt / 2;
      saveXY();

      SecondPlayerPoints += 1;
      setByName("value_2", SecondPlayerPoints.toString())
    }
    
    if (X1 > 1280 && X1 < 1300 && Y1 + 50 > document.getElementById("SecondBlock").asInstanceOf[HTMLElement].offsetTop && Y1 < document.getElementById("SecondBlock").asInstanceOf[HTMLElement].offsetTop + 100) {

      autostrike = false;

      correct = 0;

      DirectionSwitcher = 5;

      Strike = (js.Math.random() * 10).toInt;

      StrikeDirection = (js.Math.random() * 10).toInt;

      if (Strike <= 5) {
        if (Y1 < Y2) {
            Direction = 1;
        }
        if (Y1 > Y2) {
            Direction = 2;
        }
      } else {
        Direction = Strike;
      }

      Random = (Math.random() * 10).toInt / 2;
      saveXY();
    }


    if (X1 > 60 && X1 < 75 && Y1 + 50 > document.getElementById("FirsBlock").asInstanceOf[HTMLElement].offsetTop && Y1 + 25 < document.getElementById("FirsBlock").asInstanceOf[HTMLElement].offsetTop + 100) {

      autostrike = false;

      correct = 0;

      DirectionSwitcher = 6;
      Strike = (js.Math.random() * 10).toInt;

      StrikeDirection = (js.Math.random() * 10).toInt;

      if (Strike <= 5) {
        if (Y1 < Y2) {
            Direction = 1;
        }
        if (Y1 > Y2) {
            Direction = 2;
        }
      } else {
        Direction = Strike;
      }

      Random = (Math.random() * 10).toInt / 2;
      saveXY();
    }

    DirectionSwitcher match {
      case 0 =>
        X1 += X;
        Y1 += Y;
        document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
      case 1 =>
        if (Direction == 1) {
            X1 -= X;
            Y1 -= (Y + Random);
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
        if (Direction == 2) {
            X1 += X;
            Y1 -= (Y + Random);
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
      case 2 =>
        if (Direction == 1) {
            X1 -= X;
            Y1 -= Y + Random;
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
        if (Direction == 2) {
            X1 -= X;
            Y1 += Y + Random;
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
      case 3 =>
        if (Direction == 1) {
            X1 -= X;
            Y1 += (Y + Random);
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
        if (Direction == 2) {
            X1 += X;
            Y1 += Y + Random;
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
      case 4 =>
        if (Direction == 1) {
            X1 += X;
            Y1 -= Y - Random;
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
        if (Direction == 2) {
            X1 += X;
            Y1 += Y + Random;
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
      case 5 =>
        if (Direction == 1) {
            X1 -= X;
            Y1 -= (Y + Random);
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
        if (Direction == 2) {
            X1 -= X;
            Y1 += (Y + Random);
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
        if (Direction > 5) {
            if (X1 < 500 && StrikeDirection % 2 == 0) {
                X1 -= X;
                Y1 += Y - 3;
            }
            if (X1 < 500 && StrikeDirection % 2 == 1) {
                X1 -= X;
                Y1 -= Y - 3;
            }
            if (X1 > 500) {
                X1 -= X;
            }
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
      case 6 =>
        if (Direction == 1) {
            X1 += X;
            Y1 -= (Y + Random);
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
        if (Direction == 2) {
            X1 += X;
            Y1 += Y + Random;
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
        if (Direction > 5) {
            if (X1 > 900 && StrikeDirection % 2 == 0) {
                X1 += X;
                Y1 += Y - 3;
            }
            if (X1 > 900 && StrikeDirection % 2 == 1) {
                X1 += X;
                Y1 -= Y - 3;
            }
            if (X1 < 900) {
                X1 += X;
            }
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.left = X1 + "px";
            document.getElementById("Ball").asInstanceOf[HTMLImageElement].style.top = Y1 + "px";
        }
    }

    if (autostrike == true) {
        if (X1 > X2) {
          document.getElementById("SecondBlock").asInstanceOf[HTMLElement].style.top = Y1 - 25 + "px";
        }
        if (X1 < X2) {
          document.getElementById("FirsBlock").asInstanceOf[HTMLElement].style.top = Y1 - 25 + "px";
        }
    }
    if (FirstPlayerPoints == document.getElementById("points").asInstanceOf[HTMLSelectElement].value.toInt || SecondPlayerPoints == document.getElementById("points").asInstanceOf[HTMLSelectElement].value.toInt) {
        if (FirstPlayerPoints == document.getElementById("points").asInstanceOf[HTMLSelectElement].value.toInt) {
          dom.window.alert(document.getElementById("n1").asInstanceOf[HTMLInputElement].value + " Good job!. You won the game!");
          Stop();
        }

        if (SecondPlayerPoints == document.getElementById("points").asInstanceOf[HTMLSelectElement].value.toInt) {
          dom.window.alert(document.getElementById("n2").asInstanceOf[HTMLInputElement].value + " Good job! You won the game!");
          Stop();
        }
    }

    document.getElementById("n1").asInstanceOf[HTMLInputElement].readOnly = true;
    document.getElementById("n2").asInstanceOf[HTMLInputElement].readOnly = true;

  }

  @JSExport("Start")
  def Start(): Unit = {
    if (SR == false) {
      inter_1.foreach(dom.window.clearInterval(_))
      inter_2.foreach(dom.window.clearInterval(_))

      inter_1 = Option.empty;
      inter_2 = Option.empty;

      inter_1 = Some(dom.window.setInterval(() => Move(), 20));
      inter_2 = Some(dom.window.setInterval(() => question(), 10));

      SR = true;
    } else {
      document.location.reload(true);
      SR = false;
    }
  }

  @JSExport("Stop")
  def Stop(): Unit = {
    if (inter_1.isDefined && inter_2.isDefined) {
      dom.window.clearInterval(inter_1.get);
      dom.window.clearInterval(inter_2.get);
      
      inter_1 = Option.empty;
      inter_2 = Option.empty;
    } else {
      inter_1 = Some(dom.window.setInterval(() => Move(), 20));
      inter_2 = Some(dom.window.setInterval(() => question(), 10));
    }
  }

  def Restart(): Unit =
    println("Restart")
}
