package pages.home

import com.raquo.laminar.api.L.{*, given}

object HeroArtwork:

  def render(): HtmlElement =
    div(
      cls := "art",
      role := "img",
      aria.label :=
        "グリーンの配色で表現したWebサイトとコードのイラスト",
      div(
        cls := "art-grid"
      ),
      div(
        cls := "art-plus",
        "＋"
      ),
      browserWindow,
      codeCard,
      div(
        cls := "art-label",
        "考えて、つくる。一つひとつ、丁寧に。"
      )
    )

  private def browserWindow =
    div(
      cls := "window",
      windowBar,
      windowBody
    )

  private def windowBar =
    div(
      cls := "window-bar",
      i(),
      i(),
      i(),
      span(
        "丁寧に、デザインする。"
      )
    )

  private def windowBody =
    div(
      cls := "window-body",
      windowContent,
      div(
        cls := "sculpture"
      )
    )

  private def windowContent =
    div(
      div(
        cls := "tiny",
        "想いを、かたちに"
      ),
      div(
        cls := "window-title",
        "シンプルに。",
        br(),
        "想いが伝わる。"
      ),
      div(
        cls := "fake-line"
      ),
      div(
        cls := "fake-line",
        width := "65%"
      ),
      div(
        cls := "fake-button"
      )
    )

  private def codeCard =
    div(
      cls := "code-card",
      span("const"),
      " create = (idea) => {",
      br(),
      "　",
      span("return"),
      " {",
      br(),
      "　　clarity: ",
      span("true"),
      ",",
      br(),
      "　　care: ",
      span("\"every detail\""),
      br(),
      "　};",
      br(),
      "};"
    )