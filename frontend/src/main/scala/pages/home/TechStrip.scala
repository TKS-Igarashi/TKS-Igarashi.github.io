package pages.home

import com.raquo.laminar.api.L.{*, given}

object TechStrip:

  def render(
              technologies: List[String]
            ): HtmlElement =
    div(
      cls := "intro-strip",
      span(
        "開発に使う技術"
      ),
      div(
        cls := "tech-list",
        technologies.map { technology =>
          span(technology)
        }
      )
    )
