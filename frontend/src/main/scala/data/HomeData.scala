package data

import models.{Project, ProjectImage}

object HomeData:

  val technologies: List[String] =
    List(
      "JavaScript",
      "Scala",
      "PostgreSQL"
    )

  val projects: List[Project] =
    List(
      Project(
        id = "portfolio",
        title = "IGAM ポートフォリオ",
        category = "Webサイト",
        conceptLabel = "公開中・改善中",
        brandName = "IGAM",
        headlineLines = List(
          "学びを、",
          "動くかたちに。"
        ),
        thumbnailClass = "",
        description =
          "プロフィール、学習している技術、制作実績を紹介する個人サイト。",
        purpose =
          "自分の取り組みや制作物をまとめ、スカウトや制作相談につなげるため。",
        role =
          "AI生成のデザインを基にしたScala.jsでのフロントエンド実装。",
        technologies =
          List("Scala", "Scala.js", "Laminar", "Waypoint", "CSS"),
        plannedTechnologies =
          List("Play Framework", "PostgreSQL"),
        highlights =
          List(
            "共通部品とページを分け、表示内容を管理しやすい構成にした。",
            "実績の絞り込みやフォームの入力確認を実装した。",
            "スマートフォンのメニューとキーボード操作に対応した。"
          ),
        githubUrl = Some("https://github.com/TKS-Igarashi/TKS-Igarashi.github.io"),
        publicUrl = Some("https://tks-igarashi.github.io/"),
        images = List(
          ProjectImage(
            src = "/images/portfolio/home.png",
            alt = "IGAMポートフォリオのホーム画面"
          )
        )
      )
    )
