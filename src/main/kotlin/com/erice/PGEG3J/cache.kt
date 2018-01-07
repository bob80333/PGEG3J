package com.erice.PGEG3J

import java.io.File
import java.util.*

class Cacher {
    private val cache: HashMap<String, String> = hashMapOf()
    private val delimiter = " -=:=- "
    private val fileName = "PGEG3J.cache"

    public fun cache(name: String, value: String) {
        cache[name] = value
    }

    public fun retrieve(name: String, default: String = ""): String {
        return cache.getOrDefault(name, default)
    }

    public fun save() {
        val names = cache.keys
        val outputList = mutableListOf<String>()
        names.forEach {
            outputList.add(it + delimiter + cache[it])
        }
        val output = outputList.joinToString("\n")
        val file = File(javaClass.classLoader.getResource(fileName).toURI())
        file.writeText(output)
    }

    public fun load() {
        val loader = Scanner(javaClass.classLoader.getResourceAsStream(fileName))
        while (loader.hasNextLine()) {
            val line = loader.nextLine()
            val halves = line.split(delimiter)
            if (halves.size > 2) error("Delimiter not unique in config file")
            cache.put(halves[0], halves[1])
        }
    }
}