package io.github.kotlinmania.byteorder

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ByteOrderTest {
    private val testU16Values = listOf(0u.toUShort(), 1u.toUShort(), 517u.toUShort(), 768u.toUShort(), UShort.MAX_VALUE)
    private val testI16Values = listOf(0.toShort(), 1.toShort(), (-1).toShort(), 193.toShort(), (-132).toShort(), Short.MIN_VALUE, Short.MAX_VALUE)
    private val testU24Values = listOf(0u, 1u, 1000000u, 0xFF_FFFFu)
    private val testI24Values = listOf(0, 1, -1, 1000000, -8388608, 8388607)
    private val testU32Values = listOf(0u, 1u, 1000000u, 0x12345678u, UInt.MAX_VALUE)
    private val testI32Values = listOf(0, 1, -1, 1000000, Int.MIN_VALUE, Int.MAX_VALUE)
    private val testU48Values = listOf(0uL, 1uL, 1000000000000uL, 0xFFFF_FFFF_FFFFuL)
    private val testI48Values = listOf(0L, 1L, -1L, 1000000000000L, -140737488355328L, 140737488355327L)
    private val testU64Values = listOf(0uL, 1uL, 1000000uL, 0x123456789ABCDEF0uL, ULong.MAX_VALUE)
    private val testI64Values = listOf(0L, 1L, -1L, 1000000L, Long.MIN_VALUE, Long.MAX_VALUE)
    private val testF32Values = listOf(0.0f, 1.0f, -1.0f, 31.312e31f, -11.32e19f)
    private val testF64Values = listOf(0.0, 1.0, -1.0, 31.312e211, -11.32e91)

    // Rust u128/i128 types are omitted as Kotlin lacks 128-bit primitive integer support.

    @Test
    fun propU16() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testU16Values) {
                val buf = ByteArray(16)
                order.writeU16(buf, n)
                val read = order.readU16(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propI16() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testI16Values) {
                val buf = ByteArray(16)
                order.writeI16(buf, n)
                val read = order.readI16(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propU24() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testU24Values) {
                val buf = ByteArray(16)
                order.writeU24(buf, n)
                val read = order.readU24(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propI24() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testI24Values) {
                val buf = ByteArray(16)
                order.writeI24(buf, n)
                val read = order.readI24(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propU32() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testU32Values) {
                val buf = ByteArray(16)
                order.writeU32(buf, n)
                val read = order.readU32(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propI32() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testI32Values) {
                val buf = ByteArray(16)
                order.writeI32(buf, n)
                val read = order.readI32(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propU48() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testU48Values) {
                val buf = ByteArray(16)
                order.writeU48(buf, n)
                val read = order.readU48(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propI48() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testI48Values) {
                val buf = ByteArray(16)
                order.writeI48(buf, n)
                val read = order.readI48(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propU64() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testU64Values) {
                val buf = ByteArray(16)
                order.writeU64(buf, n)
                val read = order.readU64(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propI64() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testI64Values) {
                val buf = ByteArray(16)
                order.writeI64(buf, n)
                val read = order.readI64(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propF32() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testF32Values) {
                val buf = ByteArray(16)
                order.writeF32(buf, n)
                val read = order.readF32(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propF64() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            for (n in testF64Values) {
                val buf = ByteArray(16)
                order.writeF64(buf, n)
                val read = order.readF64(buf)
                assertEquals(n, read)
            }
        }
    }

    @Test
    fun propUint() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (nbytes in 1..8) {
            val max = if (nbytes == 8) ULong.MAX_VALUE else (1uL shl (nbytes * 8)) - 1uL
            val testValues = listOf(0uL, 1uL, max / 2uL, max)
            for (order in orders) {
                for (n in testValues) {
                    val buf = ByteArray(16)
                    order.writeUint(buf, n, nbytes)
                    val read = order.readUint(buf, nbytes)
                    assertEquals(n, read)
                }
            }
        }
    }

    @Test
    fun propInt() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (nbytes in 1..8) {
            val max = if (nbytes == 8) Long.MAX_VALUE else (1L shl (nbytes * 8 - 1)) - 1L
            val min = if (nbytes == 8) Long.MIN_VALUE else -(1L shl (nbytes * 8 - 1))
            val testValues = listOf(0L, 1L, -1L, min, max)
            for (order in orders) {
                for (n in testValues) {
                    val buf = ByteArray(16)
                    order.writeInt(buf, n, nbytes)
                    val read = order.readInt(buf, nbytes)
                    assertEquals(n, read)
                }
            }
        }
    }

    @Test
    fun smallBuffers() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            assertFailsWith<IllegalArgumentException> { order.readU16(ByteArray(1)) }
            assertFailsWith<IllegalArgumentException> { order.writeU16(ByteArray(1), 0u) }

            assertFailsWith<IllegalArgumentException> { order.readI16(ByteArray(1)) }
            assertFailsWith<IllegalArgumentException> { order.writeI16(ByteArray(1), 0) }

            assertFailsWith<IllegalArgumentException> { order.readU24(ByteArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeU24(ByteArray(2), 0u) }

            assertFailsWith<IllegalArgumentException> { order.readI24(ByteArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeI24(ByteArray(2), 0) }

            assertFailsWith<IllegalArgumentException> { order.readU32(ByteArray(3)) }
            assertFailsWith<IllegalArgumentException> { order.writeU32(ByteArray(3), 0u) }

            assertFailsWith<IllegalArgumentException> { order.readI32(ByteArray(3)) }
            assertFailsWith<IllegalArgumentException> { order.writeI32(ByteArray(3), 0) }

            assertFailsWith<IllegalArgumentException> { order.readU48(ByteArray(5)) }
            assertFailsWith<IllegalArgumentException> { order.writeU48(ByteArray(5), 0uL) }

            assertFailsWith<IllegalArgumentException> { order.readI48(ByteArray(5)) }
            assertFailsWith<IllegalArgumentException> { order.writeI48(ByteArray(5), 0L) }

            assertFailsWith<IllegalArgumentException> { order.readU64(ByteArray(7)) }
            assertFailsWith<IllegalArgumentException> { order.writeU64(ByteArray(7), 0uL) }

            assertFailsWith<IllegalArgumentException> { order.readI64(ByteArray(7)) }
            assertFailsWith<IllegalArgumentException> { order.writeI64(ByteArray(7), 0L) }

            assertFailsWith<IllegalArgumentException> { order.readF32(ByteArray(3)) }
            assertFailsWith<IllegalArgumentException> { order.writeF32(ByteArray(3), 0.0f) }

            assertFailsWith<IllegalArgumentException> { order.readF64(ByteArray(7)) }
            assertFailsWith<IllegalArgumentException> { order.writeF64(ByteArray(7), 0.0) }

            for (nbytes in 1..8) {
                assertFailsWith<IllegalArgumentException> { order.readUint(ByteArray(nbytes - 1), nbytes) }
                assertFailsWith<IllegalArgumentException> { order.writeUint(ByteArray(nbytes - 1), 0uL, nbytes) }
                assertFailsWith<IllegalArgumentException> { order.readInt(ByteArray(nbytes - 1), nbytes) }
                assertFailsWith<IllegalArgumentException> { order.writeInt(ByteArray(nbytes - 1), 0L, nbytes) }
            }
        }
    }

    @Test
    fun sliceLengths() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            assertFailsWith<IllegalArgumentException> { order.readU16Into(ByteArray(3), UShortArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.readU16Into(ByteArray(5), UShortArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeU16Into(UShortArray(2), ByteArray(3)) }
            assertFailsWith<IllegalArgumentException> { order.writeU16Into(UShortArray(2), ByteArray(5)) }

            assertFailsWith<IllegalArgumentException> { order.readI16Into(ByteArray(3), ShortArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.readI16Into(ByteArray(5), ShortArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeI16Into(ShortArray(2), ByteArray(3)) }
            assertFailsWith<IllegalArgumentException> { order.writeI16Into(ShortArray(2), ByteArray(5)) }

            assertFailsWith<IllegalArgumentException> { order.readU32Into(ByteArray(7), UIntArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.readU32Into(ByteArray(9), UIntArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeU32Into(UIntArray(2), ByteArray(7)) }
            assertFailsWith<IllegalArgumentException> { order.writeU32Into(UIntArray(2), ByteArray(9)) }

            assertFailsWith<IllegalArgumentException> { order.readI32Into(ByteArray(7), IntArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.readI32Into(ByteArray(9), IntArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeI32Into(IntArray(2), ByteArray(7)) }
            assertFailsWith<IllegalArgumentException> { order.writeI32Into(IntArray(2), ByteArray(9)) }

            assertFailsWith<IllegalArgumentException> { order.readU64Into(ByteArray(15), ULongArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.readU64Into(ByteArray(17), ULongArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeU64Into(ULongArray(2), ByteArray(15)) }
            assertFailsWith<IllegalArgumentException> { order.writeU64Into(ULongArray(2), ByteArray(17)) }

            assertFailsWith<IllegalArgumentException> { order.readI64Into(ByteArray(15), LongArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.readI64Into(ByteArray(17), LongArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeI64Into(LongArray(2), ByteArray(15)) }
            assertFailsWith<IllegalArgumentException> { order.writeI64Into(LongArray(2), ByteArray(17)) }

            assertFailsWith<IllegalArgumentException> { order.readF32Into(ByteArray(7), FloatArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.readF32Into(ByteArray(9), FloatArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeF32Into(FloatArray(2), ByteArray(7)) }
            assertFailsWith<IllegalArgumentException> { order.writeF32Into(FloatArray(2), ByteArray(9)) }

            assertFailsWith<IllegalArgumentException> { order.readF64Into(ByteArray(15), DoubleArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.readF64Into(ByteArray(17), DoubleArray(2)) }
            assertFailsWith<IllegalArgumentException> { order.writeF64Into(DoubleArray(2), ByteArray(15)) }
            assertFailsWith<IllegalArgumentException> { order.writeF64Into(DoubleArray(2), ByteArray(17)) }
        }
    }

    @Test
    fun uintBiggerBuffer() {
        val n = LittleEndian.readUint(byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8), 5)
        assertEquals(0x05_0403_0201uL, n)
    }

    @Test
    fun regression173ArrayImpl() {
        val xs = ByteArray(100)

        assertEquals(0u.toUShort(), BigEndian.readU16(xs))
        assertEquals(0u, BigEndian.readU32(xs))
        assertEquals(0uL, BigEndian.readU64(xs))
        assertEquals(0.toShort(), BigEndian.readI16(xs))
        assertEquals(0, BigEndian.readI32(xs))
        assertEquals(0L, BigEndian.readI64(xs))

        assertEquals(0u.toUShort(), LittleEndian.readU16(xs))
        assertEquals(0u, LittleEndian.readU32(xs))
        assertEquals(0uL, LittleEndian.readU64(xs))
        assertEquals(0.toShort(), LittleEndian.readI16(xs))
        assertEquals(0, LittleEndian.readI32(xs))
        assertEquals(0L, LittleEndian.readI64(xs))
    }

    @Test
    fun propSliceRoundtrip() {
        val orders = listOf(BigEndian, LittleEndian, NativeEndian)
        for (order in orders) {
            val u16s = ushortArrayOf(1u, 2u, 0xF00Fu, 0xFFEEu)
            val u16Buf = ByteArray(8)
            order.writeU16Into(u16s, u16Buf)
            val gotU16s = UShortArray(4)
            order.readU16Into(u16Buf, gotU16s)
            for (i in u16s.indices) assertEquals(u16s[i], gotU16s[i])

            val i16s = shortArrayOf(1, 2, 0x0F, -5)
            val i16Buf = ByteArray(8)
            order.writeI16Into(i16s, i16Buf)
            val gotI16s = ShortArray(4)
            order.readI16Into(i16Buf, gotI16s)
            for (i in i16s.indices) assertEquals(i16s[i], gotI16s[i])

            val u32s = uintArrayOf(1u, 2u, 0xF00Fu, 0xFFEEu)
            val u32Buf = ByteArray(16)
            order.writeU32Into(u32s, u32Buf)
            val gotU32s = UIntArray(4)
            order.readU32Into(u32Buf, gotU32s)
            for (i in u32s.indices) assertEquals(u32s[i], gotU32s[i])

            val i32s = intArrayOf(1, 2, 0xF00F, -100)
            val i32Buf = ByteArray(16)
            order.writeI32Into(i32s, i32Buf)
            val gotI32s = IntArray(4)
            order.readI32Into(i32Buf, gotI32s)
            for (i in i32s.indices) assertEquals(i32s[i], gotI32s[i])

            val u64s = ulongArrayOf(1uL, 2uL, 0xF00FuL, 0xFFEEuL)
            val u64Buf = ByteArray(32)
            order.writeU64Into(u64s, u64Buf)
            val gotU64s = ULongArray(4)
            order.readU64Into(u64Buf, gotU64s)
            for (i in u64s.indices) assertEquals(u64s[i], gotU64s[i])

            val i64s = longArrayOf(1L, 2L, 0xF00FL, -100L)
            val i64Buf = ByteArray(32)
            order.writeI64Into(i64s, i64Buf)
            val gotI64s = LongArray(4)
            order.readI64Into(i64Buf, gotI64s)
            for (i in i64s.indices) assertEquals(i64s[i], gotI64s[i])

            val f32s = floatArrayOf(1.0f, 2.0f, 31.312e31f, -11.32e19f)
            val f32Buf = ByteArray(16)
            order.writeF32Into(f32s, f32Buf)
            val gotF32s = FloatArray(4)
            order.readF32Into(f32Buf, gotF32s)
            for (i in f32s.indices) assertEquals(f32s[i], gotF32s[i])

            val f64s = doubleArrayOf(1.0, 2.0, 31.312e211, -11.32e91)
            val f64Buf = ByteArray(32)
            order.writeF64Into(f64s, f64Buf)
            val gotF64s = DoubleArray(4)
            order.readF64Into(f64Buf, gotF64s)
            for (i in f64s.indices) assertEquals(f64s[i], gotF64s[i])

            val i8s = byteArrayOf(1, 2, 0xF, 0xE)
            val i8Buf = ByteArray(4)
            order.writeI8Into(i8s, i8Buf)
            for (i in i8s.indices) assertEquals(i8s[i], i8Buf[i])
        }
    }

    @Test
    fun fromSliceConversions() {
        val u16s = ushortArrayOf(0x1234u, 0xABCDu)
        BigEndian.fromSliceU16(u16s)
        assertEquals(0x3412u.toUShort(), u16s[0])
        assertEquals(0xCDABu.toUShort(), u16s[1])
        BigEndian.fromSliceU16(u16s)
        assertEquals(0x1234u.toUShort(), u16s[0])
        assertEquals(0xABCDu.toUShort(), u16s[1])

        val u32s = uintArrayOf(0x12345678u)
        BigEndian.fromSliceU32(u32s)
        assertEquals(0x78563412u, u32s[0])
        BigEndian.fromSliceU32(u32s)
        assertEquals(0x12345678u, u32s[0])

        val u64s = ulongArrayOf(0x123456789ABCDEF0uL)
        BigEndian.fromSliceU64(u64s)
        assertEquals(0xF0DEBC9A78563412uL, u64s[0])
        BigEndian.fromSliceU64(u64s)
        assertEquals(0x123456789ABCDEF0uL, u64s[0])

        val i16s = shortArrayOf(0x1234, 0x5678)
        BigEndian.fromSliceI16(i16s)
        assertEquals(0x3412.toShort(), i16s[0])
        assertEquals(0x7856.toShort(), i16s[1])

        val i32s = intArrayOf(0x12345678)
        BigEndian.fromSliceI32(i32s)
        assertEquals(0x78563412, i32s[0])

        val i64s = longArrayOf(0x123456789ABCDEF0L)
        BigEndian.fromSliceI64(i64s)
        assertEquals(0xF0DEBC9A78563412uL.toLong(), i64s[0])

        val f32s = floatArrayOf(1.0f)
        BigEndian.fromSliceF32(f32s)
        BigEndian.fromSliceF32(f32s)
        assertEquals(1.0f, f32s[0])

        val f64s = doubleArrayOf(1.0)
        BigEndian.fromSliceF64(f64s)
        BigEndian.fromSliceF64(f64s)
        assertEquals(1.0, f64s[0])

        // LittleEndian fromSlice is a no-op on little-endian platforms
        val leU16s = ushortArrayOf(0x1234u)
        LittleEndian.fromSliceU16(leU16s)
        assertEquals(0x1234u.toUShort(), leU16s[0])
    }
}
