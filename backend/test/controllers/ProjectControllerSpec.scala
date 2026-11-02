package controllers

import org.scalatestplus.play.*
import org.scalatestplus.play.guice.*
import play.api.libs.json.*
import play.api.test.*
import play.api.test.Helpers.*

class ProjectControllerSpec extends PlaySpec with GuiceOneAppPerSuite {

  private def get(path: String) = route(app, FakeRequest(GET, path)).get

  "GET /api/projects" should {
    "return projects as JSON" in {
      val result = get("/api/projects")

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      val ids = (contentAsJson(result) \ "projects").as[List[JsObject]].map(p => (p \ "id").as[String])
      ids mustBe List("portfolio")
    }

    "filter by category" in {
      val result = get("/api/projects?category=web_app")

      status(result) mustBe OK
      (contentAsJson(result) \ "projects").as[List[JsObject]] mustBe empty
    }

    "return 400 for an unknown category" in {
      val result = get("/api/projects?category=unknown")

      status(result) mustBe BAD_REQUEST
      (contentAsJson(result) \ "error" \ "code").as[String] mustBe "invalid_category"
    }
  }

  "GET /api/projects/:id" should {
    "return the project" in {
      val result = get("/api/projects/portfolio")

      status(result) mustBe OK
      val json = contentAsJson(result)
      (json \ "category").as[String] mustBe "website"
      (json \ "status").as[String] mustBe "improving"
      (json \ "displayOrder").toOption mustBe None
    }

    "return 404 when the project does not exist" in {
      val result = get("/api/projects/missing")

      status(result) mustBe NOT_FOUND
      (contentAsJson(result) \ "error" \ "code").as[String] mustBe "not_found"
    }
  }
}
