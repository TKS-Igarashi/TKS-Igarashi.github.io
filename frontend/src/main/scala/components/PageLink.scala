package components

import com.raquo.laminar.api.L.*
import routing.{AppRouter, Page}

object PageLink:
  def apply(
             page: Page,
             label: String,
             cssClass: String = ""
           ): HtmlElement =
    val isCurrent = AppRouter.router.currentPageSignal.map(_ == page)
    a(
      cls := cssClass,
      cls("active") <-- isCurrent,
      aria.current <-- isCurrent.map { current =>
        if current then "page" else "false"
      },
      AppRouter.router.navigateTo(page),
      label
    )