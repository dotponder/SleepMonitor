package com.arnold.sleepmonitor.process

import com.arnold.sleepmonitor.data_structure.NightData
import com.arnold.sleepmonitor.data_structure.SingleTimeData
import com.arnold.sleepmonitor.data_structure.SingleUnitData
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf

class Convert {
    private val calculator = Calculator()
    /*
    fun singleTime2Unit(singleTimeData: List<SingleTimeData>) : SingleUnitData {
        val singleUnitData = SingleUnitData(
            "0",
            0.0,
            0,
            0,
            0,
            0.0,
            0,
        )

        singleUnitData.time = singleTimeData[0].time
        singleUnitData.meanLux = singleTimeData.map { it.lux }.average()
        singleUnitData.movesCount = calculator.movesCount(singleTimeData)
        singleUnitData.snoreCount = calculator.snoreCount(singleTimeData)
        singleUnitData.meanEnvironmentVolume = singleTimeData.map { it.volume }.average()

        return singleUnitData
    }

    fun singleUnit2Night(singleUnitData: List<SingleUnitData>) : NightData {
        val nightData = NightData(
            "0",
            "0",
            0,
            0,
            0.0,
            0,
            0.0,
            0.0,
            0,
            0,
            0,
            0
        )

        nightData.startTime = singleUnitData[0].time
        nightData.endTime = singleUnitData[singleUnitData.size - 1].time
        // ai generated, need to be modified
        nightData.duration = singleUnitData.size
        nightData.meanLux = singleUnitData.map { it.meanLux }.average().toInt()
        nightData.meanVolume = singleUnitData.map { it.meanEnvironmentVolume }.average()
        nightData.environmentScore = singleUnitData.map { it.meanLux }.average().toInt()
        nightData.deepSleepRatio = singleUnitData.filter { it.meanLux < 0.1 }.size.toDouble() / singleUnitData.size
        nightData.lightSleepRatio = singleUnitData.filter { it.meanLux > 0.1 }.size.toDouble() / singleUnitData.size
        //nightData.awakeCount = singleUnitData.map { it.awakeCount }.average().toInt()
        nightData.deepContinuesScore = singleUnitData.filter { it.meanLux < 0.1 }.size
        nightData.respirationQualityScore = singleUnitData.map { it.meanEnvironmentVolume }.average().toInt()
        nightData.sleepScore = singleUnitData.map { it.meanLux }.average().toInt()

        return nightData
    }

    fun singleUnit2DataFrame(list: List<SingleUnitData>) : AnyFrame {
        val time by column(list.map { it.time })
        val meanLux by column(list.map { it.meanLux })
        val movesCount by column(list.map { it.movesCount })
        val SnoreCount by column(list.map { it.snoreCount })
        val meanEnvironmentVolume by column(list.map { it.meanEnvironmentVolume })
        val noiseVolume by column(list.map { it.noiseVolume })
        val noiseCount by column(list.map { it.noiseCount })
        val awakeCount by column(list.map { it.awakeCount })

        return dataFrameOf(
            time, meanLux, movesCount, SnoreCount, meanEnvironmentVolume, noiseVolume, noiseCount, awakeCount
        )
    }

    fun dataFrame2SingleUnit(dataFrame: DataFrame<*>) : List<SingleUnitData> {
        val time = dataFrame["time"].toList()
        val meanLux = dataFrame["meanLux"].toList()
        val movesCount = dataFrame["movesCount"].toList()
        val snoreCount = dataFrame["snoreCount"].toList()
        val meanEnvironmentVolume = dataFrame["meanEnvironmentVolume"].toList()
        val noiseVolume = dataFrame["noiseVolume"].toList()
        val noiseCount = dataFrame["noiseCount"].toList()
        val awakeCount = dataFrame["awakeCount"].toList()

        val list = mutableListOf<SingleUnitData>()
        for (i in time.indices) {
            list.add(
                SingleUnitData(
                    time[i].toString(),
                    meanLux[i].toString().toDouble(),
                    movesCount[i].toString().toInt(),
                    snoreCount[i].toString().toInt(),
                    meanEnvironmentVolume[i].toString().toDouble(),
                    noiseVolume[i].toString().toDouble(),
                    noiseCount[i].toString().toInt(),
                    awakeCount[i].toString().toInt()
                )
            )
        }

        return list
    }*/
}
