package labs.pooh.client.infrastructure

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.google.gson.stream.JsonToken.NULL
import java.io.IOException
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class LocalDateTimeAdapter(private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME) : TypeAdapter<LocalDateTime>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: LocalDateTime?) {
        if (value == null) {
            out?.nullValue()
        } else {
            out?.value(formatter.format(value))
        }
    }

    @Throws(IOException::class)
    override fun read(out: JsonReader?): LocalDateTime? {
        out ?: return null

        when (out.peek()) {
            NULL -> {
                out.nextNull()
                return null
            }
            else -> {
                return LocalDateTime.parse(out.nextString(), formatter)
            }
        }
    }
}
