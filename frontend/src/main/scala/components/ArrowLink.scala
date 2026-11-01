package components

import com.raquo.laminar.api.L.{*, given}
import routing.{AppRouter, Page}

object ArrowLink:
  def render(label: String, destination: Page, cssClass: String = "text-link", arrow: String = "↗"): HtmlElement =
    a(
      cls := cssClass,
      AppRouter.router.navigateTo(destination),
      label,
      span(aria.hidden := true, arrow)
    )

