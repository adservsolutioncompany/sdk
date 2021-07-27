//
//  ClientError.swift
//  OneMoreAdSDK
//
//  Created by admin on 8/22/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation

enum ClientError: LocalizedError {
    case endpointError(error: Error)
    case networkError(error: URLSessionError)
    case serverError(statusCode: Int)
    case parsingError
    case dataIsEmptyError
    
    var errorDescription: String? {
        switch self {
        case .endpointError(let endpointError):
            return "Endpoint error: \(endpointError.localizedDescription)"
        case .networkError(let networkError):
            return "Network error: \(networkError.localizedDescription)"
        case .serverError(let statusCode):
            return "Server error \(statusCode)"
        case .parsingError:
            return "Fail to parse response"
        case .dataIsEmptyError:
            return "Response data is empty"
        }
    }
    
    var isSilent: Bool {
        if case .networkError(let networkError) = self, case .cancelled = networkError {
            return true
        }
        
        return false
    }
}
