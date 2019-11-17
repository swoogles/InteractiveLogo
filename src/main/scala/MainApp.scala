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

  def logoElementGroup(sector: Sector, logoColor: LogoColor, numberOfElements: Int): List[JQuery] =
    Range.inclusive(1, numberOfElements).map( idx => createElementId(sector, logoColor, idx)).map(id => JQueryStatic(id)).toList


  def main(): Unit = {
    println("Starting 'InteractiveLogo'...")
    val lightSideElements = logoElementGroup(Sector.Side, LogoColor.Light, 8)
      lightSideElements
      .foreach(element => element.hide)
    var currentLightSideIndex = 1
    import org.scalajs.dom
    val element: JQuery = JQueryStatic("#TOP_Med_04")
    element.hide()
    dom.window.setInterval( () => {
      if (currentLightSideIndex <= lightSideElements.size) {
        lightSideElements(currentLightSideIndex).show()
        currentLightSideIndex += 1
      }

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
