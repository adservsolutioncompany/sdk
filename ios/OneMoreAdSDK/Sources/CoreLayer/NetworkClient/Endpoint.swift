//
//  Endpoint.swift
//  OneMoreAdSDK
//
//  Created by admin on 8/22/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation

enum Endpoint: HTTPEndpoint {
    
    case ad(host: String, queryItems: HTTPQueryItems)
    case userId(host: String, queryItems: HTTPQueryItems)
    
    case install(host: String, queryItems: HTTPQueryItems)
    case open(host: String, queryItems: HTTPQueryItems)
    case reg(host: String, queryItems: HTTPQueryItems)
    case auth(host: String, queryItems: HTTPQueryItems)
    case bet(host: String, queryItems: HTTPQueryItems)
    case dep(host: String, queryItems: HTTPQueryItems)
}

extension Endpoint {
    var scheme: String {
        return "https"
    }
    
    var host: String {
        switch self {
        case .ad(let host, _),
             .userId(let host, _),
             .install(let host, _),
             .open(let host, _),
             .reg(let host, _),
             .auth(let host, _),
             .bet(let host, _),
             .dep(let host, _):
            return host
        }
    }
    
    var port: Int? {
        return nil
    }
    
    var queryItems: HTTPQueryItems? {
        switch self {
        case .ad(_, let queryItems),
             .userId(_, let queryItems),
             .install(_, let queryItems),
             .open(_, let queryItems),
             .reg(_, let queryItems),
             .auth(_, let queryItems),
             .bet(_, let queryItems),
             .dep(_, let queryItems):
            return queryItems
        }
    }
    
    var path: String {
        switch self {
        case .ad:
            return "/app"
        case .userId:
            return "/appmc"
        case .install,
             .open,
             .reg,
             .auth,
             .bet,
             .dep:
            return "/appevent"
        }
    }
    
    var method: HTTPMethod {
        return .get
    }
    
    var params: HTTPParameters? {
        return nil
    }
    
    var headers: HTTPHeaders {
        return [:]
    }
}
