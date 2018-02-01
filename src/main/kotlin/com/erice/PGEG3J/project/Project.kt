package com.erice.PGEG3J.project

import com.erice.PGEG3JL.Game
import com.erice.PGEG3JL.MB
import com.erice.PGEG3JL.Rom
import com.erice.PGEG3JL.findGame
import java.io.FileInputStream
import java.io.FileReader
import java.io.FileWriter

class Project(var name: String, var filename: String, var absoluteFolderPath: String, var absoluteOriginalRomPath: String, var game: Game) {

    init {
        if (game == Game.AutoDetect) {
            val data = ByteArray(16 * MB)
            FileInputStream(absoluteOriginalRomPath).read(data)
            val rom = Rom(name, data)
            game = findGame(rom)
        }
    }
    companion object {
        private const val delimiter = " := "
        fun loadProject(projectFilePath: String): Project {
            val fileReader = FileReader(projectFilePath)
            val lines = fileReader.readLines()

            val name = lines[0].split(delimiter)[1]
            val fileName = lines[1].split(delimiter)[1]
            val absoluteFolderPath = lines[2].split(delimiter)[1]
            val absoluteOriginalRomPath = lines[3].split(delimiter)[1]
            val game = Game.valueOf(lines[4].split(delimiter)[1])

            return Project(name, fileName, absoluteFolderPath, absoluteOriginalRomPath, game)
        }
    }

    private fun saveProjectFile() {
        val fileWriter = FileWriter(absoluteFolderPath + filename)

        fileWriter.write("name := $name\n")
        fileWriter.write("filename := $filename\n")
        fileWriter.write("absoluteFolderPath := $absoluteFolderPath\n")
        fileWriter.write("absoluteOriginalRomPath := $absoluteOriginalRomPath\n")
        fileWriter.write("game := ${game.gameId}")

        fileWriter.flush()
        fileWriter.close()
    }


}