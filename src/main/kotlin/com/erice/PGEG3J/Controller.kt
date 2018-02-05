package com.erice.PGEG3J

import com.erice.PGEG3J.project.Project
import com.erice.PGEG3JL.Map
import javafx.scene.control.Tab
import javafx.scene.control.TreeView
import tornadofx.*

class ProjectController : Controller() {

    var project: Project = Project.EmptyProject
    lateinit var mapsAndBanks : TreeView<String>

}

class MapController : Controller() {
    val openMaps = MutableList<OpenedMap>(0, {OpenedMap()})
}

class OpenedMap() {
    lateinit var tab: Tab
    lateinit var map: Map
}


