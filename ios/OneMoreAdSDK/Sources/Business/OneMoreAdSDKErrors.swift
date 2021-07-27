//
//  OneMoreAdSDKError.swift
//  OneMoreAdSDK
//
//  Created by admin on 12/24/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation

public enum OneMoreAdSDKError: Error {
    case networkError(error: Error)
    case sDKDoesNotConfigured
    case parentViewControllerNotInViewHierarchy
}

