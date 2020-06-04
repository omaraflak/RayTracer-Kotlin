package materials

import geometry.Point3F
import scene.lights.Light

interface Material {
    fun getColor(point: Point3F, normal: Point3F, toLight: Point3F, toCamera: Point3F, light: Light): Point3F
}
