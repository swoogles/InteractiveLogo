import java.awt.Polygon

import MainApp.LogoColor.LogoColor
import MainApp.Sector.Sector

import scala.scalajs.js.{JSApp, UndefOr}
import org.scalajs.dom

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

    val topAndCounterSpaceElements =
      counterSpaceElements ++
        lightTopElements ++
        mediumTopElements

    topAndCounterSpaceElements.foreach(_.hide)

    var allElementIndex = 0
    var loopsCompleted = 0
    val loopLimit = 5
    var lightSidePiecesRestoredToOriginalLocation = 0
    var mediumSidePiecesRestoredToOriginalLocation = 0

    val startingHorizontalOffset = -300
    val horizontalStep = -(startingHorizontalOffset / loopLimit)

    lightSideElements.foreach(lightSideElement => {
      val polygon = new Polygon(lightSideElement.children("polygon"))
      polygon.move(startingHorizontalOffset, 0)
    })

    mediumSideElements.foreach(mediumSideElement => {
      val polygon = new Polygon(mediumSideElement.children("polygon"))
      polygon.move(startingHorizontalOffset, 0)
    })

    dom.window.setInterval( () => {
      if (allElementIndex < topAndCounterSpaceElements.size) {
        topAndCounterSpaceElements(allElementIndex).show()
        allElementIndex += 1
      } else if (lightSidePiecesRestoredToOriginalLocation < lightSideElements.size) {
        val polygon = new Polygon(lightSideElements(lightSidePiecesRestoredToOriginalLocation).children("polygon"))
        polygon.move(horizontalStep, 0)
        loopsCompleted += 1
        if (loopsCompleted == loopLimit) {
          loopsCompleted = 0
          lightSidePiecesRestoredToOriginalLocation += 1
        }
      } else if (mediumSidePiecesRestoredToOriginalLocation < mediumSideElements.size) {
        val polygon = new Polygon(mediumSideElements(mediumSidePiecesRestoredToOriginalLocation).children("polygon"))
        polygon.move(horizontalStep, 0)
        loopsCompleted += 1
        if (loopsCompleted == loopLimit) {
          loopsCompleted = 0
          mediumSidePiecesRestoredToOriginalLocation += 1
        }
      } else if (darkFill.is(":hidden")) {
        darkFill.show(500)
      }
    }, 50)

  }

  class Polygon(polygon: JQuery) {
    def move(xIn: Float, yIn: Float): Unit = {
      val rawPoints: String = polygon.attr("points").get
      val pairedPoints: List[Array[String]] = rawPoints.split("\\s+").grouped(2).toList
      val newPoints = pairedPoints.map {
        case Array(x, y) => s"${x.toFloat+xIn}  ${y.toFloat+yIn}"
      }
      setPolygonPoints(polygon, newPoints)
    }
  }

  private def setPolygonPoints(polygon: JQuery, newPoints: List[String]): Unit = {
    if (!polygon.is("polygon")) throw new IllegalArgumentException("Element is not a polygon.")
      polygon.attr("points", newPoints.mkString(" "))
  }

}
