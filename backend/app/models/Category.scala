package models

enum Category(val code: String):
  case Website extends Category("website")
  case WebApp extends Category("web_app")

object Category:
  def fromCode(code: String): Option[Category] =
    values.find(_.code == code)
