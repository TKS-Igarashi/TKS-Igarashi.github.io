package services

import jakarta.inject.{Inject, Singleton}
import models.{Category, Project}
import repositories.ProjectRepository
import scala.concurrent.Future

@Singleton
class ProjectService @Inject()(repository: ProjectRepository):
  def list(category: Option[Category]): Future[List[Project]] =
    repository.findAll(category)

  def find(id: String): Future[Option[Project]] =
    repository.findById(id)