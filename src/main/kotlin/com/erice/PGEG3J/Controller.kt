package com.erice.PGEG3J

import com.erice.PGEG3J.project.Project
import com.erice.PGEG3JL.Banks
import com.erice.PGEG3JL.GameData
import com.erice.PGEG3JL.Map
import javafx.scene.control.*
import tornadofx.*


class ProjectController : Controller() {

    val mapController: MapController by inject()
    lateinit var banks: Banks
    private set

    var project: Project = Project.EmptyProject
        set(value) {
            banks = Banks(value.rom, value.game, GameData(value.game))
            setupMapsAndBanks()
            field = value
        }
    lateinit var mapsAndBanks : TreeView<Button>

    private fun setupMapsAndBanks() {
        val root = TreeItem<Button>(Button(""))
        val banksList = (0 until banks.banks.size).toList().map { Button(it.toString()) }
        val mapsList = mutableListOf(listOf<Button>())
        mapsList.removeAt(0)
        banks.banks.forEachIndexed{ index, it ->
            mapsList.add(it.maps.toList().mapIndexed{ index2, map ->
                val button = Button(map.name + " " + index +"." + index2)
                button.action { mapController.openMap(map, button.text) }
                button
            })
        }

        mapsAndBanks.root = root
        mapsAndBanks.root.isExpanded = true


        mapsAndBanks.populate {
            if (it == root) banksList else
                if (banksList.contains(it.value)) mapsList[banksList.indexOf(it.value)] else listOf()
        }
    }


}

class MapController : Controller() {
    val openMaps = MutableList<OpenedMap>(0, {OpenedMap()})
    lateinit var tabPane: TabPane
    lateinit var selectionModel: SelectionModel<Tab>

    fun openMap(map: Map, name: String) {
        val existingMap = openMaps.firstOrNull { it.map == map }
        if (existingMap != null) {
            selectionModel.select(existingMap.tab)
        } else {
            val newTab = tabPane.tab(name)
            val openedMap = OpenedMap()
            openedMap.map = map
            openedMap.tab = newTab
            openMaps.add(openedMap)

            newTab.setOnClosed { closeMap(openedMap) }
        }
    }

    private fun closeMap(openedMap: OpenedMap) {
        openMaps.remove(openedMap)
    }
}

class OpenedMap {
    lateinit var tab: Tab
    lateinit var map: Map
}


