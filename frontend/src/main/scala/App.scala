import org.scalajs.dom
import com.raquo.laminar.api.L.*
import components.{Footer, Header, PageLink}
import pages.*
import routing.{AppRouter, Page}

object App:

  private def renderPage(page: Page): HtmlElement =
    page match
      case Page.Home => Home.render()
      case Page.Profile => Profile.render()
      case Page.Skill => Skill.render()
      case Page.Works => Works.render()
      case Page.Contact => Contact.render()

  def apply(): HtmlElement =
    var initialRender = true

    val content = mainTag(
      idAttr := "content",
      tabIndex := -1
    )

    content.amend(
      child <-- AppRouter.router.currentPageSignal.map { page =>
        val view = renderPage(page)

        view.amend(
          onMountCallback { _ =>
            if initialRender then
              initialRender = false
            else
              content.ref.focus()
              dom.window.scrollTo(0, 0)
          }
        )

        view
      }
    )

    div(
      a(
        cls := "skip",
        href := "#content",
        "本文へ移動",
        onClick --> { event =>
          event.preventDefault()
          content.ref.focus()
        }
      ),
      Header.render(),
      content,
      Footer.render()
    )
