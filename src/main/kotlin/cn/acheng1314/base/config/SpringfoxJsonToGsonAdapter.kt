package cn.acheng1314.base.config

import java.lang.reflect.Type

import com.google.gson.*
import springfox.documentation.spring.web.json.Json

class SpringfoxJsonToGsonAdapter : com.google.gson.JsonSerializer<Json> {

        override fun serialize(json: Json, type: Type, context: JsonSerializationContext): JsonElement {
                val parser = JsonParser()
                return parser.parse(json.value())
        }

}