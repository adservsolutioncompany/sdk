//
//  AnalyticsProvider.swift
//  OneMoreAdSDK
//
//  Created by admin on 12/24/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation
import UIKit
import AdSupport

struct AnalyticsProvider {
    let systemName: String = Device.current.systemName ?? "null"
    let systemVersion: String = Device.current.systemVersion ?? "null"
    let model: String = Device.current.model ?? "null"
    let fullModelName: String = Device.current.realDevice.description
    var screen: String { "\(Int(UIScreen.main.bounds.width))x\(Int(UIScreen.main.bounds.height))" }
    let bundleID: String = Bundle.main.bundleIdentifier ?? "null"
    let processorCount = ProcessInfo.processInfo.processorCount
    let physicalMemory = ProcessInfo.processInfo.physicalMemory
    let languageCode: String = Locale.current.languageCode ?? "null"
    var timeZoneShift: Int { TimeZone.current.secondsFromGMT()/60 }
    let adIdentifier: String = ASIdentifierManager.shared().advertisingIdentifier.uuidString
    
    let webViewFrame: CGRect
    
    var webViewWidth: Int { Int(webViewFrame.size.width) }
    var webViewHeight: Int { Int(webViewFrame.size.height) }
    
    private static func secondsToHoursMinutesSeconds(seconds : Int) -> (Int, Int, Int) {
      return (seconds / 3600, (seconds % 3600) / 60, (seconds % 3600) % 60)
    }
}
