package org.jetbrains.greeting.responses

import io.github.aakira.napier.Napier


enum class MusicalKey(val value: Int) {
    UNKNOWN(-1),
    C(0),
    C_SHARP(1),
    D(2),
    D_SHARP(3),
    E(4),
    F(5),
    F_SHARP(6),
    G(7),
    G_SHARP(8),
    A(9),
    A_SHARP(10),
    B(11);

    //15

    fun toDisplayString(): String {
        return when(this){
            UNKNOWN -> "?"
            C -> "C"
            C_SHARP -> "C#"
            D -> "D"
            D_SHARP -> "D#"
            E -> "E"
            F -> "F"
            F_SHARP -> "F#"
            G -> "G"
            G_SHARP -> "G#"
            A -> "A"
            A_SHARP -> "A#"
            B -> "B"
        }
    }

    fun getRelativeKey(mode: MusicalMode): MusicalKey {

        var steps = 0

        steps = when (mode) {
            MusicalMode.MAJOR -> {
                value + 9
            }

            MusicalMode.MINOR -> {
                value + 3
            }
        }
        steps %= 12

        return fromInt(steps)
    }

    /***
     * This represents the position of the wheel relative to the original mode
     * e.g. originalMode of the track was MAJOR and the current displayed mode is MAJOR
     * Then we need to turn the wheel 3 steps to the right to get to the relative minor
     * e.g. if originalMode is MAJOR and the current displayed mode is MINOR
     * We need to reset the wheel to position 0, which is the root note of the major scale
     * Very confusing and bad code.
     *
     * TODO where the fuck should this live?
     * It has nothing to do with keys, or modes really
     * It is a ui helper really
     */
    fun getRelativeOffset(currentMode: MusicalMode, originalMode: MusicalMode): Int {
        return when(originalMode to currentMode){
            MusicalMode.MAJOR to MusicalMode.MAJOR -> 3
            MusicalMode.MAJOR to MusicalMode.MINOR -> 0
            MusicalMode.MINOR to MusicalMode.MAJOR -> 0
            MusicalMode.MINOR to MusicalMode.MINOR -> -3
            else -> {
                 Napier.d("Unknown mode combination")
                0
            }
        }
    }

    companion object {
//        entries: This is a built-in method for enums in Kotlin, returning an array of all enum constants.
//        associateBy(PitchClass::value): This function creates a map where the keys are the integer values (value property of the MusicalKey
        //
        //
        //
        //
        //
        //
        //
        //
        //        )
//        and the values are the corresponding enum instances.
//        Essentially, it maps each integer (from -1 to 11) to its corresponding PitchClass.
        private val map = entries.associateBy(MusicalKey::value)
        fun fromInt(value: Int) = map[value] ?: UNKNOWN
    }
}