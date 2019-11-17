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
    val Medium = Value("Med")
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
    println("One big collection of elements")

    val darkFill = JQueryStatic("#B_Dark_Fill")
    darkFill.hide()

    val counterSpaceElements =
      List(
      JQueryStatic("#Counter_Space_01"),
        JQueryStatic("#Counter_Space_02")
        )

    val lightSideElements = logoElementGroup(Sector.Side, LogoColor.Light, 8)
    val mediumSideElements = logoElementGroup(Sector.Side, LogoColor.Medium, 7)
    val lightTopElements = logoElementGroup(Sector.Top, LogoColor.Light, 8)
    val mediumTopElements = logoElementGroup(Sector.Top, LogoColor.Medium, 7)

    val allElements =
      counterSpaceElements ++
      lightSideElements ++
        mediumSideElements ++
        lightTopElements ++
        mediumTopElements

    allElements.foreach(_.hide)

    var currentLightSideIndex = 0
    var finished = false

    import org.scalajs.dom
    dom.window.setInterval( () => {
      if (currentLightSideIndex < allElements.size) {
        allElements(currentLightSideIndex).show()
        currentLightSideIndex += 1
      } else if (darkFill.is(":hidden")){
        darkFill.show(500)
      } else if (!finished) {
        lightSideElements.foreach(lightSideElement => {
          val rawPoints: String = lightSideElement.children("polygon").attr("points").get
          println("rawPoints: " + rawPoints)
          println("shape points: " )
          val pairedPoints: List[Array[String]] = rawPoints.split("\\s+").grouped(2).toList
          println("num of paired points: " + pairedPoints.length)
          pairedPoints.foreach(println)
          val newPoints = pairedPoints.map {
            case Array(x, y) => s"${x.toFloat-50}  $y"
          }.mkString(" ")
          lightSideElement.children("polygon").attr("points", newPoints)
        })
        finished = true
      }
//      if (element.is(":visible")) element.hide()
//      else element.show()
    }, 100)

  }

}
