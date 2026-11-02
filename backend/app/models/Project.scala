package models

final case class ProjectImage(src: String, alt: String)

final case class Project(
                          id: String,
                          title: String,
                          category: Category,
                          status: ProjectStatus,
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
                          images: List[ProjectImage],
                          displayOrder: Int
                        )
