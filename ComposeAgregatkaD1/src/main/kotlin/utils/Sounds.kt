package utils

import androidx.compose.ui.res.useResource
import java.io.File
import javax.sound.sampled.AudioSystem


fun sound_On() {
    return
    val lol = File("raw/tesla_autopilot_on.wav")
//    val a = useResource("raw/tesla_autopilot_on.wav",{
//        lol =
//    })


    try {
        val clip = AudioSystem.getClip()
        clip.open(AudioSystem.getAudioInputStream(lol))
        clip.start()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
fun sound_Error() {
    return
    val lol = File("raw/tesla_err.wav")

    try {
        val clip = AudioSystem.getClip()
        clip.open(AudioSystem.getAudioInputStream(lol))
        clip.start()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}