package scene

import geometry.Point3F
import geometry.times
import objects.Object
import scene.lights.Light
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Scene(
    val camera: Camera,
    val lights: List<Light>,
    val objects: List<Object>
) {
    fun render(onProgress: ((Int) -> Unit)? = null): List<List<Point3F>> {
        val screen = camera.screen
        val image = List(screen.height) {
            List(screen.width) {
                Point3F()
            }
        }

        val xVector = (screen.topRight - screen.topLeft) * (1f / screen.width)
        val yVector = (screen.bottomLeft - screen.topLeft) * (1f / screen.height)

        for (i in 0 until screen.height) {
            for (j in 0 until screen.width) {
                var color = Point3F()
                val pixel = screen.topLeft + xVector * j.toFloat() + yVector * i.toFloat()
                val ray = (pixel - camera.position).normalized()
                val origin = Point3F(camera.position)
                var reflection = 1f

                for (depth in 0..3) {
                    var minIndex: Int? = null
                    var minDistance = Float.MAX_VALUE
                    objects.forEachIndexed { index, obj ->
                        obj.intersect(origin, ray)?.let {
                            if (it < minDistance) {
                                minDistance = it
                                minIndex = index
                            }
                        }
                    }

                    minIndex?.let {
                        color += reflection * objects[it].getLightColor(origin, ray, minDistance, this)
                        val intersection = origin + minDistance * ray
                        val normal = objects[it].getNormalVectorSurface(intersection)
                        origin.set(intersection + 1e-3f * normal)
                        ray.set(ray.reflection(normal))
                        reflection *= objects[it].material.getReflectivity(intersection)
                    }

                    if (minIndex == null) {
                        break
                    }
                }

                color.apply {
                    x = x.coerceIn(0f, 1f)
                    y = y.coerceIn(0f, 1f)
                    z = z.coerceIn(0f, 1f)
                }

                image[i][j].set(color)
            }
            onProgress?.invoke((i + 1) * 100 / screen.height)
        }

        return image
    }

    fun renderToFile(filepath: String, onProgress: ((Int) -> Unit)? = null) {
        val image = render(onProgress)
        val buffer = toBufferedImage(image)
        val file = File(filepath)
        ImageIO.write(buffer, "png", file)
    }

    companion object {
        fun toBufferedImage(image: List<List<Point3F>>): BufferedImage {
            val height = image.size
            val width = image.first().size
            return BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR).also {
                image.forEachIndexed { y, list ->
                    list.forEachIndexed { x, p ->
                        val red = (p.x * 255).toInt()
                        val green = (p.y * 255).toInt()
                        val blue = (p.z * 255).toInt()
                        val color = (red shl 16) or (green shl 8) or blue
                        it.setRGB(x, y, color)
                    }
                }
            }
        }
    }
}
