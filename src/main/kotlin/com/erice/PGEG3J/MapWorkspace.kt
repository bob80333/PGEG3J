package com.erice.PGEG3J

import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.control.TreeItem
import javafx.scene.paint.Color
import javafx.stage.Modality
import tornadofx.*

class MapWorkspace() : Fragment("PGEG3J") {
    override val root =
            borderpane {
                prefHeight = 720.0
                prefWidth = 1280.0
                top {
                    vbox {
                        menubar {
                            menu("File") {
                                item("New Project").action {
                                    log.info("Creating project")
                                    CreateProjectWizard().openModal(modality = Modality.APPLICATION_MODAL)
                                }
                                item("Open...").action {
                                    log.info("Opening project")
                                }
                                menu("Open Recent") {

                                }

                                item("Settings").action {
                                    log.info("Opening settings")
                                    SettingsFragment().openModal()
                                }

                                separator()
                                item("Exit").action {
                                    log.info("Closing...")
                                    Platform.exit()
                                }
                            }
                            menu("Window") {
                                item("Close all").action {
                                }
                            }
                            menu("Help") {
                                item("About...").action {
                                    AboutFragment().openModal()
                                }
                            }
                        }
                        hbox {
                            style {
                                backgroundColor += Color.LIGHTGRAY
                            }
                            label("tool config go here")
                        }
                    }
                }

                left {
                    vbox {
                        spacing = 16.0
                        button("tool1")
                        button("tool2")
                        button("tool3")
                        button("tool4")
                        button("tool5")
                        button("tool6")
                        button("tool7")
                        button("tool8")
                        button("tool9")
                        button("tool10")
                        button("tool11")
                        button("tool12")
                        button("tool13")
                        button("tool14")
                    }
                }

                right {
                    vbox {
                        treeview<String> {
                            prefHeight = 300.0
                            root = TreeItem<String>("OH YEAH ROOT")
                            val maps = getMapList()
                            populate { parent ->
                                if (parent == root) maps else listOf()
                            }
                        }

                        flowpane {
                            label("tool info? idk")
                        }
                    }
                }

                bottom {
                    hbox {
                        spacing = 16.0
                        label("What is progressing?")
                        separator(Orientation.VERTICAL)
                        progressbar(0.5) {
                            style {
                                accentColor = c("#39b54a")
                            }
                        }
                    }
                }

                center {
                    tabpane {

                    }
                }
            }
}

fun getMapList(): List<String> {
    return listOf("POKEMON TOWN", "OH YEHA TOWN2", "ELITE 4 PLACE", "HALL OF U WIN")
}

class ProjectModel : ItemViewModel<Project>() {
    val name = bind { item?.name?.toProperty() }
    val filename = bind { item?.filename?.toProperty() }
    val absoluteFolderPath = bind { item?.absoluteFolderPath?.toProperty() }
    val absoluteOriginalRomPath = bind { item?.absoluteOriginalRomPath?.toProperty() }
    val game = bind { item?.game?.toProperty() }

    fun createItemFromProperties(): Project {
        return Project(name.getValue(), filename.getValue(), absoluteFolderPath.getValue(), absoluteOriginalRomPath.getValue(), game.getValue())
    }
}


fun main(args: Array<String>) {

}