package pages.home

import com.raquo.laminar.api.L.{*, given}
import components.{ArrowLink, ProjectCard}
import models.Project
import routing.Page

object WorksSection:

  def render(
              projects: List[Project]
            ): HtmlElement =
    sectionTag(
      cls := "section",
      sectionHeader,
      div(
        cls := "cards",
        projects.map { project =>
          ProjectCard.render(project)
        }
      ),
      p(
        cls := "sample-note",
        "現在制作中のポートフォリオサイトを掲載しています。"
      )
    )

  private def sectionHeader: HtmlElement =
    div(
      cls := "section-top",
      div(
        span(
          cls := "overline",
          "つくったもの"
        ),
        h2(
          "制作実績"
        ),
        p(
          "デザインと実装で、アイデアをかたちに。"
        )
      ),
      ArrowLink.render(
        label = "すべての実績を見る",
        destination = Page.Works
      )
    )
