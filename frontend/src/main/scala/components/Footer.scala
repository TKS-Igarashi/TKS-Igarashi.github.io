package components

import org.scalajs.dom
import org.scalajs.dom.document
import com.raquo.laminar.api.L.{*, given}
import routing.AppRouter
import routing.Page.Home

object Footer:

  def render(): HtmlElement =
    footerTag(
      a(
        cls := "brand",
        AppRouter.router.navigateTo(Home),
        "IGAM."
      ),
      span("考えて、つくる。使う人のために"),
      small("© 2026 IGAM"),
      a(
        AppRouter.router.navigateTo(Home),
        aria.label := "ホームへ戻る",
        "↑"
      )
    )