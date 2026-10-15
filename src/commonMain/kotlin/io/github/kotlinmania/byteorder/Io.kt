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
