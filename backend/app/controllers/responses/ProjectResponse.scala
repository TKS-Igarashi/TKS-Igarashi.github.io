package controllers.responses

import models.Project
import play.api.libs.json.*

final case class ProjectImageResponse(src: String, alt: String)

final case class ProjectResponse(
                                  id: String,
                                  title: String,
                                  category: String,
                                  status: String,
                                  brandName: String,
                                  headlineLines: List[String],
                                  description: String,
                                  purpose: String,
                                  role: String,
                                  technologies: List[String],
                                  plannedTechnologies: List[String],
                                  highlights: List[String],
                                  githubUrl: Option[String],
                                  publicUrl: Option[String],
                                  images: List[ProjectImageResponse]
                                )

object ProjectResponse:
  // 値がない Optionは、キーを省かずnullとして出す。
  private val config = Json.configured(
    using JsonConfiguration(optionHandlers = OptionHandlers.WritesNull)
  )

  given Writes[ProjectImageResponse] = Json.writes[ProjectImageResponse]
  given Writes[ProjectResponse] = config.writes[ProjectResponse]

  def from(project: Project): ProjectResponse =
    ProjectResponse(
      id = project.id,
      title = project.title,
      category = project.category.code,
      status = project.status.code,
      brandName = project.brandName,
      headlineLines = project.headlineLines,
      description = project.description,
      purpose = project.purpose,
      role = project.role,
      technologies = project.technologies,
      plannedTechnologies = project.plannedTechnologies,
      highlights = project.highlights,
      githubUrl = project.githubUrl,
      publicUrl = project.publicUrl,
      images = project.images.map(image => ProjectImageResponse(image.src, image.alt))
    )

final case class ProjectListResponse(projects: List[ProjectResponse])

object ProjectListResponse:
  given Writes[ProjectListResponse] = Json.writes[ProjectListResponse]