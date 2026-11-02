import com.google.inject.AbstractModule
import repositories.{InMemoryProjectRepository, ProjectRepository}

class Module extends AbstractModule:
  override def configure(): Unit =
    bind(classOf[ProjectRepository]).to(classOf[InMemoryProjectRepository])