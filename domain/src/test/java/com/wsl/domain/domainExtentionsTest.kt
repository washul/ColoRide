package com.wsl.domain

import com.google.common.truth.Truth.assertThat
import com.wsl.domain.utils.fromJsonToCity
import org.junit.Test

class DomainExtensionsTest {

    @Test
    fun `provide a JSON to convert it to a City object`() {
        val json: String = "{name: GDL, image: null}"
        val city = json.fromJsonToCity()
        assertThat(city.name).isEqualTo("GDL")
    }

}