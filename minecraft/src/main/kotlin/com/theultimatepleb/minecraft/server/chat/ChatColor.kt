package com.theultimatepleb.minecraft.server.chat

/**
 * Created by Sebastian Agius on 10/09/2017.
 */
enum class ChatColor(val code: Char) {
    BLACK('0'), DARK_BLUE('1'), DARK_GREEN('2'), DARK_AQUA('3'), DARK_RED('4'), DARK_PURPLE('5'), GOLD('6'), GRAY('7'), DARK_GRAY('8'),
    BLUE('9'), GREEN('a'), AQUA('b'), RED('c'), LIGHT_PURPLE('d'), YELLOW('e'), WHITE('f'),

    OBFUSCATED('k'), BOLD('l'), STRIKETHROUGH('m'), UNDERLINE('n'), ITALIC('o'), RESET('r');

    fun nameL(): String {
        return name.toLowerCase()
    }

    companion object {
        fun byCode(code: Char): ChatColor? {
            return ChatColor.values().find { chatColor -> chatColor.code == code }
        }

        fun translateAlternateCharCode(code: Char, text: String): String {
            val array = text.toCharArray()
            array.indices.filter { array[it] == code && byCode(array[it +1]) != null }
                    .forEach { array[it] = CHAR_CODE }
            return String(array)
        }

        val CHAR_CODE = '§'
    }
}