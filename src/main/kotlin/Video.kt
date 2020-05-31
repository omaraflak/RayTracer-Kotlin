import geometry.Point3F
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import scene.Scene
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    createAnimation("output/anim/images") { current, total ->
        println("$current/$total")
    }
}

fun createAnimation(folder: String, onProgress: ((Int, Int) -> Unit)? = null) {
    var processed = 0

    Observable.range(0, 360)
        .flatMap {
            val scene = createScene()
            scene.camera.position.apply { set(rotateY(it.toFloat())) }
            Observable.fromCallable {
                Pair(it, Scene.toBufferedImage(scene.render()))
            }.subscribeOn(Schedulers.computation())
        }
        .observeOn(Schedulers.io())
        .blockingSubscribe {
            val file = File("$folder/image_${it.first}.png")
            ImageIO.write(it.second, "png", file)
            onProgress?.invoke(++processed, 360)
        }
}

fun Point3F.rotateY(angle: Float): Point3F {
    val rad = (angle * PI / 180).toFloat()
    return Point3F(
        cos(rad) * x - sin(rad) * z,
        y,
        sin(rad) * x + cos(rad) * z
    )
}

