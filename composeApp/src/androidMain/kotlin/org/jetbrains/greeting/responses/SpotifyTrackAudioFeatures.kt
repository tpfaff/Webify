package org.jetbrains.greeting.responses

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class TrackAudioAnalysis(
    @Ignore val bars: List<Bar>,
    @Ignore val beats: List<Beat>,
    @Ignore val meta: Meta,
    @Ignore val sections: List<Section>,
    @Ignore val segments: List<Segment>,
    @Ignore val tatums: List<Tatum>,
    val track: Track
)

@Serializable
data class Bar(
    val confidence: Double,
    val duration: Double,
    val start: Double
)

@Serializable
data class Beat(
    val confidence: Double,
    val duration: Double,
    val start: Double
)

@Serializable
data class Meta(
    val analysis_time: Double,
    val analyzer_version: String,
    val detailed_status: String,
    val input_process: String,
    val platform: String,
    val status_code: Int,
    val timestamp: Int
)

@Serializable
data class Section(
    val confidence: Float,
    val duration: Double,
    val key: Int,
    val key_confidence: Double,
    val loudness: Double,
    val mode: Int,
    val mode_confidence: Double,
    val start: Float,
    val tempo: Double,
    val tempo_confidence: Double,
    val time_signature: Int,
    val time_signature_confidence: Float
)

@Serializable
data class Segment(
    val confidence: Double,
    val duration: Double,
    val loudness_end: Float,
    val loudness_max: Double,
    val loudness_max_time: Double,
    val loudness_start: Double,
    val pitches: List<Double>,
    val start: Double,
    val timbre: List<Double>
)

@Serializable
data class Tatum(
    val confidence: Double,
    val duration: Double,
    val start: Double
)

@Entity
@Serializable
data class Track(
    val analysis_channels: Int,
    val analysis_sample_rate: Int,
    val code_version: Double,
    val codestring: String,
    val duration: Double,
    val echoprint_version: Double,
    val echoprintstring: String,
    val end_of_fade_in: Float,
    @Serializable(with = MusicalKeySerializer::class) var key: MusicalKey,
    val key_confidence: Float,
    val loudness: Double,
    @Serializable(with = ModeSerializer::class)
    var mode: MusicalMode,
    val mode_confidence: Double,
    @PrimaryKey val num_samples: Int,
    val offset_seconds: Int,
    val rhythm_version: Float,
    val rhythmstring: String,
    val sample_md5: String,
    val start_of_fade_out: Double,
    val synch_version: Float,
    val synchstring: String,
    val tempo: Double,
    val tempo_confidence: Double,
    val time_signature: Int,
    val time_signature_confidence: Double,
    val window_seconds: Int,
    var originalMode: MusicalMode? = null,
)