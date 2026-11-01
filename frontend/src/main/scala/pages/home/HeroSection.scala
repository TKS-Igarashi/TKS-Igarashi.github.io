package pages.home

import org.scalajs.dom
import org.scalajs.dom.document
import com.raquo.laminar.api.L.{*, given}
import components.ArrowLink
import routing.Page

object HeroSection:
  def render(): HtmlElement =
    sectionTag(
      cls := "hero",
      introduction,
      HeroArtwork.render()
    )

  private def introduction: HtmlElement =
    div(
      div(
        cls := "eyebrow",
        "Web制作・アプリケーション開発"
      ),
      headline,
      p(
        cls := "hero-tagline",
        "考えて、つくる。使う人のために。"
      ),
      description,
      actions
    )

  private def headline: HtmlElement =
    h1(
      "想いを、かたちに。",
      br(),
      em(
        "使いやすさを、",
        br(),
        "その先へ。"
      )
    )

  private def description: HtmlElement =
    p(
      cls := "body-copy",
      "シンプルであること。わかりやすいこと。",
      br(),
      "一つひとつの課題に向き合い、",
      br(),
      "想いが伝わるWebのかたちを考えます。"
    )

  private def actions: HtmlElement =
    div(
      cls := "actions",
      ArrowLink.render(
        label = "制作実績を見る",
        destination = Page.Works,
        cssClass = "button"
      ),
      ArrowLink.render(
        label = "私について",
        destination = Page.Profile,
        arrow = "→"
      )
    )
