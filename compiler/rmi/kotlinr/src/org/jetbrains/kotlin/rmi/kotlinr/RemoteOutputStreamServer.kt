/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.rmi.kotlinr

import org.jetbrains.kotlin.rmi.RemoteOutputStream
import org.jetbrains.kotlin.rmi.clientLoopbackSocketFactory
import org.jetbrains.kotlin.rmi.serverLoopbackSocketFactory
import java.io.OutputStream
import java.rmi.server.UnicastRemoteObject


class RemoteOutputStreamServer(val out: OutputStream, port: Int = 0) : RemoteOutputStream {

    init {
        UnicastRemoteObject.exportObject(this, port, clientLoopbackSocketFactory, serverLoopbackSocketFactory)
    }

    public fun disconnect() {
        UnicastRemoteObject.unexportObject(this, true)
    }

    override fun close() {
        out.close()
    }

    override fun write(data: ByteArray, offset: Int, length: Int) {
        out.write(data, offset, length)
    }

    override fun write(dataByte: Int) {
        out.write(dataByte)
    }
}
