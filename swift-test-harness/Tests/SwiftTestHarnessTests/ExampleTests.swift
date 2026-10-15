#if canImport(Testing)
import Testing
import Byteorder

@Suite("Byteorder Swift Export Tests")
struct ByteorderExportTests {
    @Test("Byteorder swift module imported cleanly")
    func testSwiftModuleLoads() throws {
        #expect(Bool(true), "Byteorder swift module imported cleanly")
    }
}
#elseif canImport(XCTest)
import XCTest
import Byteorder

final class ByteorderExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        XCTAssertTrue(true, "Byteorder swift module imported cleanly")
    }
}
#endif
