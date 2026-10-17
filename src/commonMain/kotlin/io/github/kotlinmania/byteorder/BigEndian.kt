package io.github.kotlinmania.byteorder

/**
 * Defines big-endian serialization.
 */
public object BigEndian : ByteOrder() {
    override fun readU16(buf: ByteArray, offset: Int): UShort {
        val b0 = buf[offset].toInt() and 0xFF
        val b1 = buf[offset + 1].toInt() and 0xFF
        return ((b0 shl 8) or b1).toUShort()
    }

    override fun readU24(buf: ByteArray, offset: Int): UInt {
        val b0 = buf[offset].toUInt() and 0xFFu
        val b1 = buf[offset + 1].toUInt() and 0xFFu
        val b2 = buf[offset + 2].toUInt() and 0xFFu
        return (b0 shl 16) or (b1 shl 8) or b2
    }

    override fun readI24(buf: ByteArray, offset: Int): Int {
        val u = readU24(buf, offset).toInt()
        return (u shl 8) shr 8
    }

    override fun readU32(buf: ByteArray, offset: Int): UInt {
        val b0 = buf[offset].toUInt() and 0xFFu
        val b1 = buf[offset + 1].toUInt() and 0xFFu
        val b2 = buf[offset + 2].toUInt() and 0xFFu
        val b3 = buf[offset + 3].toUInt() and 0xFFu
        return (b0 shl 24) or (b1 shl 16) or (b2 shl 8) or b3
    }

    override fun readU48(buf: ByteArray, offset: Int): ULong {
        val hi = readU16(buf, offset).toULong()
        val lo = readU32(buf, offset + 2).toULong()
        return (hi shl 32) or lo
    }

    override fun readI48(buf: ByteArray, offset: Int): Long {
        val u = readU48(buf, offset).toLong()
        return (u shl 16) shr 16
    }

    override fun readU64(buf: ByteArray, offset: Int): ULong {
        val hi = readU32(buf, offset).toULong()
        val lo = readU32(buf, offset + 4).toULong()
        return (hi shl 32) or lo
    }

    override fun writeU16(buf: ByteArray, n: UShort, offset: Int) {
        val v = n.toInt()
        buf[offset] = ((v ushr 8) and 0xFF).toByte()
        buf[offset + 1] = (v and 0xFF).toByte()
    }

    override fun writeU24(buf: ByteArray, n: UInt, offset: Int) {
        buf[offset] = ((n shr 16) and 0xFFu).toByte()
        buf[offset + 1] = ((n shr 8) and 0xFFu).toByte()
        buf[offset + 2] = (n and 0xFFu).toByte()
    }

    override fun writeU32(buf: ByteArray, n: UInt, offset: Int) {
        buf[offset] = ((n shr 24) and 0xFFu).toByte()
        buf[offset + 1] = ((n shr 16) and 0xFFu).toByte()
        buf[offset + 2] = ((n shr 8) and 0xFFu).toByte()
        buf[offset + 3] = (n and 0xFFu).toByte()
    }

    override fun writeU48(buf: ByteArray, n: ULong, offset: Int) {
        writeU16(buf, ((n shr 32) and 0xFFFFuL).toUShort(), offset)
        writeU32(buf, (n and 0xFFFF_FFFFuL).toUInt(), offset + 2)
    }

    override fun writeU64(buf: ByteArray, n: ULong, offset: Int) {
        writeU32(buf, ((n shr 32) and 0xFFFF_FFFFuL).toUInt(), offset)
        writeU32(buf, (n and 0xFFFF_FFFFuL).toUInt(), offset + 4)
    }
}
