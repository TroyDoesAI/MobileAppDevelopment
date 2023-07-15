
import XCTest
@testable import Assignment6

final class JavaScriptISO8601DateDecoderTests: XCTestCase {
  
  private struct WithISO8601Date: Identifiable, Decodable {
    let id: UUID
    let date: Date
  }
  
  func decode(_ dateStr: String, fractionalSeconds: Bool = false) throws {
    let formatter = ISO8601DateFormatter()
    if fractionalSeconds {
      formatter.formatOptions = [
        .withInternetDateTime,
        .withFractionalSeconds
      ]
    }
    let date = formatter.date(from: dateStr)
    let json = "{\"id\": \"5545fefa-8455-45aa-8dde-ced674273934\", \"date\": \"\(dateStr)\"}"
    let data = Data(json.utf8)
    let obj = try JSONDecoder.javaScriptISO8601().decode(WithISO8601Date.self, from: data)
    XCTAssertEqual(obj.date, date)
  }

  func testWholeSeconds() throws {
    try decode("2022-01-31T02:22:40Z")
  }
  
  func testFractionalSeconds() throws {
    try decode("2022-01-31T02:22:40.001Z", fractionalSeconds: true)
  }

  func testBadDate() throws {
    XCTAssertThrowsError(try decode("2022-13-01T02:22:40Z"))
  }
  
}
