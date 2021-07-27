//
//  HTTPComponents.swift
//  OneMoreAdSDK
//
//  Created by admin on 8/22/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation

typealias HTTPParameters = [String: Any]
typealias HTTPQueryItems = [String: String]
typealias HTTPHeaders = [String: String]

enum HTTPMethod: String {
    case get        = "GET"
    case head       = "HEAD"
    case post       = "POST"
    case put        = "PUT"
    case options    = "OPTIONS"
    case patch      = "PATCH"
    case delete     = "DELETE"
    case trace      = "TRACE"
    case connect    = "CONNECT"
}
