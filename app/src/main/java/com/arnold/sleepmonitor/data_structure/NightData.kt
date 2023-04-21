package com.arnold.sleepmonitor.data_structure

data class NightData(
    var startTime: String,
    var endTime: String,
    var duration: Int,
    var meanLux: Int,
    var meanVolume: Double,
    var environmentScore: Int,
    var deepSleepRatio: Double,
    var lightSleepRatio: Double,
    var awakeCount: Int,
    var deepContinuesScore: Int,
    var respirationQualityScore: Int,
    var sleepScore: Int
)
