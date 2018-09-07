package com.github.xenonminecraft.network.util

import io.netty.util.internal.StringUtil
import java.math.BigInteger
import java.security.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Sebastian Agius on 10/09/2017.
 */
class EncryptionManager {
    lateinit var keyPair: KeyPair
    val privateKey
        get() = checkNotNull(keyPair.private)
    val publicKey
        get() = checkNotNull(keyPair.public)
    private val generator = KeyPairGenerator.getInstance("RSA").apply { initialize(1024) }
    val secureRandom = SecureRandom()

    fun generateKeyPair() {
        keyPair = generator.genKeyPair()
    }

    fun publicKeyAsPEM() = "-----BEGIN PUBLIC KEY-----".plus(StringUtil.NEWLINE)
            .plus(String(Base64.getEncoder().encode(publicKey.encoded))).plus(StringUtil.NEWLINE)
            .plus("-----END PUBLIC KEY-----")

    fun generateByteArray(size: Int): ByteArray {
        val array = ByteArray(size)
        secureRandom.nextBytes(array)
        return array
    }

    fun decryptData(key: Key, data: ByteArray) = cipherOperation(2, key, data)

    fun encryptData(key: Key, data: ByteArray) = cipherOperation(1, key, data)

    fun decryptSharedKey(key: PrivateKey, sharedKeyEncrypted: ByteArray) =
            SecretKeySpec(decryptData(key, sharedKeyEncrypted), "AES")

    fun cipherOperation(mode: Int, key: Key, data: ByteArray) = createCipherInstance(mode, key.algorithm, key).doFinal(data)

    fun createCipherInstance(mode: Int, transformation: String, key: Key): Cipher {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(mode, key)
        return cipher
    }

    fun createNetCipher(mode: Int, key: Key): Cipher {
        try {
            val cipher = Cipher.getInstance("AES/CFB8/NoPadding")
            cipher.init(mode, key, IvParameterSpec(key.encoded))
            return cipher
        } catch(e: Exception) {
            throw e
        }
    }

    fun getServerIdHash(serverId: String, publicKey: PublicKey, secretKey: SecretKey)  =
            digestOperation("SHA-1", serverId.toByteArray(
                    charset("ISO_8859_1")), secretKey.encoded, publicKey.encoded)

    fun hashToString(data: ByteArray) = BigInteger(data).toString(16)

    private fun digestOperation(algorithm: String, vararg data: ByteArray): ByteArray {
        try {
            val messagedigest = MessageDigest.getInstance(algorithm)

            for (byte in data) {
                messagedigest.update(byte)
            }

            return messagedigest.digest()
        } catch (e: NoSuchAlgorithmException) {
            throw e
        }

    }

}