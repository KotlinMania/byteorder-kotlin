// port-lint: source io.rs
package io.github.kotlinmania.byteorder

/**
 * Extends byte readers with methods for reading numbers in a given [ByteOrder].
 */
public interface ReadBytesExt {
    public fun readExact(buf: ByteArray)

    public fun readU8(): UByte {
        val buf = ByteArray(1)
        readExact(buf)
        return buf[0].toUByte()
    }

    public fun readI8(): Byte {
        val buf = ByteArray(1)
        readExact(buf)
        return buf[0]
    }

    public fun readU16(order: ByteOrder = LittleEndian): UShort {
        val buf = ByteArray(2)
        readExact(buf)
        return order.readU16(buf)
    }

    public fun readI16(order: ByteOrder = LittleEndian): Short {
        val buf = ByteArray(2)
        readExact(buf)
        return order.readI16(buf)
    }

    public fun readU24(order: ByteOrder = LittleEndian): UInt {
        val buf = ByteArray(3)
        readExact(buf)
        return order.readU24(buf)
    }

    public fun readI24(order: ByteOrder = LittleEndian): Int {
        val buf = ByteArray(3)
        readExact(buf)
        return order.readI24(buf)
    }

    public fun readU32(order: ByteOrder = LittleEndian): UInt {
        val buf = ByteArray(4)
        readExact(buf)
        return order.readU32(buf)
    }

    public fun readI32(order: ByteOrder = LittleEndian): Int {
        val buf = ByteArray(4)
        readExact(buf)
        return order.readI32(buf)
    }

    public fun readU48(order: ByteOrder = LittleEndian): ULong {
        val buf = ByteArray(6)
        readExact(buf)
        return order.readU48(buf)
    }

    public fun readI48(order: ByteOrder = LittleEndian): Long {
        val buf = ByteArray(6)
        readExact(buf)
        return order.readI48(buf)
    }

    public fun readU64(order: ByteOrder = LittleEndian): ULong {
        val buf = ByteArray(8)
        readExact(buf)
        return order.readU64(buf)
    }

    public fun readI64(order: ByteOrder = LittleEndian): Long {
        val buf = ByteArray(8)
        readExact(buf)
        return order.readI64(buf)
    }

    // Rust 128-bit integer methods omitted as Kotlin lacks 128-bit primitive integer support.

    public fun readUint(nbytes: Int, order: ByteOrder = LittleEndian): ULong {
        val buf = ByteArray(nbytes)
        readExact(buf)
        return order.readUint(buf, nbytes)
    }

    public fun readInt(nbytes: Int, order: ByteOrder = LittleEndian): Long {
        val buf = ByteArray(nbytes)
        readExact(buf)
        return order.readInt(buf, nbytes)
    }

    public fun readF32(order: ByteOrder = LittleEndian): Float {
        val buf = ByteArray(4)
        readExact(buf)
        return order.readF32(buf)
    }

    public fun readF64(order: ByteOrder = LittleEndian): Double {
        val buf = ByteArray(8)
        readExact(buf)
        return order.readF64(buf)
    }

    public fun readI8Into(dst: ByteArray) {
        readExact(dst)
    }

    public fun readI16Into(dst: ShortArray, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(dst.size * 2)
        readExact(buf)
        order.readI16Into(buf, dst)
    }

    public fun readI32Into(dst: IntArray, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(dst.size * 4)
        readExact(buf)
        order.readI32Into(buf, dst)
    }

    public fun readI64Into(dst: LongArray, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(dst.size * 8)
        readExact(buf)
        order.readI64Into(buf, dst)
    }

    public fun readF32Into(dst: FloatArray, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(dst.size * 4)
        readExact(buf)
        order.readF32Into(buf, dst)
    }

    public fun readF32IntoUnchecked(dst: FloatArray, order: ByteOrder = LittleEndian) {
        readF32Into(dst, order)
    }

    public fun readF64Into(dst: DoubleArray, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(dst.size * 8)
        readExact(buf)
        order.readF64Into(buf, dst)
    }

    public fun readF64IntoUnchecked(dst: DoubleArray, order: ByteOrder = LittleEndian) {
        readF64Into(dst, order)
    }
}

/**
 * Extends byte writers with methods for writing numbers in a given [ByteOrder].
 */
public interface WriteBytesExt {
    public fun writeAll(buf: ByteArray)

    public fun writeU8(n: UByte) {
        writeAll(byteArrayOf(n.toByte()))
    }

    public fun writeI8(n: Byte) {
        writeAll(byteArrayOf(n))
    }

    public fun writeU16(n: UShort, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(2)
        order.writeU16(buf, n)
        writeAll(buf)
    }

    public fun writeI16(n: Short, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(2)
        order.writeI16(buf, n)
        writeAll(buf)
    }

    public fun writeU24(n: UInt, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(3)
        order.writeU24(buf, n)
        writeAll(buf)
    }

    public fun writeI24(n: Int, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(3)
        order.writeI24(buf, n)
        writeAll(buf)
    }

    public fun writeU32(n: UInt, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(4)
        order.writeU32(buf, n)
        writeAll(buf)
    }

    public fun writeI32(n: Int, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(4)
        order.writeI32(buf, n)
        writeAll(buf)
    }

    public fun writeU48(n: ULong, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(6)
        order.writeU48(buf, n)
        writeAll(buf)
    }

    public fun writeI48(n: Long, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(6)
        order.writeI48(buf, n)
        writeAll(buf)
    }

    public fun writeU64(n: ULong, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(8)
        order.writeU64(buf, n)
        writeAll(buf)
    }

    public fun writeI64(n: Long, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(8)
        order.writeI64(buf, n)
        writeAll(buf)
    }

    // Rust 128-bit integer methods omitted as Kotlin lacks 128-bit primitive integer support.

    public fun writeUint(n: ULong, nbytes: Int, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(nbytes)
        order.writeUint(buf, n, nbytes)
        writeAll(buf)
    }

    public fun writeInt(n: Long, nbytes: Int, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(nbytes)
        order.writeInt(buf, n, nbytes)
        writeAll(buf)
    }

    public fun writeF32(n: Float, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(4)
        order.writeF32(buf, n)
        writeAll(buf)
    }

    public fun writeF64(n: Double, order: ByteOrder = LittleEndian) {
        val buf = ByteArray(8)
        order.writeF64(buf, n)
        writeAll(buf)
    }
}

public fun ReadBytesExt.readU16Into(dst: UShortArray, order: ByteOrder = LittleEndian) {
    val buf = ByteArray(dst.size * 2)
    readExact(buf)
    order.readU16Into(buf, dst)
}

public fun ReadBytesExt.readU32Into(dst: UIntArray, order: ByteOrder = LittleEndian) {
    val buf = ByteArray(dst.size * 4)
    readExact(buf)
    order.readU32Into(buf, dst)
}

public fun ReadBytesExt.readU64Into(dst: ULongArray, order: ByteOrder = LittleEndian) {
    val buf = ByteArray(dst.size * 8)
    readExact(buf)
    order.readU64Into(buf, dst)
}
