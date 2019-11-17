import MainApp.LogoColor.LogoColor
import MainApp.Sector.Sector

import scala.scalajs.js.{JSApp, UndefOr}

//import org.scalajs.dom
//import dom.{ document, window }

import org.querki.jquery._

object MainApp extends JSApp {
  object Sector extends Enumeration {
    type Sector = Value
    val Side = Value("SIDE")
    val Top = Value("TOP")
  }

  object LogoColor extends Enumeration {
    type LogoColor = Value
    val Medium = Value("Medium")
    val Light = Value("Light")
  }


  val biggestSideMedium = "SIDE_Med_07"
  val biggestSideLight = "SIDE_Light_08"
  val biggestTopMedium = "TOP_Med_04"
  val biggestTopLight = "TOP_Light_06"

  def createElementId(sector: Sector, logoColor: LogoColor, index: Int): String =
    s"#${sector}_${logoColor}_0$index"


  def main(): Unit = {
    println("Starting 'InteractiveLogo'...")
    val constructedElement = JQueryStatic(createElementId(Sector.Side, LogoColor.Light, 1))
    import org.scalajs.dom
    val element = JQueryStatic("#TOP_Med_04")
    element.hide()
    dom.window.setInterval( () => {
      if (constructedElement.is(":visible")) constructedElement.hide()
      else constructedElement.show()

      if (element.is(":visible")) element.hide()
      else element.show()

      println("toggling")

    }, 1000)
//    dom.window.alert("Hi from Scala-js-dom")
//    dom.document.querySelector("#TOP_Med_04").setAttribute("hidden", "true")


//    val p = document.createElement("p")
//    val text = document.createTextNode("Hello!")
//    p.appendChild(text)
//    document.body.appendChild(p)
  }

}
