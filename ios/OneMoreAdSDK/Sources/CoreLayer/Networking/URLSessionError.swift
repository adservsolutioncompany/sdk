//
//  URLSessionError.swift
//  OneMoreAdSDK
//
//  Created by admin on 8/22/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation

enum URLSessionError: LocalizedError {
    
    case unknown
    case timeOut
    case networkConnectionLost
    case cancelled
    case other(urlError: Error)
    
    init(urlError: Error?) {
        if let urlError = urlError as NSError?, urlError.domain == NSURLErrorDomain {
            switch urlError.code {
            case NSURLErrorNotConnectedToInternet,
                 NSURLErrorCannotConnectToHost,
                 NSURLErrorNetworkConnectionLost:
                self = .networkConnectionLost
            case NSURLErrorTimedOut:
                self = .timeOut
            case NSURLErrorCancelled:
                self = .cancelled
            default:
                self = .other(urlError: urlError)
            }
        } else {
            self = .unknown
        }
    }
    
    var errorDescription: String? {
        switch self {
        case .unknown:
            return "Unknown"
        case .timeOut:
            return "Timeout"
        case .networkConnectionLost:
            return "Network connection lost"
        case .cancelled:
            return "Cancelled"
        case .other(let urlError):
            return urlError.localizedDescription
        }
    }
}
