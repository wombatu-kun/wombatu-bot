pluginManagement {
	repositories {
		gradlePluginPortal()
	}
}

rootProject.name = "wombatubot"

include("bot-app", "bot-currency-api")

rootProject.children.forEach {
	it.buildFileName = it.name.drop(4) + ".gradle.kts"
}
