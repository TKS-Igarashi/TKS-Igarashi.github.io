package components

import com.raquo.laminar.api.L.{*, given}

object PageHeading:

  def render(
              title: String,
              eyebrow: String,
              description: String
            ): HtmlElement =
    div(
      cls := "page-head",
      span(
        cls := "eyebrow",
        eyebrow
      ),
      h1(
        title,
        span(
          color := "#96aa7e",
          "."
        )
      ),
      p(
        description
      )
    )
