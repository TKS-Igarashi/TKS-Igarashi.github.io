package pages

import org.scalajs.dom
import org.scalajs.dom.document
import com.raquo.laminar.api.L.{*, given}
import components.{ContactBand, Footer, Header}
import data.HomeData
import pages.home.{AboutSection, HeroSection, TechStrip, WorksSection}

object Home:

  def render(): HtmlElement =
    div(
      div(
        cls := "wrap",
        HeroSection.render(),
        TechStrip.render(HomeData.technologies),
        WorksSection.render(HomeData.projects),
        AboutSection.render()
      ),
      ContactBand.render()
    )