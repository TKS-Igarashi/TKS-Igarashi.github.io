package routing

import com.raquo.waypoint.*

object AppRouter:

  val router: Router[Page] = new Router[Page](
    routes = List(
      Route.static(Page.Home, root),
      Route.static(Page.Profile, root / "profile"),
      Route.static(Page.Skill, root / "skill"),
      Route.static(Page.Works, root / "works"),
      Route.static(Page.Contact, root / "contact"),
    ),
    // ブラウザのタブに表示するタイトル
    getPageTitle = page => page.title,

    // Pageを、ブラウザの履歴に保存する文字列へ変換
    serializePage = page => page.key,

    // 履歴に保存した文字列からPageを復元
    deserializePage = key => Page.fromKey(key)
  )
