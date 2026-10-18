package io.github.kotlinmania.byteorder

/**
 * Defines little-endian serialization.
 */
public object LittleEndian : ByteOrder() {
    override fun readU16(buf: ByteArray, offset: Int): UShort {
        require(offset + 2 <= buf.size) { "buffer too small for u16" }
        val b0 = buf[offset].toInt() and 0xFF
        val b1 = buf[offset + 1].toInt() and 0xFF
        return (b0 or (b1 shl 8)).toUShort()
    }

    override fun readU24(buf: ByteArray, offset: Int): UInt {
        require(offset + 3 <= buf.size) { "buffer too small for u24" }
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
        require(offset + 4 <= buf.size) { "buffer too small for u32" }
        val b0 = buf[offset].toUInt() and 0xFFu
        val b1 = buf[offset + 1].toUInt() and 0xFFu
        val b2 = buf[offset + 2].toUInt() and 0xFFu
        val b3 = buf[offset + 3].toUInt() and 0xFFu
        return b0 or (b1 shl 8) or (b2 shl 16) or (b3 shl 24)
    }

    override fun readU48(buf: ByteArray, offset: Int): ULong {
        require(offset + 6 <= buf.size) { "buffer too small for u48" }
        val lo = readU32(buf, offset).toULong()
        val hi = readU16(buf, offset + 4).toULong()
        return lo or (hi shl 32)
    }

    override fun readI48(buf: ByteArray, offset: Int): Long {
        val u = readU48(buf, offset).toLong()
        return (u shl 16) shr 16
    }

    override fun readU64(buf: ByteArray, offset: Int): ULong {
        require(offset + 8 <= buf.size) { "buffer too small for u64" }
        val lo = readU32(buf, offset).toULong()
        val hi = readU32(buf, offset + 4).toULong()
        return lo or (hi shl 32)
    }

    override fun readUint(buf: ByteArray, nbytes: Int, offset: Int): ULong {
        require(nbytes in 1..8 && offset + nbytes <= buf.size) {
            "nbytes must be between 1 and 8 and fit within buf"
        }
        var out = 0uL
        for (i in 0 until nbytes) {
            val b = buf[offset + i].toULong() and 0xFFuL
            out = out or (b shl (i * 8))
        }
        return out
    }

    override fun writeU16(buf: ByteArray, n: UShort, offset: Int) {
        require(offset + 2 <= buf.size) { "buffer too small for u16" }
        val v = n.toInt()
        buf[offset] = (v and 0xFF).toByte()
        buf[offset + 1] = ((v ushr 8) and 0xFF).toByte()
    }

    override fun writeU24(buf: ByteArray, n: UInt, offset: Int) {
        require(offset + 3 <= buf.size) { "buffer too small for u24" }
        buf[offset] = (n and 0xFFu).toByte()
        buf[offset + 1] = ((n shr 8) and 0xFFu).toByte()
        buf[offset + 2] = ((n shr 16) and 0xFFu).toByte()
    }

    override fun writeU32(buf: ByteArray, n: UInt, offset: Int) {
        require(offset + 4 <= buf.size) { "buffer too small for u32" }
        buf[offset] = (n and 0xFFu).toByte()
        buf[offset + 1] = ((n shr 8) and 0xFFu).toByte()
        buf[offset + 2] = ((n shr 16) and 0xFFu).toByte()
        buf[offset + 3] = ((n shr 24) and 0xFFu).toByte()
    }

    override fun writeU48(buf: ByteArray, n: ULong, offset: Int) {
        require(offset + 6 <= buf.size) { "buffer too small for u48" }
        writeU32(buf, (n and 0xFFFF_FFFFuL).toUInt(), offset)
        writeU16(buf, ((n shr 32) and 0xFFFFuL).toUShort(), offset + 4)
    }

    override fun writeU64(buf: ByteArray, n: ULong, offset: Int) {
        require(offset + 8 <= buf.size) { "buffer too small for u64" }
        writeU32(buf, (n and 0xFFFF_FFFFuL).toUInt(), offset)
        writeU32(buf, ((n shr 32) and 0xFFFF_FFFFuL).toUInt(), offset + 4)
    }

    override fun writeUint(buf: ByteArray, n: ULong, nbytes: Int, offset: Int) {
        require(packSize(n) <= nbytes && nbytes in 1..8 && offset + nbytes <= buf.size) {
            "packSize(n) must be <= nbytes and fit within buf"
        }
        for (i in 0 until nbytes) {
            val shift = i * 8
            buf[offset + i] = ((n shr shift) and 0xFFuL).toByte()
        }
    }

    override fun fromSliceU16(numbers: UShortArray) {
        // No-op on little-endian host platforms
    }

    override fun fromSliceU32(numbers: UIntArray) {
        // No-op on little-endian host platforms
    }

    override fun fromSliceU64(numbers: ULongArray) {
        // No-op on little-endian host platforms
    }

    override fun fromSliceI16(src: ShortArray) {
        // No-op on little-endian host platforms
    }

    override fun fromSliceI32(src: IntArray) {
        // No-op on little-endian host platforms
    }

    override fun fromSliceI64(src: LongArray) {
        // No-op on little-endian host platforms
    }

    override fun fromSliceF32(numbers: FloatArray) {
        // No-op on little-endian host platforms
    }

    override fun fromSliceF64(numbers: DoubleArray) {
        // No-op on little-endian host platforms
    }
}
