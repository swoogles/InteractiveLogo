import scala.scalajs.js.{JSApp, UndefOr}

//import org.scalajs.dom
//import dom.{ document, window }

import org.querki.jquery._

object MainApp extends JSApp {

  def main(): Unit = {
    println("Starting 'InteractiveLogo'...")
    import org.scalajs.dom
    val element = JQueryStatic("#TOP_Med_04")
    element.hide()
    dom.window.setInterval( () => {
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
