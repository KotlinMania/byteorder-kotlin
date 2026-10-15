// port-lint: source lib.rs
package io.github.kotlinmania.byteorder

/**
 * `ByteOrder` describes types that can serialize integers as bytes.
 */
public sealed interface ByteOrder {
    public fun readU16(buf: ByteArray, offset: Int = 0): UShort
    public fun readI16(buf: ByteArray, offset: Int = 0): Short = readU16(buf, offset).toShort()
    public fun readU24(buf: ByteArray, offset: Int = 0): UInt
    public fun readI24(buf: ByteArray, offset: Int = 0): Int
    public fun readU32(buf: ByteArray, offset: Int = 0): UInt
    public fun readI32(buf: ByteArray, offset: Int = 0): Int = readU32(buf, offset).toInt()
    public fun readU48(buf: ByteArray, offset: Int = 0): ULong
    public fun readI48(buf: ByteArray, offset: Int = 0): Long
    public fun readU64(buf: ByteArray, offset: Int = 0): ULong
    public fun readI64(buf: ByteArray, offset: Int = 0): Long = readU64(buf, offset).toLong()
    public fun readF32(buf: ByteArray, offset: Int = 0): Float = Float.fromBits(readI32(buf, offset))
    public fun readF64(buf: ByteArray, offset: Int = 0): Double = Double.fromBits(readI64(buf, offset))

    public fun writeU16(buf: ByteArray, n: UShort, offset: Int = 0)
    public fun writeI16(buf: ByteArray, n: Short, offset: Int = 0) = writeU16(buf, n.toUShort(), offset)
    public fun writeU24(buf: ByteArray, n: UInt, offset: Int = 0)
    public fun writeI24(buf: ByteArray, n: Int, offset: Int = 0) = writeU24(buf, n.toUInt(), offset)
    public fun writeU32(buf: ByteArray, n: UInt, offset: Int = 0)
    public fun writeI32(buf: ByteArray, n: Int, offset: Int = 0) = writeU32(buf, n.toUInt(), offset)
    public fun writeU48(buf: ByteArray, n: ULong, offset: Int = 0)
    public fun writeI48(buf: ByteArray, n: Long, offset: Int = 0) = writeU48(buf, n.toULong(), offset)
    public fun writeU64(buf: ByteArray, n: ULong, offset: Int = 0)
    public fun writeI64(buf: ByteArray, n: Long, offset: Int = 0) = writeU64(buf, n.toULong(), offset)
    public fun writeF32(buf: ByteArray, n: Float, offset: Int = 0) = writeI32(buf, n.toRawBits(), offset)
    public fun writeF64(buf: ByteArray, n: Double, offset: Int = 0) = writeI64(buf, n.toRawBits(), offset)

    public fun readU16Into(src: ByteArray, dst: UShortArray) {
        require(src.size == dst.size * 2) { "src.size must equal dst.size * 2" }
        for (i in dst.indices) {
            dst[i] = readU16(src, i * 2)
        }
    }

    public fun readI16Into(src: ByteArray, dst: ShortArray) {
        require(src.size == dst.size * 2) { "src.size must equal dst.size * 2" }
        for (i in dst.indices) {
            dst[i] = readI16(src, i * 2)
        }
    }

    public fun readU32Into(src: ByteArray, dst: UIntArray) {
        require(src.size == dst.size * 4) { "src.size must equal dst.size * 4" }
        for (i in dst.indices) {
            dst[i] = readU32(src, i * 4)
        }
    }

    public fun readI32Into(src: ByteArray, dst: IntArray) {
        require(src.size == dst.size * 4) { "src.size must equal dst.size * 4" }
        for (i in dst.indices) {
            dst[i] = readI32(src, i * 4)
        }
    }

    public fun readU64Into(src: ByteArray, dst: ULongArray) {
        require(src.size == dst.size * 8) { "src.size must equal dst.size * 8" }
        for (i in dst.indices) {
            dst[i] = readU64(src, i * 8)
        }
    }

    public fun readI64Into(src: ByteArray, dst: LongArray) {
        require(src.size == dst.size * 8) { "src.size must equal dst.size * 8" }
        for (i in dst.indices) {
            dst[i] = readI64(src, i * 8)
        }
    }

    public fun readF32Into(src: ByteArray, dst: FloatArray) {
        require(src.size == dst.size * 4) { "src.size must equal dst.size * 4" }
        for (i in dst.indices) {
            dst[i] = readF32(src, i * 4)
        }
    }

    public fun readF64Into(src: ByteArray, dst: DoubleArray) {
        require(src.size == dst.size * 8) { "src.size must equal dst.size * 8" }
        for (i in dst.indices) {
            dst[i] = readF64(src, i * 8)
        }
    }

    public fun writeU16Into(src: UShortArray, dst: ByteArray) {
        require(dst.size == src.size * 2) { "dst.size must equal src.size * 2" }
        for (i in src.indices) {
            writeU16(dst, src[i], i * 2)
        }
    }

    public fun writeU32Into(src: UIntArray, dst: ByteArray) {
        require(dst.size == src.size * 4) { "dst.size must equal src.size * 4" }
        for (i in src.indices) {
            writeU32(dst, src[i], i * 4)
        }
    }

    public fun writeU64Into(src: ULongArray, dst: ByteArray) {
        require(dst.size == src.size * 8) { "dst.size must equal src.size * 8" }
        for (i in src.indices) {
            writeU64(dst, src[i], i * 8)
        }
    }

    public fun writeF32Into(src: FloatArray, dst: ByteArray) {
        require(dst.size == src.size * 4) { "dst.size must equal src.size * 4" }
        for (i in src.indices) {
            writeF32(dst, src[i], i * 4)
        }
    }

    public fun writeF64Into(src: DoubleArray, dst: ByteArray) {
        require(dst.size == src.size * 8) { "dst.size must equal src.size * 8" }
        for (i in src.indices) {
            writeF64(dst, src[i], i * 8)
        }
    }
}

/**
 * Defines big-endian serialization.
 */
public object BigEndian : ByteOrder {
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

/**
 * Defines little-endian serialization.
 */
public object LittleEndian : ByteOrder {
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

public typealias NetworkEndian = BigEndian
public typealias NativeEndian = LittleEndian
public typealias LE = LittleEndian
public typealias BE = BigEndian
