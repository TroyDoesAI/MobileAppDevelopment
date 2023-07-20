/*
 * Copyright (C) 2022-2023 David C. Harrison. All right reserved.
 *
 * You may not use, distribute, publish, or modify this code without
 * the express written permission of the copyright holder.
 *
 * Extracted from encoder/decoder @ https://bootstragram.com/blog/json-dates-swift/
 */

import Foundation

class JavaScriptISO8601DateDecoder {
  static let fractionalSecondsFormatter: ISO8601DateFormatter = {
    let res = ISO8601DateFormatter()
    res.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
    return res
  }()

  static let defaultFormatter = ISO8601DateFormatter()

  static func decodedDate(_ decoder: Decoder) throws -> Date {
    let container = try decoder.singleValueContainer()
    let dateAsString = try container.decode(String.self)

    var date: Date?
    for formatter in [fractionalSecondsFormatter, defaultFormatter] {
      if let res = formatter.date(from: dateAsString) {
        date = res
        break;
      }
    }
    return date!
  }
}

extension JSONDecoder.DateDecodingStrategy {
  static func javaScriptISO8601() -> JSONDecoder.DateDecodingStrategy {
    .custom(JavaScriptISO8601DateDecoder.decodedDate)
  }
}

extension JSONDecoder {
  static func javaScriptISO8601() -> JSONDecoder {
    let res = JSONDecoder()
    res.dateDecodingStrategy = .javaScriptISO8601()
    return res
  }
}
