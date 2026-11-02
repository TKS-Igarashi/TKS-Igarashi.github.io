package controllers

import com.google.inject.Singleton
import controllers.responses.{ErrorResponse, ProjectListResponse, ProjectResponse}
import jakarta.inject.Inject
import models.Category
import play.api.libs.json.Json
import play.api.mvc.*
import services.ProjectService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProjectController @Inject()(
                                   val controllerComponents: ControllerComponents,
                                   projectService: ProjectService
                                 )(using ExecutionContext) extends BaseController:

  def list(category: Option[String]): Action[AnyContent] = Action.async {
    parseCategory(category) match
      case Left(error) =>
        Future.successful(BadRequest(Json.toJson(error)))
      case Right(parsedCategory) =>
        projectService.list(parsedCategory).map { projects =>
          Ok(Json.toJson(ProjectListResponse(projects.map(ProjectResponse.from))))
        }
  }

  def show(id: String): Action[AnyContent] = Action.async {
    projectService.find(id).map {
      case Some(project) =>
        Ok(Json.toJson(ProjectResponse.from(project)))
      case None =>
        NotFound(Json.toJson(ErrorResponse("not_found", s"Project '$id' was not found.")))
    }
  }

  // クエリの文字列を Category に変換する。未指定なら絞り込みなし、不正な値ならエラーを返す
  private def parseCategory(raw: Option[String]): Either[ErrorResponse, Option[Category]] =
    raw match
      case None => Right(None)
      case Some(code) =>
        Category.fromCode(code)
          .map(Some(_))
          .toRight(
            ErrorResponse(
              "invalid_category",
              s"Unknown category '$code'. Expected one of: ${Category.values.map(_.code).mkString(", ")}"
            )
          )