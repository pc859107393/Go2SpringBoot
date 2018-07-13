package cn.acheng1314.base.utils


import cn.acheng1314.base.domain.wrap.ResponseWrapList
import com.baomidou.mybatisplus.plugins.Page
import com.baomidou.mybatisplus.plugins.pagination.Pagination
import com.google.gson.*
import com.google.gson.internal.ConstructorConstructor
import com.google.gson.internal.bind.CollectionTypeAdapterFactory
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.io.Reader
import java.lang.reflect.Type
import java.util.ArrayList
import com.google.gson.JsonElement
import com.google.gson.JsonArray
import org.springframework.http.HttpStatus

@SuppressWarnings("UNCHECKED_CAST")
object GsonUtil {

    private var gson: Gson? = null


    /**
     * 自定义TypeAdapter ,null对象将被解析成空字符串
     */
    private val STRING = object : TypeAdapter<String>() {
        override fun read(reader: JsonReader): String {
            try {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull()
                    return ""//原先是返回Null，这里改为返回空字符串
                }
                return reader.nextString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        override fun write(writer: JsonWriter, value: String?) {
            try {
                if (value == null) {
                    writer.nullValue()
                    return
                }
                writer.value(value)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 自定义adapter，解决由于数据类型为Int,实际传过来的值为Float，导致解析出错的问题
     * 目前的解决方案为将所有Int类型当成Double解析，再强制转换为Int
     */
    private val INTEGER = object : TypeAdapter<Number>() {
        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Number {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return 0
            }
            try {
                val i = `in`.nextDouble()
                return i.toInt()
            } catch (e: NumberFormatException) {
                throw JsonSyntaxException(e)
            }

        }

        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: Number) {
            out.value(value)
        }
    }

    init {
        val gsonBulder = GsonBuilder()
        gsonBulder.registerTypeAdapter(String::class.java, STRING)   //所有String类型null替换为字符串“”
        gsonBulder.registerTypeAdapter(Int::class.javaPrimitiveType, INTEGER) //int类型对float做兼容
        gsonBulder.setDateFormat("yyyy-MM-dd HH:mm:ss")
        //通过反射获取instanceCreators属性
        try {
            val builder = gsonBulder.javaClass as Class<*>
            val f = builder.getDeclaredField("instanceCreators")
            f.isAccessible = true
            //注册数组的处理器
            gsonBulder.registerTypeAdapterFactory(CollectionTypeAdapterFactory(ConstructorConstructor(f.get(gsonBulder) as Map<Type, InstanceCreator<Any>>)))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        gson = gsonBulder.create()
    }

    /**
     * Json字符串 转为指定对象
     *
     * @param json json字符串
     * @param type 对象类型
     * @param <T>  对象类型
     * @return
     * @throws JsonSyntaxException
    </T> */
    @Throws(JsonSyntaxException::class)
    fun <T> toBean(json: String, type: Class<T>): T {
        return gson!!.fromJson(json, type)
    }

    /**
     * 将jsonStr转换为javaBean
     *
     * @param object
     * @return json string
     */
    fun toJson(`object`: Any): String {
        return gson!!.toJson(`object`)
    }

    /**
     * 将jsonStr转换为javaBean
     *
     * @param json
     * @param type
     * @return instance of type
     */
    fun <V> fromJson(json: String, type: Class<V>): V {
        return gson!!.fromJson(json, type)
    }

    /**
     * 将jsonStr转换为javaBean
     *
     * @param json
     * @param type
     * @return instance of type
     */
    fun <V> fromJson(json: String, type: Type): V {
        return gson!!.fromJson(json, type)
    }

    /**
     * 将reader转换为javaBean
     *
     * @param reader
     * @param type
     * @return instance of type
     */
    fun <V> fromJson(reader: Reader, type: Class<V>): V {
        return gson!!.fromJson(reader, type)
    }

    /**
     * 将reader转换为javaBean
     *
     * @param reader
     * @param type
     * @return instance of type
     */
    fun <V> fromJson(reader: Reader, type: Type): V {
        return gson!!.fromJson(reader, type)
    }

//
//    /**
//     * 封装的转换json，外部一条语句调用
//     *
//     * @param object 需要写出的内容，code不成功则一致扔null
//     * @param code   数据状态高级枚举
//     * @param msg    不成功状态的提示信息
//     * @return
//     */
//    fun toJsonObjStr(`object`: Any?, code: ResponseCode, msg: String?): String {
//        val result = ResponseObj()
//        result.code = code.code
//        result.msg = if (StringUtils.isEmpty(msg)) code.msg else msg
//        if (code.code == ResponseCode.OK.code) {  //数据获取成功
//            result.data = `object`
//        }
//        return toJson(result)
//    }

    fun <T> toList(json: String, type: Class<T>): ArrayList<T>? {
        val list = ArrayList<T>()
        return try {
            val parser = JsonParser()
            parser.parse(json).asJsonArray.forEach { element -> list.add(gson!!.fromJson(element, type)) }
            ArrayList(list)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }
}