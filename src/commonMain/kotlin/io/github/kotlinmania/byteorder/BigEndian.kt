package io.github.kotlinmania.byteorder

/**
 * Defines big-endian serialization.
 */
public object BigEndian : ByteOrder() {
    override fun readU16(buf: ByteArray, offset: Int): UShort {
        require(offset + 2 <= buf.size) { "buffer too small for u16" }
        val b0 = buf[offset].toInt() and 0xFF
        val b1 = buf[offset + 1].toInt() and 0xFF
        return ((b0 shl 8) or b1).toUShort()
    }

    override fun readU24(buf: ByteArray, offset: Int): UInt {
        require(offset + 3 <= buf.size) { "buffer too small for u24" }
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
        require(offset + 4 <= buf.size) { "buffer too small for u32" }
        val b0 = buf[offset].toUInt() and 0xFFu
        val b1 = buf[offset + 1].toUInt() and 0xFFu
        val b2 = buf[offset + 2].toUInt() and 0xFFu
        val b3 = buf[offset + 3].toUInt() and 0xFFu
        return (b0 shl 24) or (b1 shl 16) or (b2 shl 8) or b3
    }

    override fun readU48(buf: ByteArray, offset: Int): ULong {
        require(offset + 6 <= buf.size) { "buffer too small for u48" }
        val hi = readU16(buf, offset).toULong()
        val lo = readU32(buf, offset + 2).toULong()
        return (hi shl 32) or lo
    }

    override fun readI48(buf: ByteArray, offset: Int): Long {
        val u = readU48(buf, offset).toLong()
        return (u shl 16) shr 16
    }

    override fun readU64(buf: ByteArray, offset: Int): ULong {
        require(offset + 8 <= buf.size) { "buffer too small for u64" }
        val hi = readU32(buf, offset).toULong()
        val lo = readU32(buf, offset + 4).toULong()
        return (hi shl 32) or lo
    }

    override fun readUint(buf: ByteArray, nbytes: Int, offset: Int): ULong {
        require(nbytes in 1..8 && offset + nbytes <= buf.size) {
            "nbytes must be between 1 and 8 and fit within buf"
        }
        var out = 0uL
        for (i in 0 until nbytes) {
            val b = buf[offset + i].toULong() and 0xFFuL
            out = (out shl 8) or b
        }
        return out
    }

    override fun writeU16(buf: ByteArray, n: UShort, offset: Int) {
        require(offset + 2 <= buf.size) { "buffer too small for u16" }
        val v = n.toInt()
        buf[offset] = ((v ushr 8) and 0xFF).toByte()
        buf[offset + 1] = (v and 0xFF).toByte()
    }

    override fun writeU24(buf: ByteArray, n: UInt, offset: Int) {
        require(offset + 3 <= buf.size) { "buffer too small for u24" }
        buf[offset] = ((n shr 16) and 0xFFu).toByte()
        buf[offset + 1] = ((n shr 8) and 0xFFu).toByte()
        buf[offset + 2] = (n and 0xFFu).toByte()
    }

    override fun writeU32(buf: ByteArray, n: UInt, offset: Int) {
        require(offset + 4 <= buf.size) { "buffer too small for u32" }
        buf[offset] = ((n shr 24) and 0xFFu).toByte()
        buf[offset + 1] = ((n shr 16) and 0xFFu).toByte()
        buf[offset + 2] = ((n shr 8) and 0xFFu).toByte()
        buf[offset + 3] = (n and 0xFFu).toByte()
    }

    override fun writeU48(buf: ByteArray, n: ULong, offset: Int) {
        require(offset + 6 <= buf.size) { "buffer too small for u48" }
        writeU16(buf, ((n shr 32) and 0xFFFFuL).toUShort(), offset)
        writeU32(buf, (n and 0xFFFF_FFFFuL).toUInt(), offset + 2)
    }

    override fun writeU64(buf: ByteArray, n: ULong, offset: Int) {
        require(offset + 8 <= buf.size) { "buffer too small for u64" }
        writeU32(buf, ((n shr 32) and 0xFFFF_FFFFuL).toUInt(), offset)
        writeU32(buf, (n and 0xFFFF_FFFFuL).toUInt(), offset + 4)
    }

