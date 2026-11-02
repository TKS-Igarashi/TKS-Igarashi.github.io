package repositories

import models.{Category, Project}
import scala.concurrent.Future

trait ProjectRepository:
  // displayOrderの昇順、同じ番号ならidの昇順で返す
  def findAll(category: Option[Category]): Future[List[Project]]
  def findById(id: String): Future[Option[Project]]