package components

import com.raquo.laminar.api.L.{*, given}
import routing.Page

object ContactBand:

  def render(): HtmlElement =
    sectionTag(
      cls := "contact-band",
      introduction,
      ArrowLink.render(
        label = "お問い合わせ",
        destination = Page.Contact,
        cssClass = "button"
      )
    )

  private def introduction: HtmlElement =
    div(
      span(
        cls := "overline",
        "まずは、お話ししませんか"
      ),
      h2(
        "一緒に、かたちに。"
      ),
      p(
        "アイデアを、少しずつかたちに。まずはお話ししませんか。"
      )
    )
