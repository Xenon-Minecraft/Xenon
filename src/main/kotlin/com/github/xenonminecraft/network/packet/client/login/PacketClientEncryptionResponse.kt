/*
 * MIT License
 *
 * Copyright (c) 2018 Xenon and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.xenonminecraft.network.packet.client.login

import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.readSpecialByteArray
import com.github.xenonminecraft.network.util.readVarInt
import io.netty.buffer.ByteBuf
import java.security.PrivateKey

/**
 * Specification: https://wiki.vg/Protocol#Encryption_Response
 *
 * Shared Secret Length: VarInt
 * Shared Secret: ByteArray
 * Verify Token Length: VarInt
 * Verify Token: ByteArray
 */
@PacketInfo(0x01)
class PacketClientEncryptionResponse(var sharedSecret: ByteArray? = null,
                                     var verifyToken: ByteArray? = null) : Packet() {
    override fun decode(byteBuf: ByteBuf) {
        sharedSecret = byteBuf.readSpecialByteArray()
        verifyToken = byteBuf.readSpecialByteArray()
    }

    fun getToken(key: PrivateKey) = Xenon.instance!!.encryptionManager.decryptData(key, verifyToken!!)

    fun getSecretKey(key: PrivateKey) = Xenon.instance!!.encryptionManager.decryptSharedKey(key, sharedSecret!!)
}