package pages

import com.raquo.laminar.api.L.{*, given}
import components.{ ContactBand, PageHeading, ProjectCard }
import data.HomeData

object Works:

  def render(): HtmlElement =
    div(
      content,
      ContactBand.render()
    )

  private def content: HtmlElement =
    val selectedCategory = Var[Option[String]](None)

    val choices: List[(String, Option[String])] = List(
      ("すべて", None),
      ("Webサイト", Some("Webサイト")),
      ("Webアプリ", Some("Webアプリ"))
    )

    val filters = div(
      cls := "filters",
      role := "group",
      aria.label := "制作実績を絞り込み",
      choices.map { case (label, category) =>
        val isSelected = selectedCategory.signal.map(_ == category)
        button(
          typ := "button",
          cls := "filter",
          cls("selected") <-- isSelected,
          aria.pressed <-- isSelected.map(_.toString),
          label,
          onClick --> { _ =>
            selectedCategory.set(category)
          }
        )
      }
    )

    val projectList = div(
      cls := "cards",
      idAttr := "work-list",
      children <-- selectedCategory.signal.map { category =>
        val projects = HomeData.projects.filter { project =>
          category.forall(_ == project.category)
        }

        if projects.isEmpty then
          List(
            p(
              cls := "sample-note",
              "該当する制作実績はまだありません。"
            )
          )
        else
          projects.map(ProjectCard.render)
      }
    )

    div(
      cls := "wrap",
      PageHeading.render(
        title = "制作実績",
        eyebrow = "これまでの制作例",
        description =
          "一つひとつのアイデアに、ふさわしいかたちを。"
      ),
      filters,
      projectList,
      sampleNote
    )

//  private def filters: HtmlElement =
//    div(
//      cls := "filters",
//      role := "group",
//      aria.label := "制作実績を絞り込み",
//      filterButton(
//        label = "すべて",
//        filterValue = "All",
//        selected = true
//      ),
//      filterButton(
//        label = "Webサイト",
//        filterValue = "Website",
//        selected = false
//      ),
//      filterButton(
//        label = "Webアプリ",
//        filterValue = "Web App",
//        selected = false
//      )
//    )
//
//  private def filterButton(
//                            label: String,
//                            filterValue: String,
//                            selected: Boolean
//                          ): HtmlElement =
//    button(
//      typ := "button",
//      cls :=
//        (if selected then "filter selected" else "filter"),
//      dataAttr("filter") := filterValue,
//      aria.pressed := selected.toString,
//      label
//    )
//
//  private def projectList: HtmlElement =
//    div(
//      cls := "cards",
//      idAttr := "work-list",
//      HomeData.projects.map { project =>
//        ProjectCard.render(project)
//      }
//    )

  private def sampleNote: HtmlElement =
    p(
      cls := "sample-note",
      marginBottom := "70px",
    )
