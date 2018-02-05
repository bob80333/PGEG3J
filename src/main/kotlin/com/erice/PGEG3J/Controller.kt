package com.erice.PGEG3J

import com.erice.PGEG3J.project.Project
import com.erice.PGEG3JL.Banks
import com.erice.PGEG3JL.GameData
import com.erice.PGEG3JL.Map
import javafx.scene.control.Tab
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.util.Callback
import tornadofx.*


class ProjectController : Controller() {

    lateinit var banks: Banks
    private set

    var project: Project = Project.EmptyProject
        set(value) {
            banks = Banks(value.rom, value.game, GameData(value.game))
            setupMapsAndBanks()
            field = value
        }
    lateinit var mapsAndBanks : TreeView<String>

    private fun setupMapsAndBanks() {
        val root = TreeItem<String>(" ")
        val banksList = (0 until banks.banks.size).toList().map { it.toString() }
        val mapsList = mutableListOf(listOf<String>())
        mapsList.removeAt(0)
        banks.banks.forEachIndexed{ index, it ->
            mapsList.add(it.maps.toList().mapIndexed{ index2, map -> map.name + " " + index +"." + index2})
        }

        mapsAndBanks.root = root
        mapsAndBanks.root.isExpanded = true


        mapsAndBanks.populate {
            if (it == root) banksList else
                if (banksList.contains(it.value)) mapsList[banksList.indexOf(it.value)] else listOf()
        }
    }

    fun setupMouseClickOnTreeView() {
        mapsAndBanks.cellFactory = Callback<TreeView<String>, TreeCell<String>> {
            val cell =  TreeCell<String>()
            cell.setOnMouseClicked {  }
            cell
        }

    }


}

class MapController : Controller() {
    val openMaps = MutableList<OpenedMap>(0, {OpenedMap()})
}

class OpenedMap {
    lateinit var tab: Tab
    lateinit var map: Map
}


