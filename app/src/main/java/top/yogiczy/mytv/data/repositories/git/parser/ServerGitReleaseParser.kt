package top.yogiczy.mytv.data.repositories.git.parser

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import top.yogiczy.mytv.data.entities.GitRelease

/**
 * 自有服务器发行版解析
 *
 * update.json 格式：
 * {"version": "1.4.5", "downloadUrl": "https://lihansong.top/tvzy/mytv.apk", "description": "更新说明"}
 */
class ServerGitReleaseParser : GitReleaseParser {
    override fun isSupport(url: String): Boolean {
        return url.contains("lihansong.top")
    }

    override suspend fun parse(data: String): GitRelease {
        val json = Json.parseToJsonElement(data).jsonObject

        return GitRelease(
            version = json.getValue("version").jsonPrimitive.content.trimStart('v', 'V'),
            downloadUrl = json.getValue("downloadUrl").jsonPrimitive.content,
            description = json["description"]?.jsonPrimitive?.content ?: "",
        )
    }
}
