package org.jetbrains.greeting.responses

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class TrackAudioAnalysisAndroid(
    @Ignore val bars: List<Bar>,
    @Ignore val beats: List<Beat>,
    @Ignore val meta: Meta,
    @Ignore val sections: List<Section>,
    @Ignore val segments: List<Segment>,
    @Ignore val tatums: List<Tatum>,
    override val track: Track
): ITrackAudioAnalysis()

@Entity
@Serializable
data class TrackAndroid(
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
    @PrimaryKey override val num_samples: Int,
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
): ITrack()