    override fun writeUint(buf: ByteArray, n: ULong, nbytes: Int, offset: Int) {
        require(packSize(n) <= nbytes && nbytes in 1..8 && offset + nbytes <= buf.size) {
            "packSize(n) must be <= nbytes and fit within buf"
        }
        for (i in 0 until nbytes) {
            val shift = (nbytes - 1 - i) * 8
            buf[offset + i] = ((n shr shift) and 0xFFuL).toByte()
        }
    }

    override fun fromSliceU16(numbers: UShortArray) {
        for (i in numbers.indices) {
            val v = numbers[i].toInt()
            numbers[i] = (((v and 0xFF) shl 8) or ((v ushr 8) and 0xFF)).toUShort()
        }
    }

    override fun fromSliceU32(numbers: UIntArray) {
        for (i in numbers.indices) {
            val v = numbers[i]
            numbers[i] = ((v and 0xFFu) shl 24) or
                ((v and 0xFF00u) shl 8) or
                ((v and 0xFF_0000u) shr 8) or
                ((v and 0xFF00_0000u) shr 24)
        }
    }

    override fun fromSliceU64(numbers: ULongArray) {
        for (i in numbers.indices) {
            val v = numbers[i]
            numbers[i] = ((v and 0xFFuL) shl 56) or
                ((v and 0xFF00uL) shl 40) or
                ((v and 0xFF_0000uL) shl 24) or
                ((v and 0xFF00_0000uL) shl 8) or
                ((v and 0xFF_0000_0000uL) shr 8) or
                ((v and 0xFF00_0000_0000uL) shr 24) or
                ((v and 0xFF_0000_0000_0000uL) shr 40) or
                ((v and 0xFF00_0000_0000_0000uL) shr 56)
        }
    }

    override fun fromSliceI16(src: ShortArray) {
        for (i in src.indices) {
            val v = src[i].toInt()
            src[i] = (((v and 0xFF) shl 8) or ((v ushr 8) and 0xFF)).toShort()
        }
    }

    override fun fromSliceI32(src: IntArray) {
        for (i in src.indices) {
            val v = src[i]
            src[i] = ((v and 0xFF) shl 24) or
                ((v and 0xFF00) shl 8) or
                ((v ushr 8) and 0xFF00) or
                ((v ushr 24) and 0xFF)
        }
    }

    override fun fromSliceI64(src: LongArray) {
        for (i in src.indices) {
            val v = src[i]
            src[i] = ((v and 0xFFL) shl 56) or
                ((v and 0xFF00L) shl 40) or
                ((v and 0xFF_0000L) shl 24) or
                ((v and 0xFF00_0000L) shl 8) or
                ((v ushr 8) and 0xFF00_0000L) or
                ((v ushr 24) and 0xFF_0000L) or
                ((v ushr 40) and 0xFF00L) or
                ((v ushr 56) and 0xFFL)
        }
    }

    override fun fromSliceF32(numbers: FloatArray) {
        for (i in numbers.indices) {
            val bits = numbers[i].toRawBits()
            val rev = ((bits and 0xFF) shl 24) or
                ((bits and 0xFF00) shl 8) or
                ((bits ushr 8) and 0xFF00) or
                ((bits ushr 24) and 0xFF)
            numbers[i] = Float.fromBits(rev)
        }
    }

    override fun fromSliceF64(numbers: DoubleArray) {
        for (i in numbers.indices) {
            val bits = numbers[i].toRawBits()
            val rev = ((bits and 0xFFL) shl 56) or
                ((bits and 0xFF00L) shl 40) or
                ((bits and 0xFF_0000L) shl 24) or
                ((bits and 0xFF00_0000L) shl 8) or
                ((bits ushr 8) and 0xFF00_0000L) or
                ((bits ushr 24) and 0xFF_0000L) or
                ((bits ushr 40) and 0xFF00L) or
                ((bits ushr 56) and 0xFFL)
            numbers[i] = Double.fromBits(rev)
        }
    }
}
