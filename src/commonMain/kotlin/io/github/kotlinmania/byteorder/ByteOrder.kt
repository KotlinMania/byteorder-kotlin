package io.github.kotlinmania.byteorder

/**
 * Describes types that can serialize integers as bytes.
 */
public abstract class ByteOrder {
    public abstract fun readU16(buf: ByteArray, offset: Int = 0): UShort

    public fun readI16(buf: ByteArray, offset: Int = 0): Short = readU16(buf, offset).toShort()

    public open fun readU24(buf: ByteArray, offset: Int = 0): UInt = readUint(buf, 3, offset).toUInt()

    public open fun readI24(buf: ByteArray, offset: Int = 0): Int = readInt(buf, 3, offset).toInt()

    public abstract fun readU32(buf: ByteArray, offset: Int = 0): UInt

    public fun readI32(buf: ByteArray, offset: Int = 0): Int = readU32(buf, offset).toInt()

    public open fun readU48(buf: ByteArray, offset: Int = 0): ULong = readUint(buf, 6, offset)

    public open fun readI48(buf: ByteArray, offset: Int = 0): Long = readInt(buf, 6, offset)

    public abstract fun readU64(buf: ByteArray, offset: Int = 0): ULong

    public fun readI64(buf: ByteArray, offset: Int = 0): Long = readU64(buf, offset).toLong()

    // Rust 128-bit integer methods omitted as Kotlin lacks 128-bit primitive integer support.

    public abstract fun readUint(buf: ByteArray, nbytes: Int, offset: Int = 0): ULong

    public fun readInt(buf: ByteArray, nbytes: Int, offset: Int = 0): Long =
        extendSign(readUint(buf, nbytes, offset), nbytes)

    public fun readF32(buf: ByteArray, offset: Int = 0): Float = Float.fromBits(readI32(buf, offset))

    public fun readF64(buf: ByteArray, offset: Int = 0): Double = Double.fromBits(readI64(buf, offset))

    public abstract fun writeU16(buf: ByteArray, n: UShort, offset: Int = 0)

    public fun writeI16(buf: ByteArray, n: Short, offset: Int = 0) {
        writeU16(buf, n.toUShort(), offset)
    }

    public open fun writeU24(buf: ByteArray, n: UInt, offset: Int = 0) {
        writeUint(buf, n.toULong(), 3, offset)
    }

    public open fun writeI24(buf: ByteArray, n: Int, offset: Int = 0) {
        writeInt(buf, n.toLong(), 3, offset)
    }

    public abstract fun writeU32(buf: ByteArray, n: UInt, offset: Int = 0)

    public fun writeI32(buf: ByteArray, n: Int, offset: Int = 0) {
        writeU32(buf, n.toUInt(), offset)
    }

    public open fun writeU48(buf: ByteArray, n: ULong, offset: Int = 0) {
        writeUint(buf, n, 6, offset)
    }

    public open fun writeI48(buf: ByteArray, n: Long, offset: Int = 0) {
        writeInt(buf, n, 6, offset)
    }

    public abstract fun writeU64(buf: ByteArray, n: ULong, offset: Int = 0)

    public fun writeI64(buf: ByteArray, n: Long, offset: Int = 0) {
        writeU64(buf, n.toULong(), offset)
    }

    public abstract fun writeUint(buf: ByteArray, n: ULong, nbytes: Int, offset: Int = 0)

    public fun writeInt(buf: ByteArray, n: Long, nbytes: Int, offset: Int = 0) {
        writeUint(buf, unextendSign(n, nbytes), nbytes, offset)
    }

    public fun writeF32(buf: ByteArray, n: Float, offset: Int = 0) {
        writeI32(buf, n.toRawBits(), offset)
    }

    public fun writeF64(buf: ByteArray, n: Double, offset: Int = 0) {
        writeI64(buf, n.toRawBits(), offset)
    }

    public fun readU16Into(src: ByteArray, dst: UShortArray) {
        require(src.size == dst.size * 2) { "src size must equal dst size * 2" }
        for (i in dst.indices) {
            dst[i] = readU16(src, i * 2)
        }
    }

    public fun readI16Into(src: ByteArray, dst: ShortArray) {
        require(src.size == dst.size * 2) { "src size must equal dst size * 2" }
        for (i in dst.indices) {
            dst[i] = readI16(src, i * 2)
        }
    }

    public fun readU32Into(src: ByteArray, dst: UIntArray) {
        require(src.size == dst.size * 4) { "src size must equal dst size * 4" }
        for (i in dst.indices) {
            dst[i] = readU32(src, i * 4)
        }
    }

    public fun readI32Into(src: ByteArray, dst: IntArray) {
        require(src.size == dst.size * 4) { "src size must equal dst size * 4" }
        for (i in dst.indices) {
            dst[i] = readI32(src, i * 4)
        }
    }

    public fun readU64Into(src: ByteArray, dst: ULongArray) {
        require(src.size == dst.size * 8) { "src size must equal dst size * 8" }
        for (i in dst.indices) {
            dst[i] = readU64(src, i * 8)
        }
    }

    public fun readI64Into(src: ByteArray, dst: LongArray) {
        require(src.size == dst.size * 8) { "src size must equal dst size * 8" }
        for (i in dst.indices) {
            dst[i] = readI64(src, i * 8)
        }
    }

