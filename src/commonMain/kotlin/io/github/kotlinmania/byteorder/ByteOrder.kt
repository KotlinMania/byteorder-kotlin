package io.github.kotlinmania.byteorder

/**
 * `ByteOrder` describes types that can serialize integers as bytes.
 */
public abstract class ByteOrder {
    public abstract fun readU16(buf: ByteArray, offset: Int = 0): UShort

    public fun readI16(buf: ByteArray, offset: Int = 0): Short = readU16(buf, offset).toShort()

    public abstract fun readU24(buf: ByteArray, offset: Int = 0): UInt

    public abstract fun readI24(buf: ByteArray, offset: Int = 0): Int

    public abstract fun readU32(buf: ByteArray, offset: Int = 0): UInt

    public fun readI32(buf: ByteArray, offset: Int = 0): Int = readU32(buf, offset).toInt()

    public abstract fun readU48(buf: ByteArray, offset: Int = 0): ULong

    public abstract fun readI48(buf: ByteArray, offset: Int = 0): Long

    public abstract fun readU64(buf: ByteArray, offset: Int = 0): ULong

    public fun readI64(buf: ByteArray, offset: Int = 0): Long = readU64(buf, offset).toLong()

    public fun readF32(buf: ByteArray, offset: Int = 0): Float = Float.fromBits(readI32(buf, offset))

    public fun readF64(buf: ByteArray, offset: Int = 0): Double = Double.fromBits(readI64(buf, offset))

    public abstract fun writeU16(buf: ByteArray, n: UShort, offset: Int = 0)

    public fun writeI16(buf: ByteArray, n: Short, offset: Int = 0) = writeU16(buf, n.toUShort(), offset)

    public abstract fun writeU24(buf: ByteArray, n: UInt, offset: Int = 0)

    public fun writeI24(buf: ByteArray, n: Int, offset: Int = 0) = writeU24(buf, n.toUInt(), offset)

    public abstract fun writeU32(buf: ByteArray, n: UInt, offset: Int = 0)

    public fun writeI32(buf: ByteArray, n: Int, offset: Int = 0) = writeU32(buf, n.toUInt(), offset)

    public abstract fun writeU48(buf: ByteArray, n: ULong, offset: Int = 0)

    public fun writeI48(buf: ByteArray, n: Long, offset: Int = 0) = writeU48(buf, n.toULong(), offset)

    public abstract fun writeU64(buf: ByteArray, n: ULong, offset: Int = 0)

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
