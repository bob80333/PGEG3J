package com.erice.PGEG3J.configuration

import java.io.File
import java.util.*

object Configure {
    private val config: HashMap<String, String> = hashMapOf()
    private const val delimiter = " -=:=- "
    private const val fileName = "PGEG3J.config"

    fun configure(name: String, value: String) {
        config[name] = value
    }

    fun retrieve(name: String, default: String = ""): String {
        return config.getOrDefault(name, default)
    }

    fun save() {
        val names = config.keys
        val outputList = mutableListOf<String>()
        names.forEach {
            outputList.add(it + delimiter + config[it])
        }
        val output = outputList.joinToString("\n")
        val file = File(javaClass.classLoader.getResource(fileName).toURI())
        file.writeText(output)
    }

    fun load() {
        val loader = Scanner(javaClass.classLoader.getResourceAsStream(fileName))
        while (loader.hasNextLine()) {
            val line = loader.nextLine()
            val halves = line.split(delimiter)
            if (halves.size > 2) error("Delimiter not unique in config file")
            config[halves[0]] = halves[1]
        }
    }
}