    public fun readF32Into(src: ByteArray, dst: FloatArray) {
        require(src.size == dst.size * 4) { "src size must equal dst size * 4" }
        for (i in dst.indices) {
            dst[i] = readF32(src, i * 4)
        }
    }

    public fun readF32IntoUnchecked(src: ByteArray, dst: FloatArray) {
        readF32Into(src, dst)
    }

    public fun readF64Into(src: ByteArray, dst: DoubleArray) {
        require(src.size == dst.size * 8) { "src size must equal dst size * 8" }
        for (i in dst.indices) {
            dst[i] = readF64(src, i * 8)
        }
    }

    public fun readF64IntoUnchecked(src: ByteArray, dst: DoubleArray) {
        readF64Into(src, dst)
    }

    public fun writeU16Into(src: UShortArray, dst: ByteArray) {
        require(dst.size == src.size * 2) { "dst size must equal src size * 2" }
        for (i in src.indices) {
            writeU16(dst, src[i], i * 2)
        }
    }

    public fun writeI8Into(src: ByteArray, dst: ByteArray) {
        require(dst.size == src.size) { "dst size must equal src size" }
        src.copyInto(dst)
    }

    public fun writeI16Into(src: ShortArray, dst: ByteArray) {
        require(dst.size == src.size * 2) { "dst size must equal src size * 2" }
        for (i in src.indices) {
            writeI16(dst, src[i], i * 2)
        }
    }

    public fun writeU32Into(src: UIntArray, dst: ByteArray) {
        require(dst.size == src.size * 4) { "dst size must equal src size * 4" }
        for (i in src.indices) {
            writeU32(dst, src[i], i * 4)
        }
    }

    public fun writeI32Into(src: IntArray, dst: ByteArray) {
        require(dst.size == src.size * 4) { "dst size must equal src size * 4" }
        for (i in src.indices) {
            writeI32(dst, src[i], i * 4)
        }
    }

    public fun writeU64Into(src: ULongArray, dst: ByteArray) {
        require(dst.size == src.size * 8) { "dst size must equal src size * 8" }
        for (i in src.indices) {
            writeU64(dst, src[i], i * 8)
        }
    }

    public fun writeI64Into(src: LongArray, dst: ByteArray) {
        require(dst.size == src.size * 8) { "dst size must equal src size * 8" }
        for (i in src.indices) {
            writeI64(dst, src[i], i * 8)
        }
    }

    public fun writeF32Into(src: FloatArray, dst: ByteArray) {
        require(dst.size == src.size * 4) { "dst size must equal src size * 4" }
        for (i in src.indices) {
            writeF32(dst, src[i], i * 4)
        }
    }

    public fun writeF64Into(src: DoubleArray, dst: ByteArray) {
        require(dst.size == src.size * 8) { "dst size must equal src size * 8" }
        for (i in src.indices) {
            writeF64(dst, src[i], i * 8)
        }
    }

    public abstract fun fromSliceU16(numbers: UShortArray)

    public abstract fun fromSliceU32(numbers: UIntArray)

    public abstract fun fromSliceU64(numbers: ULongArray)

    public open fun fromSliceI16(src: ShortArray) {
        for (i in src.indices) {
            val v = src[i].toInt()
            val rev = (((v and 0xFF) shl 8) or ((v ushr 8) and 0xFF)).toShort()
            val swapped = if (this is BigEndian) rev else src[i]
            src[i] = swapped
        }
    }

    public open fun fromSliceI32(src: IntArray) {
        for (i in src.indices) {
            val v = src[i]
            val rev =
                ((v and 0xFF) shl 24) or
                    ((v and 0xFF00) shl 8) or
                    ((v ushr 8) and 0xFF00) or
                    ((v ushr 24) and 0xFF)
            val swapped = if (this is BigEndian) rev else src[i]
            src[i] = swapped
        }
    }

    public open fun fromSliceI64(src: LongArray) {
        for (i in src.indices) {
            val v = src[i]
            val rev =
                ((v and 0xFFL) shl 56) or
                    ((v and 0xFF00L) shl 40) or
                    ((v and 0xFF_0000L) shl 24) or
                    ((v and 0xFF00_0000L) shl 8) or
                    ((v ushr 8) and 0xFF00_0000L) or
                    ((v ushr 24) and 0xFF_0000L) or
                    ((v ushr 40) and 0xFF00L) or
                    ((v ushr 56) and 0xFFL)
            val swapped = if (this is BigEndian) rev else src[i]
            src[i] = swapped
        }
    }

    public abstract fun fromSliceF32(numbers: FloatArray)

    public abstract fun fromSliceF64(numbers: DoubleArray)
}

internal fun packSize(n: ULong): Int {
    if (n == 0uL) return 1
    val bits = 64 - n.countLeadingZeroBits()
    return (bits + 7) / 8
}

internal fun extendSign(value: ULong, nbytes: Int): Long {
    val shift = (8 - nbytes) * 8
    return (value.toLong() shl shift) shr shift
}

internal fun unextendSign(value: Long, nbytes: Int): ULong {
    val shift = (8 - nbytes) * 8
    return ((value shl shift) ushr shift).toULong()
}
