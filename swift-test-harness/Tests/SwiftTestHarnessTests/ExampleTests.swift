import Testing
import Byteorder
import KotlinStdlib

@Suite("Byteorder Swift Export Tests")
struct ByteorderExportTests {
    @Test("Unsigned endian encoding crosses Swift boundary")
    func unsignedEndianRoundTrip() {
        let bytes = KotlinStdlib.ByteArray(size: 4)
        BigEndian.shared.writeU32(buf: bytes, n: 0x12345678, offset: 0)
        #expect(bytes._get(index: 0) == 0x12)
        #expect(bytes._get(index: 3) == 0x78)
        #expect(BigEndian.shared.readU32(buf: bytes, offset: 0) == 0x12345678)
        #expect(LittleEndian.shared.readU32(buf: bytes, offset: 0) == 0x78563412)
        LittleEndian.shared.writeU16(buf: bytes, n: 0x1234, offset: 1)
        #expect(bytes._get(index: 1) == 0x34)
        #expect(bytes._get(index: 2) == 0x12)
        #expect(LittleEndian.shared.readU16(buf: bytes, offset: 1) == 0x1234)
    }

    @Test("Signed nonstandard widths cross Swift boundary")
    func signedWidthRoundTrip() {
        let bytes = KotlinStdlib.ByteArray(size: 8)
        BigEndian.shared.writeI24(buf: bytes, n: -2, offset: 1)
        #expect(BigEndian.shared.readI24(buf: bytes, offset: 1) == -2)
        #expect(bytes._get(index: 1) == -1)
        LittleEndian.shared.writeI48(buf: bytes, n: -0x0102030405, offset: 1)
        #expect(LittleEndian.shared.readI48(buf: bytes, offset: 1) == -0x0102030405)
        #expect(bytes._get(index: 6) == -1)
    }

    @Test("Floating bit patterns cross Swift boundary")
    func floatingBitPatterns() {
        let bytes = KotlinStdlib.ByteArray(size: 8)
        BigEndian.shared.writeF64(buf: bytes, n: -0.0, offset: 0)
        #expect(BigEndian.shared.readF64(buf: bytes, offset: 0).bitPattern == Double(-0.0).bitPattern)
        LittleEndian.shared.writeF64(buf: bytes, n: .infinity, offset: 0)
        #expect(LittleEndian.shared.readF64(buf: bytes, offset: 0) == .infinity)
        LittleEndian.shared.writeF32(buf: bytes, n: -13.25, offset: 2)
        #expect(LittleEndian.shared.readF32(buf: bytes, offset: 2).bitPattern == Float(-13.25).bitPattern)
    }
}
