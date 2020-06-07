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

        for (y in 0 until screen.height) {
            for (x in 0 until screen.width) {
                val pixel = screen.topLeft + xVector * x.toFloat() + yVector * y.toFloat()
                val direction = (pixel - camera.position).normalized()
                val origin = Point3F(camera.position)
                image[y][x].set(traceRay(origin, direction))
            }
            onProgress?.invoke((y + 1) * 100 / screen.height)
        }

        return image
    }

    private fun traceRay(origin: Point3F, direction: Point3F, maxDepth: Int = 3): Point3F {
        val color = Point3F()
        var reflection = 1f

        for (k in 0..maxDepth) {
            var nearestObject: Object? = null
            var minDistance = Float.MAX_VALUE
            objects.forEach { obj ->
                obj.intersect(origin, direction)?.let {
                    if (it < minDistance) {
                        minDistance = it
                        nearestObject = obj
                    }
                }
            }

            nearestObject?.let {
                val intersection = origin + minDistance * direction
                val normal = it.getNormalVectorSurface(intersection)
                color.set(color + reflection * it.getLightColor(origin, direction, minDistance, this))
                origin.set(intersection + 1e-3f * normal)
                direction.set(direction.reflection(normal))
                reflection *= it.material.getReflectivity(intersection)
            } ?: break
        }

        return color.apply {
            x = x.coerceIn(0f, 1f)
            y = y.coerceIn(0f, 1f)
            z = z.coerceIn(0f, 1f)
        }
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
