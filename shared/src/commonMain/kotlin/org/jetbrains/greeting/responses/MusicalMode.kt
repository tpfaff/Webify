package org.jetbrains.greeting.responses

import org.jetbrains.greeting.responses.MusicalMode.NoteIntervalType.*

enum class MusicalMode(mode: Int) {
    MINOR(0),
    MAJOR(1);

    val modePattern: ModePattern by lazy {
        when (this) {
            MINOR -> ModePattern.MinorPattern()
            MAJOR -> ModePattern.MajorPattern()
        }
    }

    fun getRelativeMode() : MusicalMode {
        return when (this) {
            MINOR -> MAJOR
            MAJOR -> MINOR
        }
    }



    enum class NoteIntervalType(step: Char) {
        ROOT('R'),
        WHOLE('W'),
        HALF('H');
    }

    enum class ChordType(type: String) {
        MAJOR("Major"),
        MINOR("Minor"),
        AUGMENTED("Augmented"),
        DIMINISHED("Diminished"),
    }

    fun toDisplayString(): String {
        return when (this) {
            MINOR -> "Minor"
            MAJOR -> "Major"
        }
    }


    companion object {
        fun fromInt(mode: Int): MusicalMode {
            return when (mode) {
                0 -> MINOR
                1 -> MAJOR
                else -> throw IllegalArgumentException("Invalid mode")
            }
        }
    }

    sealed class ModePattern {
        abstract val noteIntervals: List<NoteIntervalType>
        abstract val chordIntervals: List<ChordType>

        class MajorPattern() : ModePattern() {
            override val noteIntervals = listOf(ROOT, WHOLE, WHOLE, HALF, WHOLE, WHOLE, WHOLE)
            override val chordIntervals = listOf(
                ChordType.MAJOR,
                ChordType.MINOR,
                ChordType.MINOR,
                ChordType.MAJOR,
                ChordType.MAJOR,
                ChordType.MINOR,
                ChordType.DIMINISHED
            )
        }

        class MinorPattern : ModePattern() {
            override val noteIntervals = listOf(
                ROOT, WHOLE, HALF, WHOLE, WHOLE, HALF,
                WHOLE
            )
            override val chordIntervals = listOf(
                ChordType.MINOR,
                ChordType.DIMINISHED,
                ChordType.MAJOR,
                ChordType.MINOR,
                ChordType.MINOR,
                ChordType.MAJOR,
                ChordType.MAJOR
            )

        }
//
//        fun getRelativeKey(mode: MusicalMode): MusicalKey {
//
//            var steps = 0
//
//            steps = when (mode) {
//                MusicalMode.MAJOR -> {
//                    value + 9
//                }
//
//                MusicalMode.MINOR -> {
//                    value + 3
//                }
//            }
//            steps %= 12
//
//            return MusicalKey.fromInt(steps)
//        }
//
//        fun getRelativeOffset(mode: MusicalMode, originalMode: MusicalMode): Int {
//            var steps = 0
//
//            if(originalMode == MusicalMode.MAJOR) {
//                steps = when (mode) {
//                    MusicalMode.MAJOR -> {
//                         println("Mode is Major and steps are ${value + 9}")
//                        3
//                    }
//
//                    MusicalMode.MINOR -> {
//                        //this only works when moving from major to minor
//                        0
//                    }
//                }
//            }
//            if(originalMode == MusicalMode.MINOR) {
//                steps = when (mode) {
//                    MusicalMode.MAJOR -> {
//                         println("Mode is Major and steps are ${value + 9}")
//                        0
//                    }
//
//                    MusicalMode.MINOR -> {
//                        //this only works when moving from major to minor
//                        -3
//                    }
//                }
//            }
//
//            return steps
//        }
    }
}

