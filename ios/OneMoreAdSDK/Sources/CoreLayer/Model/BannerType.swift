//
//  BannerType.swift
//  OneMoreAdSDK
//
//  Created by admin on 1/9/20.
//  Copyright © 2020 SLG. All rights reserved.
//

import Foundation


public enum BannerType {
    case banner
    case bannerLarge
    case bannerRectangle
    
    var adType: AdType {
        switch self {
        case .banner:
            return .banner
        case .bannerLarge:
            return .bannerLarge
        case .bannerRectangle:
            return .bannerRectangle
        }
    }
    
}
