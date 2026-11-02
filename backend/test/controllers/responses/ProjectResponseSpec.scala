package controllers.responses

import models.{Category, Project, ProjectStatus}
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.*

class ProjectResponseSpec extends PlaySpec {

  // テスト用のデータ。InMemoryProjectRepository のデータには依存させない
  private val project = Project(
    id = "sample",
    title = "サンプル",
    category = Category.WebApp,
    status = ProjectStatus.InDevelopment,
    brandName = "SAMPLE",
    headlineLines = List("見出し"),
    description = "説明",
    purpose = "目的",
    role = "担当",
    technologies = List("Scala"),
    plannedTechnologies = Nil,
    highlights = Nil,
    githubUrl = Some("https://example.com/repo"),
    publicUrl = Some("https://example.com"),
    images = Nil,
    displayOrder = 1
  )

  private def toJson(project: Project): JsValue =
    Json.toJson(ProjectResponse.from(project))

  "ProjectResponse JSON" should {
    "write a missing URL as null instead of omitting the key" in {
      val json = toJson(project.copy(githubUrl = None))

      (json \ "githubUrl").get mustBe JsNull
    }

    "write category and status as codes" in {
      val json = toJson(project)

      (json \ "category").as[String] mustBe "web_app"
      (json \ "status").as[String] mustBe "in_development"
    }

    "write empty lists as []" in {
      val json = toJson(project)

      (json \ "images").get mustBe Json.arr()
    }
  }
}
