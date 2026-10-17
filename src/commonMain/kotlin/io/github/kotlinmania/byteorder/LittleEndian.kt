package io.github.kotlinmania.byteorder

/**
 * Defines little-endian serialization.
 */
public object LittleEndian : ByteOrder() {
    override fun readU16(buf: ByteArray, offset: Int): UShort {
        val b0 = buf[offset].toInt() and 0xFF
        val b1 = buf[offset + 1].toInt() and 0xFF
        return (b0 or (b1 shl 8)).toUShort()
    }

    override fun readU24(buf: ByteArray, offset: Int): UInt {
        val b0 = buf[offset].toUInt() and 0xFFu
        val b1 = buf[offset + 1].toUInt() and 0xFFu
        val b2 = buf[offset + 2].toUInt() and 0xFFu
        return b0 or (b1 shl 8) or (b2 shl 16)
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
        return b0 or (b1 shl 8) or (b2 shl 16) or (b3 shl 24)
    }

    override fun readU48(buf: ByteArray, offset: Int): ULong {
        val lo = readU32(buf, offset).toULong()
        val hi = readU16(buf, offset + 4).toULong()
        return lo or (hi shl 32)
    }

    override fun readI48(buf: ByteArray, offset: Int): Long {
        val u = readU48(buf, offset).toLong()
        return (u shl 16) shr 16
    }

    override fun readU64(buf: ByteArray, offset: Int): ULong {
        val lo = readU32(buf, offset).toULong()
        val hi = readU32(buf, offset + 4).toULong()
        return lo or (hi shl 32)
    }

    override fun writeU16(buf: ByteArray, n: UShort, offset: Int) {
        val v = n.toInt()
        buf[offset] = (v and 0xFF).toByte()
        buf[offset + 1] = ((v ushr 8) and 0xFF).toByte()
    }

    override fun writeU24(buf: ByteArray, n: UInt, offset: Int) {
        buf[offset] = (n and 0xFFu).toByte()
        buf[offset + 1] = ((n shr 8) and 0xFFu).toByte()
        buf[offset + 2] = ((n shr 16) and 0xFFu).toByte()
    }

    override fun writeU32(buf: ByteArray, n: UInt, offset: Int) {
        buf[offset] = (n and 0xFFu).toByte()
        buf[offset + 1] = ((n shr 8) and 0xFFu).toByte()
        buf[offset + 2] = ((n shr 16) and 0xFFu).toByte()
        buf[offset + 3] = ((n shr 24) and 0xFFu).toByte()
    }

    override fun writeU48(buf: ByteArray, n: ULong, offset: Int) {
        writeU32(buf, (n and 0xFFFF_FFFFuL).toUInt(), offset)
        writeU16(buf, ((n shr 32) and 0xFFFFuL).toUShort(), offset + 4)
    }

    override fun writeU64(buf: ByteArray, n: ULong, offset: Int) {
        writeU32(buf, (n and 0xFFFF_FFFFuL).toUInt(), offset)
        writeU32(buf, ((n shr 32) and 0xFFFF_FFFFuL).toUInt(), offset + 4)
    }
}
