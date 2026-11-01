package models

final case class ProjectImage(
                               src: String,
                               alt: String
                             )

final case class Project(
                          id: String,
                          title: String,
                          category: String,
                          conceptLabel: String,
                          brandName: String,
                          headlineLines: List[String],
                          thumbnailClass: String,
                          description: String,

                          purpose: String = "",
                          role: String = "",
                          technologies: List[String] = Nil,
                          plannedTechnologies: List[String] = Nil,
                          highlights: List[String] = Nil,
                          githubUrl: Option[String] = None,
                          publicUrl: Option[String] = None,
                          images: List[ProjectImage] = Nil
                        )
