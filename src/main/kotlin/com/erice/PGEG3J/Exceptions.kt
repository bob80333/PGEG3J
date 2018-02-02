package com.erice.PGEG3J

class InvalidRomException(reason: String) : Exception("This rom is invalid, because " + reason)