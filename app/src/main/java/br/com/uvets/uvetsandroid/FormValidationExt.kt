package br.com.uvets.uvetsandroid

import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import bloder.com.blitzcore.mask.BlitzMaskFormatter
import bloder.com.blitzcore.validation.DefaultBlitzValidations
import com.google.android.material.chip.ChipGroup
import java.util.ArrayList
import kotlin.collections.HashSet

class AppFormValidations : DefaultBlitzValidations() {

    companion object {
        private const val CPF_REGEX = "(?!(\\d)\\1{2}.\\1{3}.\\1{3}-\\1{2})\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"

        private fun validateCPF(cpf: String): Boolean {
            val cleanCPF = cpf.replace(".", "").replace("-", "")

            val chars = cleanCPF.toCharArray()

            if (chars.size != 11) {
                return false
            }

            val integerList = toIntArray(chars)

            val set = HashSet<Int>(integerList)

            if (set.size <= 1) {
                return false
            }

            val soma1 = 11 - (integerList.get(0) * 10 +
                    integerList.get(1) * 9 +
                    integerList.get(2) * 8 +
                    integerList.get(3) * 7 +
                    integerList.get(4) * 6 +
                    integerList.get(5) * 5 +
                    integerList.get(6) * 4 +
                    integerList.get(7) * 3 +
                    integerList.get(8) * 2) % 11

            val dv1 = if (soma1 > 9) 0 else soma1

            val soma2 = 11 - (integerList.get(0) * 11 +
                    integerList.get(1) * 10 +
                    integerList.get(2) * 9 +
                    integerList.get(3) * 8 +
                    integerList.get(4) * 7 +
                    integerList.get(5) * 6 +
                    integerList.get(6) * 5 +
                    integerList.get(7) * 4 +
                    integerList.get(8) * 3 +
                    integerList.get(9) * 2) % 11

            val dv2 = if (soma2 > 9) 0 else soma2

            return dv1 == integerList.get(9) && dv2 == integerList.get(10)
        }

        private fun toIntArray(chars: CharArray): List<Int> {
            val integers = ArrayList<Int>(chars.size)

            for (i in chars.indices) {
                integers.add(i, Integer.valueOf(chars[i].toString()))
            }

            return integers
        }
    }

    fun EditText.isCpf(): EditText = bindViewValidation(this) {
        it.text.matches(Regex(CPF_REGEX)) && validateCPF(it.text.toString())
    }

    fun EditText.matches(editText: EditText): EditText = bindViewValidation(this) {
        it.text == editText.text
    }

    fun CheckBox.isAccepted(): View = bindViewValidation(this) {
        this.isChecked
    }

    fun RadioGroup.isAnySelected(): View = bindViewValidation(this) {
        this.checkedRadioButtonId != -1
    }

    fun ChipGroup.isAnySelected(): View = bindViewValidation(this) {
        this.checkedChipId != -1
    }
}

private object MaskDsl {

    private val textWatchers: MutableMap<EditText, TextWatcher> = mutableMapOf()

    fun injectMask(editText: EditText, textWatcher: TextWatcher) {
        editText.removeTextChangedListener(textWatchers[editText])
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        textWatcher.let {
            textWatchers[editText] = it
            editText.addTextChangedListener(it)
        }
    }
}

fun EditText.withMask(mask: String) = if (mask.isNotEmpty() && mask.isNotBlank()) {
    MaskDsl.injectMask(this, BlitzMaskFormatter(this, mask))
} else {
}