package models

enum ProjectStatus(val code: String):
  case InDevelopment extends ProjectStatus("in_development")
  case Published extends ProjectStatus("published")
  case Improving extends ProjectStatus("improving")

object ProjectStatus:
  def fromCode(code: String): Option[ProjectStatus] =
    values.find(_.code == code)