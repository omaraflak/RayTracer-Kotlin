import geometry.Point3F
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import scene.Scene
import java.io.File
import javax.imageio.ImageIO

fun main() {
    "output/animation".also {
        File(it).mkdirs()
        createAnimation(it) { current, total ->
            println("$current/$total")
        }
    }
}

fun createAnimation(folder: String, onProgress: ((Int, Int) -> Unit)? = null) {
    var processed = 0
    val origin = Point3F(0.6f, 0.35f, -0.6f)
    val axis = Point3F.yUnit()

    Observable.range(0, 360)
        .flatMap {
            val scene = createScene()
            scene.camera.updatePosition(scene.camera.position.rotate(it.toFloat(), axis, origin))
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