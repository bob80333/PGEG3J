package com.erice.PGEG3J

import javafx.application.Application
import javafx.scene.image.Image
import tornadofx.*

class WorkspaceApp : App() {
    override val primaryView = MapWorkspace::class

    init {
        importStylesheet(Style::class)
        setStageIcon(Image(javaClass.classLoader.getResourceAsStream("PGEG3J-Iconv4.png")))
    }

}

fun main(args: Array<String>) {
    Application.launch(WorkspaceApp::class.java, *args)
}