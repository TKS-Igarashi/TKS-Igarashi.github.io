package pages

import com.raquo.laminar.api.L.{*, given}
import components.{ContactBand, PageHeading}

object Skill:

  private final case class SkillCategory(
                                          number: String,
                                          title: String,
                                          description: String,
                                          technologies: List[(String, String)]
                                        )

  private val categories: List[SkillCategory] =
    List(
      SkillCategory(
        number = "01",
        title = "バックエンド",
        description = "バックエンドは個人開発にて学習中",
        technologies =
          List(
            "Scala" -> "個人開発",
            "PlayFramework" -> "個人開発",
            "PostgreSQL" -> "個人開発"
          )
      ),
      SkillCategory(
        number = "02",
        title = "フロントエンド",
        description = "JavaScriptは1年ほど社内ツールの作成・改修をしました。TypeScriptやScala.jsは個人開発にて学習中",
        technologies =
          List(
            "HTML / CSS" -> "経験1年ほど",
            "JavaScript" -> "経験1年半ほど",
            "TypeScript" -> "個人開発",
            "React / Next.js" -> "個人開発",
            "jQuery" -> "経験半年ほど",
            "Scala.js" -> "個人開発"
          )
      ),
      SkillCategory(
        number = "03",
        title = "開発ツール",
        description = "GitHubなど基本的なツールに加えて、学習の補助等でAIを使用しています。",
        technologies =
          List(
            "Git / GitHub" -> "経験1年ほど",
            "GitHub Actions" -> "個人開発",
            "Docker" -> "個人開発",
            "Linux" -> "個人開発",
            "Codex / Claude Code" -> "個人開発"
          )
      )
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
        title = "スキル",
        eyebrow = "技術・できること",
        description =
          "伝わる画面から、それを支える仕組みまで。"
      ),
      skillList
    )

  private def skillList: HtmlElement =
    div(
      cls := "skills",
      categories.map { category =>
        skillRow(category)
      },
    )

  private def skillRow(
                        category: SkillCategory
                      ): HtmlElement =
    sectionTag(
      cls := "skill-row",
      span(
        cls := "num",
        category.number
      ),
      div(
        h3(category.title),
        p(category.description)
      ),
      technologyTags(category.technologies)
    )

  private def technologyTags(
                              technologies: List[(String, String)]
                            ): HtmlElement =
    div(
      cls := "tags",
      technologies.map { case (name, experience) =>
        span(s"$name ($experience)")
      }
    )
