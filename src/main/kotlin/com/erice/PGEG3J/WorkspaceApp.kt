package com.erice.PGEG3J

import javafx.application.Application
import javafx.scene.image.Image
import tornadofx.*

class WorkspaceApp : App(MapWorkspace::class, Style::class) {
    val controller: ProjectController by inject()

    init {
        setStageIcon(Image(javaClass.classLoader.getResourceAsStream("PGEG3J-Iconv4.png")))
    }

}

fun main(args: Array<String>) {
    Application.launch(WorkspaceApp::class.java, *args)
}