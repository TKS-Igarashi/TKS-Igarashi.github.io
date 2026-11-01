package components

import org.scalajs.dom
import org.scalajs.dom.document
import com.raquo.laminar.api.L.{*, given}
import routing.{AppRouter, Page}

object Header:

  private val navItems = List(
    (Page.Home, "ホーム"),
    (Page.Profile, "プロフィール"),
    (Page.Skill, "スキル"),
    (Page.Works, "制作実績"),
  )

  def render(): HtmlElement =
    val menuOpen = Var(false)

    val menuButton = button(
      typ := "button",
      cls := "menu",
      aria.controls := "main-nav",
      aria.expanded <-- menuOpen.signal,
      aria.label <-- menuOpen.signal.map { open =>
        if open then "メニューを閉じる" else "メニューを開く"
      },
      child.text <-- menuOpen.signal.map { open =>
        if open then "メニュー -" else "メニュー +"
      },
      onClick --> { _ =>
        menuOpen.update(open => !open)
      }
    )

    headerTag(
      logo,
      menuButton,
      navTag(
        idAttr := "main-nav",
        aria.label := "メインナビゲーション",
        cls("open") <-- menuOpen.signal,
        navItems.map((page, label) => PageLink(page, label)),
        a(
          cls := "nav-contact",
          AppRouter.router.navigateTo(Page.Contact),
          "お問い合わせ",
          span(aria.hidden := true, "↗")
        )
      ),

      // ページを切り替えたら閉じる
      AppRouter.router.currentPageSignal.updates --> { _ =>
        menuOpen.set(false)
      },

      // Escapeで閉じ、操作していたボタンへ戻る
      onKeyDown --> { event =>
        if event.key == "Escape" && menuOpen.now() then
          menuOpen.set(false)
          menuButton.ref.focus()
      }
    )

  private def logo: HtmlElement =
    a(
      cls := "brand",
      AppRouter.router.navigateTo(Page.Home),
      aria.label := "IGAM ホーム",
      span(
        cls := "mark",
        "i",
        span("■")
      ),
      "IGAM",
      span(cls := "brand-dot", ".")
    )