package com.simple1c.configuration

import org.picocontainer.PicoContainer

interface ApplicationConfigurator {
    fun configure(container: PicoContainer)
}