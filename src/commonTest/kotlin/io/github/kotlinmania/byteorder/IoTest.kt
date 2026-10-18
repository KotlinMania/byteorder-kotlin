package io.github.kotlinmania.byteorder

import kotlin.test.Test
import kotlin.test.assertEquals

class Cursor(private val buffer: ByteArray, private var position: Int = 0) : ReadBytesExt {
    override fun readExact(buf: ByteArray) {
        require(position + buf.size <= buffer.size) { "unexpected end of buffer" }
        buffer.copyInto(buf, 0, position, position + buf.size)
        position += buf.size
    }
}

class Buffer : WriteBytesExt {
    private val data = ArrayList<Byte>()

    override fun writeAll(buf: ByteArray) {
        for (b in buf) {
            data.add(b)
        }
    }

    fun toByteArray(): ByteArray = data.toByteArray()
}

class IoTest {
    // Rust u128/i128 types are omitted as Kotlin lacks 128-bit primitive integer support.

    @Test
    fun testU8AndI8() {
        val wtr = Buffer()
        wtr.writeU8(2u)
        wtr.writeU8(5u)
        wtr.writeI8(2)
        wtr.writeI8((-5).toByte())

        val rdr = Cursor(wtr.toByteArray())
        assertEquals(2u.toUByte(), rdr.readU8())
        assertEquals(5u.toUByte(), rdr.readU8())
        assertEquals(2.toByte(), rdr.readI8())
        assertEquals((-5).toByte(), rdr.readI8())
    }

    @Test
    fun testU16AndI16() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            val wtr = Buffer()
            wtr.writeU16(517u, order)
            wtr.writeU16(768u, order)
            wtr.writeI16(193.toShort(), order)
            wtr.writeI16((-132).toShort(), order)

            val rdr = Cursor(wtr.toByteArray())
            assertEquals(517u.toUShort(), rdr.readU16(order))
            assertEquals(768u.toUShort(), rdr.readU16(order))
            assertEquals(193.toShort(), rdr.readI16(order))
            assertEquals((-132).toShort(), rdr.readI16(order))
        }
    }

    @Test
    fun testU24AndI24() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            val wtr = Buffer()
            wtr.writeU24(1000000u, order)
            wtr.writeI24(-50000, order)

            val rdr = Cursor(wtr.toByteArray())
            assertEquals(1000000u, rdr.readU24(order))
            assertEquals(-50000, rdr.readI24(order))
        }
    }

    @Test
    fun testU32AndI32() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            val wtr = Buffer()
            wtr.writeU32(1000000u, order)
            wtr.writeI32(-5000000, order)

            val rdr = Cursor(wtr.toByteArray())
            assertEquals(1000000u, rdr.readU32(order))
            assertEquals(-5000000, rdr.readI32(order))
        }
    }

    @Test
    fun testU48AndI48() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            val wtr = Buffer()
            wtr.writeU48(1000000000000uL, order)
            wtr.writeI48(-5000000000000L, order)

            val rdr = Cursor(wtr.toByteArray())
            assertEquals(1000000000000uL, rdr.readU48(order))
            assertEquals(-5000000000000L, rdr.readI48(order))
        }
    }

    @Test
    fun testU64AndI64() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            val wtr = Buffer()
            wtr.writeU64(1000000uL, order)
            wtr.writeI64(-5000000L, order)

            val rdr = Cursor(wtr.toByteArray())
            assertEquals(1000000uL, rdr.readU64(order))
            assertEquals(-5000000L, rdr.readI64(order))
        }
    }

    @Test
    fun testUintAndInt() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (nbytes in 1..8) {
            val uVal = if (nbytes == 8) 0x123456789ABCDEF0uL else (1uL shl (nbytes * 8 - 1)) + 1uL
            val iVal = if (nbytes == 8) -1234567890L else -((1L shl (nbytes * 8 - 2)))
            for (order in orders) {
                val wtr = Buffer()
                wtr.writeUint(uVal, nbytes, order)
                wtr.writeInt(iVal, nbytes, order)

                val rdr = Cursor(wtr.toByteArray())
                assertEquals(uVal, rdr.readUint(nbytes, order))
                assertEquals(iVal, rdr.readInt(nbytes, order))
            }
        }
    }

    @Test
    fun testF32AndF64() {
        val f32Val = Float.fromBits(31.312e31f.toRawBits())
        val f64Val = -11.32e91
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            val wtr = Buffer()
            wtr.writeF32(f32Val, order)
            wtr.writeF64(f64Val, order)

            val rdr = Cursor(wtr.toByteArray())
            assertEquals(f32Val, rdr.readF32(order))
            assertEquals(f64Val, rdr.readF64(order))
        }
    }

    @Test
    fun testReadIntoMethods() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            val wtr = Buffer()
            order.writeU16Into(ushortArrayOf(1u, 2u), ByteArray(4).also {
                order.writeU16Into(ushortArrayOf(1u, 2u), it)
                wtr.writeAll(it)
            })

            val rdr = Cursor(wtr.toByteArray())
            val got = UShortArray(2)
            rdr.readU16Into(got, order)
            assertEquals(1u.toUShort(), got[0])
            assertEquals(2u.toUShort(), got[1])

            val i8Buf = byteArrayOf(10, 20, 30)
            val rdrI8 = Cursor(i8Buf)
            val gotI8 = ByteArray(3)
            rdrI8.readI8Into(gotI8)
            assertEquals(10, gotI8[0])
            assertEquals(20, gotI8[1])
            assertEquals(30, gotI8[2])
        }
    }
}
