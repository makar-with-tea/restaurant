package org.example.service

class Cryptographer(private val cypherWord : String = "VerySafeOption",
    private val alphabet : String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789") {
    companion object {
        private var instance: Cryptographer = Cryptographer()
        fun getInstance() : Cryptographer {
            return instance
        }
    }

    fun cypherPassword(password: String) : String {
        var res : String = ""
        var ind : Int = 0
        for (i in password.indices) {
            res += ('a' + (password[i].code + cypherWord[ind].code) % alphabet.length)
            ind = (ind + 1) % cypherWord.length
        }
        return res
    }

    fun decypherWord(word: String) : String {
        var res : String = ""
        var ind : Int = 0
        for (i in word.indices) {
            res += ('a' + (word[i].code - cypherWord[ind].code + alphabet.length) % alphabet.length)
            ind = (ind + 1) % cypherWord.length
        }
        return res
    }
}