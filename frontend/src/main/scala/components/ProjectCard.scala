package components

import com.raquo.laminar.api.L.{*, given}
import models.Project

object ProjectCard:

  def render(project: Project): HtmlElement =
    val details = dialogTag(
      aria.label := s"${project.title}の詳細"
    )

    details.amend(
      button(
        typ := "button",
        cls := "close",
        aria.label := "閉じる",
        "×",
        onClick --> { _ => details.ref.close() }
      ),
      span(
        cls := "overline",
        s"制作コンセプト / ${project.category}"
      ),
      h2(project.brandName),
      h3(project.title),
      p(cls := "body-copy", project.description),
      div(
        h3("制作目的"),
        p(cls := "body-copy", project.purpose),

        h3("自分の担当"),
        p(cls := "body-copy", project.role),

        h3("使用技術"),
        div(
          cls := "tags",
          project.technologies.map(technology => span(technology))
        ),

        Option.when(project.plannedTechnologies.nonEmpty)(
          div(
            h3("今後導入予定の技術"),
            div(
              cls := "tags",
              project.plannedTechnologies.map(technology => span(technology))
            )
          )
        ),

        h3("工夫した点"),
        ul(project.highlights.map(point => li(point))),

        project.githubUrl.map { url =>
          p(
            a(
              href := url,
              target := "_blank",
              rel := "noopener noreferrer",
              "GitHubを見る（別タブ）"
            )
          )
        },

        project.publicUrl.map { url =>
          p(
            a(
              href := url,
              target := "_blank",
              rel := "noopener noreferrer",
              "公開サイトを見る（別タブ）"
            )
          )
        },

        div(
          cls := "project-images",
          project.images.map { picture =>
            img(
              src := picture.src,
              alt := picture.alt
            )
          }
        ),
      ),
      onClick --> { event =>
        if event.target == details.ref then
          val rect = details.ref.getBoundingClientRect()
          val outside =
            event.clientX < rect.left || event.clientX > rect.right ||
              event.clientY < rect.top || event.clientY > rect.bottom

          if outside then details.ref.close()
      }
    )

    div(
      button(
        typ := "button",
        cls := "work-card",
        aria.label := s"${project.title}の詳細",
        thumbnail(project),
        metadata(project),
        h3(
          project.title,
          " ",
          span(aria.hidden := true, "↗")
        ),
        onClick --> { _ =>
          if !details.ref.open then details.ref.showModal()
        }
      ),
      details
    )

  private def thumbnail(project: Project): HtmlElement =
    div(
      cls := s"thumb ${project.thumbnailClass}".trim,
      project.images.headOption match
        case Some(picture) =>
          img(
            src := picture.src,
            alt := picture.alt
          )
        case None =>
          span("画像準備中")
    )

  private def metadata(
                        project: Project
                      ): HtmlElement =
    div(
      cls := "work-meta",
      span(project.category),
      span(project.conceptLabel)
    )
