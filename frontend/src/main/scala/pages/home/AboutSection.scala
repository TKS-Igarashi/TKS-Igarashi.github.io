package pages.home

import com.raquo.laminar.api.L.{*, given}
import components.ArrowLink
import routing.Page

object AboutSection:

  def render(): HtmlElement =
    sectionTag(
      cls := "home-about",
      heading,
      introduction
    )

  private def heading: HtmlElement =
    div(
      span(
        cls := "overline",
        "大切にしていること"
      ),
      h2(
        "つくる、その背景に。"
      )
    )

  private def introduction: HtmlElement =
    div(
      p(
        "使いやすい仕組みは、丁寧に考えることから。",
        br(),
        "画面の向こうにいる人を想像しながら、",
        br(),
        "フロントエンドからバックエンドまで学び、つくっています。"
      ),
      ArrowLink.render(
        label = "プロフィールを見る",
        destination = Page.Profile
      )
    )
