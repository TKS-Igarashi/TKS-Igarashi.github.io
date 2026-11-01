package pages

import com.raquo.laminar.api.L.{*, given}
import components.{ContactBand, PageHeading}

object Profile:

  private val factItems: List[(String, String)] =
    List(
      "公開名" -> "IGAM",
      "取り組んでいる分野" -> "Webアプリケーション開発",
      "関心のある分野" ->
        "バックエンド / フロントエンド / AI / QA / インフラ / セキュリティ",
      "現在の取り組み" -> "ポートフォリオサイトの制作"
    )

  def render(): HtmlElement =
    div(
      content,
      ContactBand.render()
    )

  private def content: HtmlElement =
    div(
      cls := "wrap",
      PageHeading.render(
        title = "プロフィール",
        eyebrow = "私について",
        description =
          "考えることと、つくること。その両方を大切に。"
      ),
      profileLayout
    )

  private def profileLayout: HtmlElement =
    sectionTag(
      cls := "profile-layout",
      brandMark,
      biography
    )

  private def brandMark: HtmlElement =
    div(
      cls := "monogram",
      aria.label := "IGAMのブランドマーク",
      "IGAM"
    )

  private def biography: HtmlElement =
    div(
      span(
        cls := "overline",
        "Web制作・開発"
      ),
      h2(
        "IGAM"
      ),
      p(
        cls := "body-copy",
        "現在はポートフォリオサイト制作を取り組んでいます。" +
        "Scalaを使用していますが、色々な言語や技術を取り組みたいと思っています。" +
        "バックエンドに強く関心があり、他にAI・QA・セキュリティなども関心があります。"
      ),
      facts,
    )

  private def facts: HtmlElement =
    dl(
      cls := "facts",
      factItems.map { case (label, description) =>
        factRow(label, description)
      }
    )

  private def factRow(
                       label: String,
                       description: String
                     ): HtmlElement =
    div(
      dt(label),
      dd(description)
    )
