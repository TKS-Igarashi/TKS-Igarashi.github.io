package routing

import com.raquo.laminar.api.L.{*, given}
import com.raquo.waypoint.*
import org.scalajs.dom

private val titleText = " | IGAM's Portfolio"

sealed abstract class Page(
                            val key: String,
                            val title: String
                          )

object Page:
  case object Home extends Page("home", s"ホーム${titleText}")
  case object Profile extends Page("profile", s"プロフィール${titleText}")
  case object Skill extends Page("skill", s"スキル${titleText}")
  case object Works extends Page("works", s"制作物${titleText}")
  case object Contact extends Page("contact", s"お問い合わせ${titleText}")

  val all: List[Page] =
    List(Home, Profile, Skill, Works, Contact)

  def fromKey(key: String): Page =
    all.find(_.key == key).getOrElse {
      throw new IllegalArgumentException(s"Unknown page: $key")
    }