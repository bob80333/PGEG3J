package com.erice.PGEG3J

import com.erice.PGEG3J.configuration.SettingsFragment
import com.erice.PGEG3J.project.CreateProjectWizard
import com.erice.PGEG3J.project.Project
import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.control.TreeItem
import javafx.scene.paint.Color
import javafx.stage.Modality
import tornadofx.*

class MapWorkspace : Fragment("PGEG3J") {
    val projectController: ProjectController by inject()
    val mapController: MapController by inject()
    private val mapWorkspace = this
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
                                    CreateProjectWizard(mapWorkspace).openModal(modality = Modality.APPLICATION_MODAL)
                                }
                                item("Open...").action {
                                    log.info("Opening project")
                                }
                                menu("Open Recent") {

                                }

                                item("Settings").action {
                                    log.info("Opening settings")
                                    SettingsFragment(mapWorkspace).openModal()
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
                        treeview<Any> {
                            projectController.mapsAndBanks = this
                            prefHeight = 300.0
                            root = TreeItem<Any>(Label("OH YEAH ROOT"))
                            isShowRoot = false
                            val test = Label("Test")

                            val test2 = Label("Test2.")

                            populate {
                                if (it == root) listOf(test, test2) else listOf()
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
                        mapController.tabPane = this
                        mapController.selectionModel = this.selectionModel
                    }
                }
            }

    fun changeProjectName(title: String) {
        this.title = "PGEG3J | " + title
    }
}

class ProjectModel : ItemViewModel<Project>() {

    val name = bind { item?.name?.toProperty() }
    val filename = bind { item?.filename?.toProperty() }
    val absoluteFolderPath = bind { item?.absoluteFolderPath?.toProperty() }
    val absoluteOriginalRomPath = bind { item?.absoluteOriginalRomPath?.toProperty() }
    val game = bind { item?.game?.toProperty() }

    fun createItemFromProperties(): Project {
        return Project(name.value, filename.value, absoluteFolderPath.value, absoluteOriginalRomPath.value, game.value)
    }
}


fun main(args: Array<String>) {

}