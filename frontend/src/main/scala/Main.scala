import org.scalajs.dom
import org.scalajs.dom.document
import com.raquo.laminar.api.L.{*, given}

object Main:

  def main(args: Array[String]): Unit =
    renderOnDomContentLoaded(
      document.body,
      App()
    )