package com.example.meetexpress

import io.kotlintest.shouldBe
import io.kotlintest.specs.AbstractAnnotationSpec
import io.kotlintest.specs.StringSpec

class SignUpTest: StringSpec() {

    init {

        run {
            val activity: SignUpActivity = SignUpActivity()

            "set accout with correct data"{
                "xD".length shouldBe 2
                activity.validateFirstStep("maciek@bartosik.wtf", "password1", "password1") shouldBe true
            }
        }
    }

}